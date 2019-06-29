package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.notification.Notification;

import java.time.LocalDateTime;

public class NotificationDto {


    private long id;
    private long type;
    private String title;
    private String text;
    private LocalDateTime dateCreated;

    public NotificationDto(){}

    public NotificationDto(Notification notification){
        parse(notification);
    }


    public void parse(Notification notification){
        this.id = notification.getId();
        this.type = notification.getType();
        this.title = notification.getTitle();
        this.text = notification.getText();
        this.dateCreated = notification.getDateCreated();

    }

}
