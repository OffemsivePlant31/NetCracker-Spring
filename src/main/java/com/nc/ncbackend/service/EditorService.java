package com.nc.ncbackend.service;

import com.nc.ncbackend.dto.*;
import com.nc.ncbackend.pojo.*;
import com.nc.ncbackend.pojo.notification.Notification;
import com.nc.ncbackend.repository.GameModeRepository;
import com.nc.ncbackend.repository.InviteRepository;
import com.nc.ncbackend.repository.NotificationRepository;
import com.nc.ncbackend.service.organizer.GameOrganizerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class EditorService {

    private GameEditorInfoDto infoDto;
    private Game game = new Game();

    @Autowired
    GameOrganizerFactory gameOrganizerFactory;

    @Autowired
    GameService gameService;

    @Autowired
    PersonService personService;

    @Autowired
    GameInstanceService gameInstanceService;

    @Autowired
    TaskService taskService;

    @Autowired
    GamePointService gamePointService;

    @Autowired
    GameModeRepository gameModeRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    InviteRepository inviteRepository;

    //@Transactional(propagation = Propagation.REQUIRED)
    public GameInstance createGameInstance(long gameId, CreateGameDto createGameDto, long userId){
        Game game = gameService.findOne(gameId);
        CreateGameInstanceRequest request = new CreateGameInstanceRequest();
        request.setGameId(gameId);
        request.setPublic(createGameDto.isPublic());
        LocalDateTime localDateTime = LocalDateTime.of(createGameDto.getYear(), createGameDto.getMonth() + 1, createGameDto.getDay(), createGameDto.getHour(), createGameDto.getMinute());
        request.setStartDate(localDateTime);
        request.setUserId(userId);
        GameInstance gameInstance = gameOrganizerFactory.getInstance(game.getGameMode().getName()).createInstance(request);
        if (!createGameDto.isPublic()){
            for (PersonDto person:createGameDto.getUsers()) {
                Notification notification = new Notification();
                notification.setType(60);
                notification.setTitle("Приглашение на игру");
                notification.setText(personService.findOne(userId).getName() + " пригласил Вас на квест");
                notification.setPerson(personService.findOne(person.getId()));
                notificationRepository.save(notification);
                Invite invite = new Invite();
                invite.setGame(gameInstance);
                invite.setPersonFrom(personService.findOne(userId));
                invite.setPersonTo(personService.findOne(person.getId()));
                inviteRepository.save(invite);
            }
        }
        return gameInstance;
    }

    //@Transactional
    public Game getTemplatesFromStore(long userId, long gameId){
        Person person = personService.findOne(userId);
        Game game = gameService.findOne(gameId);
        Game newGame = new Game();
        newGame.setGameMode(game.getGameMode());
        newGame.setName(game.getName());
        newGame.setDescription(game.getDescription());
        newGame.setAuthor(person);
        newGame.setActive(true);
        newGame.setPublicTemplate(true);
        List<Task> tasks = new ArrayList<>();
        for (Task task:taskService.findByGame(game)) {
            Task newTask = new Task();
            newTask.setFindCondition(task.getFindCondition());
            newTask.setCheckCondition(task.getCheckCondition());
            GamePoint newPoint = new GamePoint();
            GamePoint point = gamePointService.find(task.getPoint().getId());
            newPoint.setName(point.getName());
            newPoint.setDescription(point.getDescription());
            newPoint.setLatitude(point.getLatitude());
            newPoint.setLongitude(point.getLongitude());
            gamePointService.save(newPoint);
            newTask.setPoint(newPoint);
            taskService.save(newTask);
            tasks.add(newTask);
        }
        newGame.setTasks(tasks);
        gameService.save(newGame);
        return newGame;
    }

    //@Transactional
    public Long createNewTaskInGame(long gameId){
        Task task = new Task();
        Game game = gameService.findOne(gameId);
        task.setGame(game);
        GamePoint point = new GamePoint();
        point.setName("Point");
        point.setDescription("Empty");
        task.setPoint(point);
        Task prevTask = taskService.findLastByGame(game);
        task.getPrevTasks().add(prevTask);
        FindCondition findCondition = new FindCondition();
        CheckCondition checkCondition = new CheckCondition();
        checkCondition.setType(1);
        task.setFindCondition(findCondition);
        task.setCheckCondition(checkCondition);
        taskService.save(task);
        return task.getId();
    }

    //@Transactional
    public void saveNewTask(GameEditorTaskInfoDto task)
    {
        Task newTask = taskService.find(task.getTaskId());
        newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
        FindCondition find = newTask.getFindCondition();
        find.setDescription(task.getFindCondition());
        newTask.setFindCondition(find);
        CheckCondition check = newTask.getCheckCondition();
        check.setCode(task.getCheckConditionCode());
        check.setDescription(task.getCheckConditionDescription());
        newTask.setCheckCondition(check);
        GamePoint newPoint = newTask.getPoint();
        GamePointDto point = task.getPoint();
        newPoint.setDescription(point.getDescription());
        newPoint.setName(point.getName());
        newPoint.setLatitude(point.getLatitude());
        newPoint.setLongitude(point.getLongitude());
        taskService.save(newTask);
    }
}
