package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.notification.GameNotification;

public class GameNotificationDto {


    private long id;
    private long gameId;
    private String gameName;
    private int minutes;

    public GameNotificationDto() {}

    public GameNotificationDto(GameNotification gameNotification) {
        parse(gameNotification);
    }

    public void parse(GameNotification gameNotification){
        this.id = gameNotification.getId();
        this.gameName = gameNotification.getGameInstance().getGame().getName();
        this.minutes = gameNotification.getMinutes();
        this.gameId = gameNotification.getGameInstance().getId();
    }

}
