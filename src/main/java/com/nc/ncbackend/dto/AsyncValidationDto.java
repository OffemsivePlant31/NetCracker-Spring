package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.AsyncValidation;

public class AsyncValidationDto {


    private long id;
    private String description;
    private long type;
    private long photo;
    private String pointName;
    private String from;

    private int state;
    private String message;

    AsyncValidationDto(){}

    public AsyncValidationDto(AsyncValidation asyncValidation){
        parse(asyncValidation);
    }

    public void parse(AsyncValidation asyncValidation){
        id = asyncValidation.getId();
        type = asyncValidation.getGameTask().getTask().getCheckCondition().getType();
        description = asyncValidation.getGameTask().getTask().getCheckCondition().getDescription();
        if(asyncValidation.getPhoto() != null){
            photo = asyncValidation.getPhoto().getId();
        }
        from = asyncValidation.getGameTask().getTeam().getUsers().get(0).getName();
        pointName = asyncValidation.getGameTask().getTask().getPoint().getName();
    }

    public long getId() {
        return id;
    }
    public int getState() {
        return state;
    }
    public String getMessage() {
        return message;
    }
}
