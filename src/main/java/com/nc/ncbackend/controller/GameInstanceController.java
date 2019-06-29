package com.nc.ncbackend.controller;

import com.nc.ncbackend.dto.GameInstanceDto;
import com.nc.ncbackend.dto.TeamResultDto;
import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Invite;
import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.Team;
import com.nc.ncbackend.repository.InviteRepository;
import com.nc.ncbackend.repository.PersonRepository;
import com.nc.ncbackend.service.GameInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/instance")
public class GameInstanceController {

    @Autowired
    GameInstanceService gameInstanceService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    InviteRepository inviteRepository;

    @GetMapping("/")
    public ResponseEntity<List<GameInstanceDto>> getGames(
            @RequestParam(required = false, name = "personid") Long personId
    ){

        List<GameInstance> games = gameInstanceService.findAllByEndDateIsNullAndPublicGameIsTrue();

        List<GameInstanceDto> result = new ArrayList<>();
        boolean subscribed = false;

        for(GameInstance gi : games){

            if(personId != null){
                subscribed = false;
                for(Team team : gi.getTeams()){
                    for(Person person : team.getUsers()){
                        if(person.getId() == personId) subscribed = true;
                    }
                }
            }

            GameInstanceDto dto = new GameInstanceDto(gi);
            dto.setSubscribed(subscribed);

            result.add(dto);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/invite/")
    public ResponseEntity<List<GameInstanceDto>> getPrivateGames(
            @RequestParam(required = false, name = "personid") Long personId
    ){

        Person person = personRepository.findOne(personId);
        List<Invite> invites = inviteRepository.findByAnsweredIsFalseAndPersonTo(person);
        List<GameInstanceDto> result = new ArrayList<>();

        for(Invite invite : invites){
            if ((invite.getGame().getEndDate() == null)&&(invite.getGame().getLastMilestone() >= 0)){
                result.add(new GameInstanceDto(invite.getGame()).asInvite(invite.getId(), invite.getPersonFrom()));
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @GetMapping("/{gameid}")
    public ResponseEntity<GameInstanceDto> getGameInfo(@PathVariable("gameid") long gameid){

        GameInstance instance = gameInstanceService.findOne(gameid);

        if(instance == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(new GameInstanceDto(instance), HttpStatus.OK);
        }
    }


    @GetMapping("/{gameid}/team")
    public ResponseEntity<List<TeamResultDto>> getTeamResult(@PathVariable("gameid") long gameid){

        GameInstance instance = gameInstanceService.findOne(gameid);

        if(instance == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            List<TeamResultDto> result = new ArrayList<>();
            for(Team team : instance.getTeams()){
                result.add(new TeamResultDto(team));
            }
            return new ResponseEntity<>(result, HttpStatus.OK);

        }
    }






}
