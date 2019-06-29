package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.notification.TaskCompleteNotification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskCompleteNotificationRepository extends CrudRepository<TaskCompleteNotification, Long> {

    List<TaskCompleteNotification> findByPersonAndCheckedIsFalse(Person person);

}
