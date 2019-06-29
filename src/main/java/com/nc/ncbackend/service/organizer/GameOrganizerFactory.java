package com.nc.ncbackend.service.organizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameOrganizerFactory {


    @Autowired
    SoloGameOrganizer soloGameOrganizer;

    @Autowired
    CommonGameOrganizer commonGameOrganizer;


    public GameOrganizer getInstance(String gamemode){

        switch(gamemode){
            case "Соло" : return soloGameOrganizer;
            case "Каждый за себя" : return commonGameOrganizer;
            default: return null;
        }
    }

}
