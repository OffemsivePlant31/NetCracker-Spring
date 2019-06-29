package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class GamePeriod {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;
    private LocalDateTime timeStart;
    private long delay;

    public GamePeriod(){

    }


    public GamePeriod(LocalDateTime timeStart, long delay) {
        this.timeStart = timeStart;
        this.delay = delay;
    }

    @Deprecated
    public GamePeriod(long id, LocalDateTime timeStart, long delay) {
        this.id = id;
        this.timeStart = timeStart;
        this.delay = delay;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
