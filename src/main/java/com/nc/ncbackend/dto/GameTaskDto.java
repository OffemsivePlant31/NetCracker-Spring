package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.GameTask;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameTaskDto {



    private long id;

    private GamePointDto point;
    private String findCondition;
    private String checkCondition;
    private String checkConditionType;
    private LocalDateTime timeDone;
    private List<Long> prevTasks;
    private boolean inValidation;

    public GameTaskDto(){

    }
    public GameTaskDto(GameTask gameTask){
        parse(gameTask);
    }

    public void parse(GameTask gameTask){

        id = gameTask.getId();
        point = new GamePointDto(gameTask.getTask().getPoint());
        if(gameTask.getTask().getFindCondition() != null){
            findCondition = gameTask.getTask().getFindCondition().getDescription();
        }
        if(gameTask.getTask().getCheckCondition() != null){
            checkCondition = gameTask.getTask().getCheckCondition().getDescription();
            switch (gameTask.getTask().getCheckCondition().getType()){
                case 1: checkConditionType = "location"; break;
                case 2: checkConditionType = "code"; break;
                case 3: checkConditionType = "photo"; break;
                default: checkConditionType = "location"; break;
            }
        }

        timeDone = gameTask.getTimeDone();

        prevTasks = new ArrayList<>();
        for(GameTask gt : gameTask.getPrevTasks()){
            prevTasks.add(gt.getId());
        }

    }

    public GameTaskDto hidePoint(){
        this.point = point;
        return this;
    }

    public GameTaskDto setInValidation(boolean inValidation){
        this.inValidation = inValidation;
        return this;
    }
}
