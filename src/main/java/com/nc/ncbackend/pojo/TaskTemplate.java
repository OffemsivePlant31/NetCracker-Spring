package com.nc.ncbackend.pojo;

import java.util.List;


@Deprecated
public class TaskTemplate {
    private String name;
    private String description;
    private FindCondition challenge;
    private GamePoint point;
    private List<TaskTemplate> prev;
}
