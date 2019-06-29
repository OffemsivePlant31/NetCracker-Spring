package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.notification.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {


    List<Notification> findByPersonAndCheckedIsFalseOrderByIdAsc(Person person);

}
