package com.nc.ncbackend.service.organizer;

import com.nc.ncbackend.dto.*;
import com.nc.ncbackend.pojo.*;

import java.util.List;

public interface GameOrganizer {

    // Сопровождение текущей игры

    TaskValidationResponse validateTask(Team team, TaskValidationRequest request);
    void processTaskValidationResult(AsyncValidationDto dto);
    List<GameTaskDto> getTasks(Team team);
    GameTaskDto getNextTask(Team team);

    // Создание инстанса игры

    GameInstance createInstance(CreateGameInstanceRequest request);
    void finalizeGame(GameInstance gameInstance);
    void startTimeoutOver(GameInstance gameInstance);

    // Регистрация пользователя

    Team subscribe(GameInstance gameInstance, Person person);
    void unsubscribe(GameInstance gameInstance, Person person);

    void leave(Team team, Person person);


    default double distance(double lat1, double lon1, double lat2, double lon2) {

        final int R = 6371; // Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000;
    }




    default String validateCheckCondition(GameTask gameTask, TaskValidationRequest request){
        String result = "ok";

        double dist = distance(
                request.getLatitude(),
                request.getLongitude(),
                gameTask.getTask().getPoint().getLatitude(),
                gameTask.getTask().getPoint().getLongitude());

        if( dist > 30){
            if(dist > 500){
                return "Очень холодно";
            }
            if(dist > 100){
                return "Холодновато";
            }
            return "Почти попал";
        }

        CheckCondition cc;
        if((cc = gameTask.getTask().getCheckCondition()) != null){
            if((cc.getType() == 2)&&((request.getCode() == null)||(! request.getCode().equals(cc.getCode())))){
                return "Неверный пароль";
            }
        }

        return result;
    }



    default GameTask getNextTaskPojo(Team team){

        c1:for(GameTask gameTask : team.getTasks()){
            if(gameTask.getTimeDone() != null) continue c1;
            for(GameTask prev : gameTask.getPrevTasks()){
                if(prev.getTimeDone() == null) continue c1;
            }
            return gameTask;
        }
        return null;
    }

}
