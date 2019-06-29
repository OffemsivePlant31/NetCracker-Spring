package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.notification.InviteNotification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InviteNotificationRepository extends CrudRepository<InviteNotification, Long> {

    List<InviteNotification> findByPersonAndCheckedIsFalse(Person person);
}
