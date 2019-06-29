package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.GameTask;
import com.nc.ncbackend.pojo.Team;

import java.time.LocalDateTime;

public class TeamResultDto {

    private long id;
    private String name;
    private int countDone;
    private LocalDateTime timeDone;

    public TeamResultDto() {
    }

    public TeamResultDto(Team team) {
        parse(team);
    }

    public void parse(Team team){
        this.id = team.getId();
        this.name = team.getUsers().get(0).getName();

        for(GameTask task : team.getTasks()){
            if(task.getTimeDone() != null){
                countDone += 1;

                if(timeDone == null){
                    timeDone = task.getTimeDone();
                }else if (timeDone.isBefore(task.getTimeDone())){
                    timeDone = task.getTimeDone();
                }
            }
        }

    }
}
