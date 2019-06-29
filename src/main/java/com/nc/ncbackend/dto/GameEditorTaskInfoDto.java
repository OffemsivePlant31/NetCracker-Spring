package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.*;

import java.util.ArrayList;
import java.util.List;

public class GameEditorTaskInfoDto {
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFindCondition() {
        return findCondition;
    }

    public void setFindCondition(String findCondition) {
        this.findCondition = findCondition;
    }

    public String getCheckConditionDescription() {
        return checkConditionDescription;
    }

    public void setCheckConditionDescription(String checkConditionDescription) {
        this.checkConditionDescription = checkConditionDescription;
    }

    public String getCheckConditionCode() {
        return checkConditionCode;
    }

    public void setCheckConditionCode(String checkConditionCode) {
        this.checkConditionCode = checkConditionCode;
    }

    public GamePointDto getPoint() {
        return point;
    }

    public void setPoint(GamePointDto point) {
        this.point = point;
    }

    public List<GameEditorTaskInfoDto> getPrev() {
        return prev;
    }

    public void setPrev(List<GameEditorTaskInfoDto> prev) {
        this.prev = prev;
    }

    private long taskId;
    private String name;
    private String description;
    private String findCondition;
    private String checkConditionDescription;
    private String checkConditionCode;
    private GamePointDto point;
    private List<GameEditorTaskInfoDto> prev;

    public GameEditorTaskInfoDto(Task task){
        taskId = task.getId();
        name = task.getName();
        description = task.getDescription();
        CheckCondition check = task.getCheckCondition();
        if (check != null){
            checkConditionDescription = task.getCheckCondition().getDescription();
            checkConditionCode = task.getCheckCondition().getCode();
        }
        FindCondition find = task.getFindCondition();
        if (find != null){
            findCondition = task.getFindCondition().getDescription();
        }
        if (task.getPoint() != null){
            point = new GamePointDto((task.getPoint()));
        } else {
            point = new GamePointDto();
        }
        // point = new GamePointDto(task.getPoint());
        prev = new ArrayList<>();
        for (Task t: task.getPrevTasks()) {
            prev.add(new GameEditorTaskInfoDto(t));
        }
    }
}
