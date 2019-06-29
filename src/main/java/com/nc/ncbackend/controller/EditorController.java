package com.nc.ncbackend.controller;


import com.nc.ncbackend.dto.*;
import com.nc.ncbackend.pojo.*;
import com.nc.ncbackend.pojo.notification.Notification;
import com.nc.ncbackend.repository.*;
import com.nc.ncbackend.repository.GameModeRepository;
import com.nc.ncbackend.service.*;
import com.nc.ncbackend.service.organizer.GameOrganizerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/edit")
public class EditorController {

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

    @Autowired
    EditorService editorService;

    @PersistenceContext
    EntityManager entityManager;

    @PostMapping("/{id}/create")
    public void createGameInstance(
            @PathVariable("id") long gameId,
            @RequestBody CreateGameDto create,
            @RequestParam("userId") long userId
    ){
        editorService.createGameInstance(gameId, create, userId);
    }

    @GetMapping("/modes")
    public List<GameModeDto> getModes(){
        ArrayList<GameModeDto> modesDto = new ArrayList<>();
        gameModeRepository.findAll().forEach(mode -> modesDto.add(new GameModeDto(mode.getId(), mode.getName(), mode.getDescription())));
        return modesDto;
    }

    @GetMapping("/users")
    public List<PersonDto> getUsers(){
        ArrayList<PersonDto> persons = new ArrayList<>();
        for (Person person:personService.findAll()) {
            persons.add(new PersonDto(person));
        }
        return persons;
    }

    @GetMapping("/points/{id}")
    public GamePointDto getPoint(@PathVariable("id") long pointId){
        return new GamePointDto(gamePointService.find(pointId));
    }

    @PostMapping("/new")
    public Long createNewEmptyGame(@RequestBody long userId){
        Game game = new Game();
        Person person = personService.findOne(userId);
        game.setActive(false);
        game.setAuthor(person);
        game.setDescription("Empty");
        game.setName("Game");
        game.setGameMode(gameModeRepository.findOne(1l));
        gameService.save(game);
        return game.getId();
    }

    @PostMapping("/{id}/tasks/new")
    public Long createNewTaskInGame(@PathVariable("id") long gameId){
        return editorService.createNewTaskInGame(gameId);
    }

    @PostMapping("/points/new")
    public Long createNewPointInTask(@RequestBody long taskId, @RequestParam("userId") long userId){
        Task task = taskService.find(taskId);
        GamePoint point = new GamePoint();
        gamePointService.save(point);
        task.setPoint(point);
        CheckCondition check = task.getCheckCondition();
        FindCondition find = task.getFindCondition();
        check.setPoint(point);
        find.setPoint(point);
        task.setCheckCondition(check);
        task.setFindCondition(find);
        taskService.save(task);
        return point.getId();
    }

    @PutMapping("/{gameId}/tasks/reorder")
    public void reorderTasks(
            @PathVariable("gameId") long gameId,
            @RequestBody List<Long> newTaskOrder
    ){
        Game game = gameService.findOne(gameId);
        List<Task> tasks = taskService.findByGame(game);
        for (Task task:tasks) {
            List<Task> prevTasks = new ArrayList<>();
            if (newTaskOrder.indexOf(task.getId()) != 0) {
                prevTasks.add(taskService.find(newTaskOrder.indexOf(task.getId() - 1)));
            }
            task.setPrevTasks(prevTasks);
            taskService.save(task);
        }
    }

    @PostMapping("/points")
    public void createPoint(@RequestBody GamePointDto point){
        GamePoint newPoint = new GamePoint();
        newPoint.setLongitude(point.getLongitude());
        newPoint.setLatitude(point.getLatitude());
        newPoint.setName(point.getName());
        gamePointService.save(newPoint);
    }

    @PutMapping("/points/{id}")
    public void changePoint(
            @PathVariable("id") long pointId,
            @RequestBody GamePointDto point
    ){
        GamePoint newPoint = gamePointService.find(pointId);
        newPoint.setName(point.getName());
        newPoint.setDescription(point.getDescription());
        newPoint.setLatitude(point.getLatitude());
        newPoint.setLongitude(point.getLongitude());
        gamePointService.save(newPoint);
    }

