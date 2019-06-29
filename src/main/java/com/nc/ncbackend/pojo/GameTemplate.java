package com.nc.ncbackend.pojo;

import java.time.LocalDateTime;
import java.util.List;
@Deprecated
public class GameTemplate {
    private Long authorId;
    private String name;
    private String description;
    private int maxPlayers;
    private GameMode mode;
    private List<TaskTemplate> tasks;
    private LocalDateTime startDate;
    private Long periodicity;
}
