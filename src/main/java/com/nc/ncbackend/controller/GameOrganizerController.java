package com.nc.ncbackend.controller;

import com.nc.ncbackend.dto.*;
import com.nc.ncbackend.pojo.*;
import com.nc.ncbackend.pojo.notification.Notification;
import com.nc.ncbackend.repository.AsyncValidationRepository;
import com.nc.ncbackend.repository.GameInstanceRepository;
import com.nc.ncbackend.repository.InviteRepository;
import com.nc.ncbackend.repository.NotificationRepository;
import com.nc.ncbackend.service.*;
import com.nc.ncbackend.service.organizer.GameOrganizerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/organizer")
public class GameOrganizerController {


    @Autowired
    PersonService personService;

    @Autowired
    TeamService teamService;

    @Autowired
    GameOrganizerFactory gameOrganizerFactory;

    @Autowired
    GameService gameService;

    @Autowired
    GameInstanceRepository gameInstanceService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AsyncValidationRepository asyncValidationRepository;

    @Autowired
    GameTaskService gameTaskService;

    @Autowired
    InviteRepository inviteRepository;


    @GetMapping("/game/id")
    public ResponseEntity<Long> getNextGameId(
            @RequestParam("userid") long userid
    ){

        Person person = personService.findOne(userid);
        Team team = teamService.findFirstByPerson(person);

        GameInstance gameInstance = gameInstanceService
                .findTop1ByOverseerAndEndDateIsNullOrderByStartDate(person);


        if((team == null)&&(gameInstance == null)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if((team != null)&&(gameInstance == null)){
            return new ResponseEntity<>(team.getGame().getId(), HttpStatus.OK);
        }
        if((team == null)&&(gameInstance != null)){
            return new ResponseEntity<>(gameInstance.getId() * (-1), HttpStatus.OK);
        }

        if(team.getGame().getStartDate().isBefore(gameInstance.getStartDate())){
            return new ResponseEntity<>(team.getGame().getId(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(gameInstance.getId() * (-1), HttpStatus.OK);
        }





    }

    @GetMapping("/task")
    public List<GameTaskDto> getTeamTasks(
            @RequestParam("userid") long userid,
            @RequestParam("gameid") long gameid
    ){

        Team team = teamService.findByGame_IdAndUsers_Id(gameid, userid);
        String gamemode = team.getGame().getGame().getGameMode().getName();

        return gameOrganizerFactory.getInstance(gamemode).getTasks(team);
    }

    @GetMapping("/task/next")
    public ResponseEntity<GameTaskDto> getTeamNextTask(
            @RequestParam("userid") long userid,
            @RequestParam("gameid") long gameid
    ){


        Team team = teamService.findByGame_IdAndUsers_Id(gameid, userid);
        if(team == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        String gamemode = team.getGame().getGame().getGameMode().getName();

        GameTaskDto task = gameOrganizerFactory.getInstance(gamemode).getNextTask(team);

        if(task != null){
            return new ResponseEntity<>(task, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/validate")
    public TaskValidationResponse validate(
            @RequestBody TaskValidationRequest request,
            @RequestParam("userid") long userid,
            @RequestParam("gameid") long gameid
    ){

        Team team = teamService.findByGame_IdAndUsers_Id(gameid, userid);
        String gamemode = team.getGame().getGame().getGameMode().getName();

        return gameOrganizerFactory.getInstance(gamemode).validateTask(team, request);
    }

    @GetMapping("/validate/async/{taskid}/result")
    public TaskValidationResponse getAsyncValidationAnswer(
            @PathVariable("taskid") long taskid
    ){
        GameTask gameTask = gameTaskService.findOne(taskid);
        AsyncValidation result = asyncValidationRepository.findByGameTaskOrderByIdDesc(gameTask).get(0);
        if(result == null){
            return new TaskValidationResponse(1, "Отказ");
        }else{
            return new TaskValidationResponse(result.getState(), result.getMessage());
        }

    }

    @GetMapping("/validate/async")
    public List<AsyncValidationDto> getAsyncValidationTasks(
            @RequestParam("userid") long userid
    ){
        List<AsyncValidationDto> result = new ArrayList<>();
        Person auditor = personService.findOne(userid);
        for(AsyncValidation asyncValidation : asyncValidationRepository.findByAuditorAndState(auditor, 0)){
            result.add(new AsyncValidationDto(asyncValidation));
        }

        return result;
    }

    @PostMapping("/validate/async")
    public void sendAsyncValidationTasks(
            @RequestBody AsyncValidationDto dto
    ){

        String gamemode = "Каждый за себя";

        gameOrganizerFactory.getInstance(gamemode).processTaskValidationResult(dto);

    }

    @PostMapping("/start")
    public void startGame(
        @RequestBody CreateGameInstanceRequest request
    ){

        Game game = gameService.findOne(request.getGameId());
        gameOrganizerFactory.getInstance(game.getGameMode().getName()).createInstance(request);

    }

    @PostMapping("/leave")
    public void leaveGame(
            @RequestParam("userid") long userid,
            @RequestParam("gameid") long gameid
    ){

        Person person = personService.findOne(userid);
        Team team = teamService.findByGame_IdAndUsers_Id(gameid, userid);
        String gamemode = team.getGame().getGame().getGameMode().getName();
        gameOrganizerFactory.getInstance(gamemode).leave(team, person);

    }

    @PostMapping("/subscribe")
    public void subscribe(
            @RequestParam("gameId") long gameId,
            @RequestParam("userId") long userId
    ){
        Person person = personService.findOne(userId);
        GameInstance gameInstance = gameInstanceService.findOne(gameId);

        gameOrganizerFactory
                .getInstance(gameInstance.getGame().getGameMode().getName())
                .subscribe(gameInstance, person);
    }

    @PostMapping("/invite")
    public void invite(
            @RequestParam("gameid") long gameId,
            @RequestParam("userid_from") long userIdFrom,
            @RequestParam("userid_to") long userIdTo
    ){
        Person sender = personService.findOne(userIdFrom);
        Person person = personService.findOne(userIdTo);
        GameInstance gameInstance = gameInstanceService.findOne(gameId);

        Invite invite = new Invite(sender, person, gameInstance);
        inviteRepository.save(invite);

        notificationRepository.save(new Notification(
                60,
                "Приглашение в игру",
                "Пользователь " + sender.getName() + " пригласил вас сыграть в " + gameInstance.getGame().getName(),
                person
        ));
    }

    @PostMapping("/invite/response")
    public void inviteResponse(
            @RequestParam("inviteid") long inviteId,
            @RequestParam("confirmed") boolean confirmed
    ){
        Invite invite = inviteRepository.findOne(inviteId);
        invite.setAnswered(true);
        inviteRepository.save(invite);

        if(confirmed){
            gameOrganizerFactory
                    .getInstance(invite.getGame().getGame().getGameMode().getName())
                    .subscribe(invite.getGame(), invite.getPersonTo());
        }


    }





}