    @DeleteMapping("/points/{id}")
    public void deletePoint(
            @PathVariable("id") long pointId
    ){
        gamePointService.delete(pointId);
    }

    @PutMapping("/{id}/tasks")
    public void updateTaskInfo(
            @RequestBody GameEditorTaskInfoDto task
    ){
        editorService.saveNewTask(task);
    }

    @GetMapping("/{id}/tasks")
    public List<GameEditorTaskInfoDto> getGameTasks(
            @PathVariable("id") long gameId
    ){
        Game game = gameService.findOne(gameId);
        List<GameEditorTaskInfoDto> gameTasks = new ArrayList<>();
        for (Task task:taskService.findByGame(game)) {
            gameTasks.add(new GameEditorTaskInfoDto(task));
        }
        return gameTasks;
    }

    @GetMapping("/{gameId}/tasks/{taskId}")
    public GameEditorTaskInfoDto getGameTasksById(
            @PathVariable("taskId") long taskId
    ){
        return new GameEditorTaskInfoDto(taskService.find(taskId));
    }

    @GetMapping("/")
    public List<GameEditorInfoDto> getUserTemplates(
            @RequestParam("userId") long userId
    ){
        Person person = personService.findOne(userId);
        List<Game> templates = gameService.findByAuthor(person);
        List<GameEditorInfoDto> response = new ArrayList<>();
        for (Game game:templates) {
            game.setTasks(new ArrayList<>());
            response.add(new GameEditorInfoDto(game));
        }

        return response;
    }

    @GetMapping("/store")
    public List<GameEditorInfoDto> getTemplatesFromStore(
            @RequestParam long userId
    ){  List<Game> templates = gameService.findAllPublicTemplates();
        List<GameEditorInfoDto> response = new ArrayList<>();
        for (Game game : templates) {
            GameEditorInfoDto dto = new GameEditorInfoDto(game);
            dto.setTasks(new ArrayList<>());
            response.add(dto);
        }

        return response;
    }

    @PostMapping("/add")
    public void addTemplateFromStore(
            @Deprecated @RequestParam long userId,
            @RequestBody long gameId
    ){
        editorService.getTemplatesFromStore(userId, gameId);
    }

    @GetMapping("/{id}")
    public GameEditorInfoDto getInfo(
            @PathVariable long id,
            @RequestParam("userId") long userId
    ){

        Game game = gameService.findOne(id);
        game.setTasks(new ArrayList<>());
        return new GameEditorInfoDto(game);
    }

    @PostMapping("/{id}")
    public void publishGame(
            @RequestParam("userId") long userId,
            @PathVariable long id,
            @RequestBody  GameEditorInfoDto gameDto
    ){
        Game game = gameService.findOne(id);
        game.setName(gameDto.getName());
        game.setMaxPlayers(gameDto.getMaxPlayers());
        GameMode mode = gameModeRepository.findOne(gameDto.getMode().getId());
        game.setGameMode(mode);
        game.setDescription(gameDto.getDescription());
        game.setActive(true);
        game.setPublicTemplate(true);
        gameService.save(game);
    }

    @DeleteMapping("/{id}/tasks/delete")
    public void deleteTask(
            @RequestParam("userId") long userId,
            @RequestParam long taskId
    ){
        Task task = taskService.find(taskId);
        if (task.getPrevTasks().size() == 0 && task.getNextTasks().size() == 0){

        } else if (task.getPrevTasks().size() == 0 && task.getNextTasks().size() != 0) {
            task.getNextTasks().get(0).setPrevTasks(new ArrayList<>());
        } else if (task.getPrevTasks().size() != 0 && task.getNextTasks().size() == 0) {
            task.getPrevTasks().get(0).setNextTasks(new ArrayList<>());
        } else {
            ArrayList<Task> nextTask = new ArrayList<>();
            nextTask.add(task.getNextTasks().get(0));
            task.getPrevTasks().get(0).setNextTasks(nextTask);
        }
        taskService.delete(taskId);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(
            @PathVariable long id,
            @RequestParam("userId") long userId
    ){
        Game game = gameService.findOne(id);
        gameService.delete(game);
    }
}
