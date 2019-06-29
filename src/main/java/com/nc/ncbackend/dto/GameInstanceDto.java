package com.nc.ncbackend.dto;


import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Person;

import java.time.LocalDateTime;

public class GameInstanceDto{

    private long id;

    private GameTemplateDto gameTemplateDto;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String overseer;

    /**
     * true, если пользователь, сделавший запрос, подписан на игру
     */
    private boolean subscribed;

    public GameInstanceDto(){

    }

    public GameInstanceDto(GameInstance gameInstance){
        parse(gameInstance);
    }


    public void parse(GameInstance gameInstance){
        id = gameInstance.getId();
        gameTemplateDto = new GameTemplateDto(gameInstance.getGame());
        startDate = gameInstance.getStartDate();
        endDate = gameInstance.getEndDate();
        overseer = gameInstance.getOverseer().getName();


    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public GameInstanceDto asInvite(long id, Person sender){
        this.id = id;
        this.overseer = sender.getName();
        return this;
    }
}
