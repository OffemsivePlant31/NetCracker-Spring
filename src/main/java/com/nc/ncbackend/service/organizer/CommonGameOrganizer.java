package com.nc.ncbackend.service.organizer;

import com.nc.ncbackend.dto.*;
import com.nc.ncbackend.pojo.*;
import com.nc.ncbackend.pojo.notification.Notification;
import com.nc.ncbackend.repository.*;
import com.nc.ncbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonGameOrganizer implements GameOrganizer {


    @Autowired TeamService teamService;
    @Autowired GameTaskService gameTaskService;
    @Autowired GameService gameService;
    @Autowired PersonService personService;
    @Autowired GameInstanceService gameInstanceService;

    @Autowired
    SoloGameOrganizer soloGameOrganizer;

    @Autowired
    TaskCompleteNotificationRepository taskCompleteNotificationRepository;

    @Autowired
    GameNotificationRepository gameNotificationRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AsyncValidationRepository asyncValidationRepository;

    @Autowired
    PhotoRepository photoRepository;



    @Override
    //@Transactional
    public TaskValidationResponse validateTask(Team team, TaskValidationRequest request) {

        // TODO: 19.11.2017 Избавиться от дублирования, создать методы для общих блоков кода

        GameTask nextTask = getNextTaskPojo(team);

        String result = validateCheckCondition(nextTask, request);
        if(! result.equals("ok")){
            return new TaskValidationResponse(1, result);
        }

        if (nextTask.getTask().getCheckCondition().getType() == 3){

            if(asyncValidationRepository.findByGameTaskAndState(nextTask, 1) == null){
                asyncValidationRepository.save(new AsyncValidation(
                        nextTask,
                        photoRepository.findOne(request.getPhotoId()),
                        team.getGame().getOverseer()
                ));
                notificationRepository.save(new Notification(
                        50,
                        "Новый запрос на проверку фото",
                        "Игрок " + team.getUsers().get(0).getName() + " отправил фото на проверку",
                        team.getGame().getOverseer()
                ));

                return new TaskValidationResponse(4, "async");
            }

        }

        // проверки прошли

        sendTaskCompleteNotifications(team);

        nextTask.setTimeDone(LocalDateTime.now());
        gameTaskService.save(nextTask);
        team.setGameStarted(true);

        if(nextTask.getNextTasks().size() == 0){
            finalizeGame(team.getGame());
            return new TaskValidationResponse(3, "Игра кончилась");
        }else{
            notificationRepository.save(new Notification(
                    71,
                    "Игрок выполнил задание",
                    "Игрок " + team.getUsers().get(0).getName() + " выполнил задание " + nextTask.getTask().getName(),
                    team.getGame().getOverseer()
            ));
            return new TaskValidationResponse(2, "всё ок");
        }
    }

    @Override
    public void processTaskValidationResult(AsyncValidationDto dto) {

        AsyncValidation asyncValidation = asyncValidationRepository.findOne(dto.getId());
        asyncValidation.setMessage(dto.getMessage());
        asyncValidation.setState(dto.getState());

        for(Person person : asyncValidation.getGameTask().getTeam().getUsers()){
            notificationRepository.save(new Notification(
                    51,
                    "Задача проверена",
                    dto.getMessage(),
                    person
            ));
        }

        if(dto.getState() == 2){

            GameTask nextTask = asyncValidation.getGameTask();

            sendTaskCompleteNotifications(nextTask.getTeam());

            nextTask.setTimeDone(LocalDateTime.now());
            gameTaskService.save(nextTask);

            if(nextTask.getNextTasks().size() == 0){
                finalizeGame(nextTask.getTeam().getGame());
            }

        }

    }

    public void sendTaskCompleteNotifications(Team team){

        long countDone = gameTaskService.countByTeamAndTimeDoneIsNotNull(team);

        for(Team t : team.getGame().getTeams()){
            if(t != team){
                for(Person person : t.getUsers()){
                    notificationRepository.save(new Notification(
                            30,
                            "Игрок выполнил задание",
                            "Игрок " + team.getUsers().get(0).getName() + " выполнил " + countDone + " задание",
                            person
                    ));

                }
            }
        }
    }

    @Override
    public List<GameTaskDto> getTasks(Team team) {

        List<GameTaskDto> result = new ArrayList<>();

        GameTaskDto gameTaskDto;
        for(GameTask gt : team.getTasks()){
            gameTaskDto = new GameTaskDto(gt);

            if(gt.getTimeDone() == null){
                gameTaskDto.hidePoint();
            }

            result.add(gameTaskDto);
        }
        return result;
    }


    @Override
    public GameTaskDto getNextTask(Team team) {
        GameTask next = soloGameOrganizer.getNextTaskPojo(team);

        if(next != null){

            return new GameTaskDto(next)
                    .hidePoint()
                    .setInValidation(asyncValidationRepository.findByGameTaskAndState(next, 0) != null);

        }else{
            return null;
        }
    }


    @Override
    public GameInstance createInstance(CreateGameInstanceRequest request) {

        Person overseer = personService.findOne(request.getUserId());
        Game game = gameService.findOne(request.getGameId());
        GameInstance gameInstance = new GameInstance(request.getStartDate(), null, game, overseer);
        gameInstance.setPublicGame(request.isPublic());
        gameInstanceService.save(gameInstance);

        return gameInstance;
    }

    @Override
    public void finalizeGame(GameInstance gameInstance) {
        gameInstance.setEndDate(LocalDateTime.now());
        gameInstanceService.save(gameInstance);

        for(Team team : gameInstance.getTeams()){
            for(Person person : team.getUsers()){

                //gameNotificationRepository.save(new GameNotification(gameInstance, person, -1));

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


        for(Team team : gameInstance.getTeams()){
            for(Person person : team.getUsers()){

                notificationRepository.save(new Notification(
                        15,
                        "Вы были дисквалифицированы",
                        "Вы не выполнили первое задание: " + gameInstance.getGame().getName(),
                        person
                ));

                leave(team, person);

            }
        }

    }

    @Override
    public Team subscribe(GameInstance gameInstance, Person person) {
        Team soloTeam = new Team(0, gameInstance.getStartDate(), false);
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

        notificationRepository.save(new Notification(
                70,
                "Новый игрок присоединился",
                "Игрок " + soloTeam.getUsers().get(0).getName() + " подписался на игру " + gameInstance.getGame().getName(),
                gameInstance.getOverseer()
        ));

        return soloTeam;
    }

    @Override
    public void unsubscribe(GameInstance gameInstance, Person person) {
        Team team = teamService.findByPersonAndGameInstance(person, gameInstance);
        teamService.delete(team);
    }

    @Override
    public void leave(Team team, Person person) {
        //team.setGameStarted(false);
        //teamService.save(team);
        teamService.delete(team);
    }
}
