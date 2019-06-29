package com.nc.ncbackend;

import com.nc.ncbackend.pojo.GameInstance;
import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.Team;
import com.nc.ncbackend.pojo.notification.Notification;
import com.nc.ncbackend.repository.NotificationRepository;
import com.nc.ncbackend.service.GameInstanceService;
import com.nc.ncbackend.service.organizer.GameOrganizerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class Schedules {

    @Autowired
    GameInstanceService gameInstanceService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    GameOrganizerFactory gameOrganizerFactory;


    // TODO: 26.11.2017 Сделать интервал в 5 минут

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void checkGameStatus(){

        List<GameInstance> games = gameInstanceService.findAllByEndDateIsNull();

        LocalDateTime now = LocalDateTime.now();
        for(GameInstance game : games){
            int interval = 0;
            long realInterval = Duration.between(now, game.getStartDate()).toMinutes();
            if (realInterval < -10) interval = -10;
            if (realInterval > 0) interval = 5;
            if (realInterval > 5) interval = 30;
            if (realInterval > 30) interval = 60;
            if (realInterval > 60) continue;

            if(interval == 0) continue;


            if(game.getLastMilestone() == interval){
                continue;
            }
            game.setLastMilestone(interval);

            if(interval == -10){

                gameOrganizerFactory
                        .getInstance(game.getGame().getGameMode().getName())
                        .startTimeoutOver(game);

                continue;
            }



            for(Team team : game.getTeams()){
                for(Person person : team.getUsers()){

                    notificationRepository.save(new Notification(
                            11,
                            "Скоро начнётся игра",
                            "До игры " + game.getGame().getName() + " осталось менее " + interval + " минут",
                            person
                    ));
                }
            }



        }


    }
}
