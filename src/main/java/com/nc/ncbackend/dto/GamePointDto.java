package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.GamePoint;

public class GamePointDto {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    private long id;
    private String name;
    private String description;
    private float latitude;
    private float longitude;

    public GamePointDto(){

    }

    public GamePointDto(GamePoint gamePoint){
        parse(gamePoint);
    }

    public void parse(GamePoint gamePoint){
        id = gamePoint.getId();
        name = gamePoint.getName();
        description = gamePoint.getDescription();
        latitude = gamePoint.getLatitude();
        longitude = gamePoint.getLongitude();
    }
}
