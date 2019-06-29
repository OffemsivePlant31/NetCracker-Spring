package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.notification.InviteNotification;

public class InviteNotificationDto {

    private long id;
    private String sender;
    private String game;

    public InviteNotificationDto() {
    }

    public InviteNotificationDto(InviteNotification inviteNotification) {
        parse(inviteNotification);
    }

    private void parse(InviteNotification inviteNotification) {
        this.id = inviteNotification.getId();
        this.sender = inviteNotification.getSender().getName();
        this.game = inviteNotification.getGameInstance().getGame().getName();
    }
}
