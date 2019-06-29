package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.notification.GameNotification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameNotificationRepository extends CrudRepository<GameNotification, Long> {

    Long countByMinutes(int minutes);
    List<GameNotification> findByPersonAndCheckedIsFalse(Person person);

}
