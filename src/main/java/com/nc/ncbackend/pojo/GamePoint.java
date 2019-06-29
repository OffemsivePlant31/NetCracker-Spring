package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GamePoint {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    private String name;
    private String description;
    private float latitude;
    private float longitude;


    @OneToMany( cascade = CascadeType.ALL )
    @JoinColumn(name="id_gamepoint")
    private List<FindCondition> findConditions;

    @OneToMany( cascade = CascadeType.ALL )
    @JoinColumn(name="id_gamepoint")
    private List<CheckCondition> checkConditions;

    public GamePoint() {

    }

    public GamePoint(String name, String description, float latitude, float longitude) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    @Deprecated
    public GamePoint(long id, String name, String description, float latitude, float longitude) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

    public List<FindCondition> getFindConditions() {
        if(findConditions == null) findConditions = new ArrayList<>();
        return findConditions;
    }

    public void setFindConditions(List<FindCondition> findConditions) {
        this.findConditions = findConditions;
    }

    public List<CheckCondition> getCheckConditions() {
        if(checkConditions == null) checkConditions = new ArrayList<>();
        return checkConditions;
    }

    public void setCheckConditions(List<CheckCondition> checkConditions) {
        this.checkConditions = checkConditions;
    }
}
