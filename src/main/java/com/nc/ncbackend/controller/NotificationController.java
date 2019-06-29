package com.nc.ncbackend.controller;

import com.nc.ncbackend.dto.NotificationDto;
import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.notification.Notification;
import com.nc.ncbackend.repository.GameNotificationRepository;
import com.nc.ncbackend.repository.InviteNotificationRepository;
import com.nc.ncbackend.repository.NotificationRepository;
import com.nc.ncbackend.repository.TaskCompleteNotificationRepository;
import com.nc.ncbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired GameNotificationRepository gameNotificationRepository;
    @Autowired TaskCompleteNotificationRepository taskCompleteNotificationRepository;
    @Autowired InviteNotificationRepository inviteNotificationRepository;

    @Autowired
    PersonService personService;

    @Autowired
    NotificationRepository notificationRepository;


    @GetMapping("/")
    public ResponseEntity<List<NotificationDto>> getAll(
            @RequestParam("userid") long userid
    ){
        Person person = personService.findOne(userid);

        List<Notification> notifications = notificationRepository.findByPersonAndCheckedIsFalseOrderByIdAsc(person);

        if(notifications.size() == 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        List<NotificationDto> result = new ArrayList<>();
        for(Notification notification : notifications){
            notification.setChecked(true);
            notificationRepository.save(notification);

            result.add(new NotificationDto(notification));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
