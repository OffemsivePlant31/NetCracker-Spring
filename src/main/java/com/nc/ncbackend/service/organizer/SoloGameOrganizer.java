package com.nc.ncbackend.service.organizer;

import com.nc.ncbackend.dto.*;
import com.nc.ncbackend.pojo.*;
import com.nc.ncbackend.pojo.notification.Notification;
import com.nc.ncbackend.repository.GameNotificationRepository;
import com.nc.ncbackend.repository.NotificationRepository;
import com.nc.ncbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SoloGameOrganizer implements GameOrganizer{


    @Autowired TeamService teamService;
    @Autowired GameTaskService gameTaskService;
    @Autowired GameService gameService;
    @Autowired PersonService personService;
    @Autowired GameInstanceService gameInstanceService;

    @Autowired
    GameNotificationRepository gameNotificationRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public TaskValidationResponse validateTask(Team team, TaskValidationRequest request) {

        GameTask nextTask = getNextTaskPojo(team);

        String result = validateCheckCondition(nextTask, request);
        if(! result.equals("ok")){
            return new TaskValidationResponse(1, result);
        }


        //Все проверки прошли

        nextTask.setTimeDone(LocalDateTime.now());
        gameTaskService.save(nextTask);

        if(nextTask.getNextTasks().size() == 0){
            finalizeGame(team.getGame());
            return new TaskValidationResponse(2, "Игра кончилась");
        }else{
            return new TaskValidationResponse(0, "всё ок");
        }

    }

    @Override
    public void processTaskValidationResult(AsyncValidationDto dto) {

    }


    @Override
    public List<GameTaskDto> getTasks(Team team) {

        List<GameTaskDto> result = new ArrayList<>();
        for(GameTask gt : team.getTasks()){
            result.add(new GameTaskDto(gt));
        }
        return result;
    }


    @Override
    public GameTaskDto getNextTask(Team team) {

        GameTask next = getNextTaskPojo(team);

        if(next != null){
            return new GameTaskDto(next);
        }else{
            return null;
        }

    }



    @Override
    public GameInstance createInstance(CreateGameInstanceRequest request) {

        Game game = gameService.findOne(request.getGameId());
        Person person = personService.findOne(request.getUserId());
        GameInstance gameInstance = new GameInstance(request.getStartDate(), null, game, person);
        gameInstanceService.save(gameInstance);

        subscribe(gameInstance, person);

        return gameInstance;
    }

    @Override
    public void finalizeGame(GameInstance gameInstance) {
        gameInstance.setEndDate(LocalDateTime.now());
        gameInstanceService.save(gameInstance);

        for(Team team : gameInstance.getTeams()){
            for(Person person : team.getUsers()){
                notificationRepository.save(new Notification(
                        20,
                        "Игра окончена",
                        "Игра " + gameInstance.getGame().getName() + " окончена",
                        person
                ));
            }
        }
    }

    @Override
    public void startTimeoutOver(GameInstance gameInstance) {

    }

    @Override
    public Team subscribe(GameInstance gameInstance, Person person) {

        Team soloTeam = new Team(0, gameInstance.getStartDate(), true);
        soloTeam.setGame(gameInstance);
        soloTeam.getUsers().add(person);


        Game template = gameInstance.getGame();

        // Проходим весь маршрут по таскам, currentTask - текущая итерация
        Task currentTask = null;

        for(Task task : template.getTasks()){
            if(task.getPrevTasks().size() == 0){
                currentTask = task;
            }
        }
        GameTask prevGameTask = new GameTask(null, currentTask);
        soloTeam.getTasks().add(prevGameTask);
        GameTask nextGameTask = null;
        while(currentTask.getNextTasks().size() != 0){

            currentTask = currentTask.getNextTasks().get(0);
            nextGameTask = new GameTask(null, currentTask);
            nextGameTask.getPrevTasks().add(prevGameTask);
            soloTeam.getTasks().add(nextGameTask);
            prevGameTask = nextGameTask;

        }

        teamService.save(soloTeam);

        return soloTeam;
    }

    @Override
    public void unsubscribe(GameInstance gameInstance, Person person) {
        finalizeGame(gameInstance);
    }

    @Override
    public void leave(Team team, Person person) {
        finalizeGame(team.getGame());
    }

}
