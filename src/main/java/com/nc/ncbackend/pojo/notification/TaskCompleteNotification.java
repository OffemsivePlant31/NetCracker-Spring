package com.nc.ncbackend.pojo.notification;

import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.pojo.Team;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TaskCompleteNotification {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_team")
    private Team team;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_person")
    private Person person;

    private int countDone;
    private LocalDateTime timeDone;
    private boolean checked;

    public TaskCompleteNotification(Team team, Person person, int countDone, LocalDateTime timeDone) {
        this.team = team;
        this.person = person;
        this.countDone = countDone;
        this.timeDone = timeDone;
        this.checked = false;
    }

    public TaskCompleteNotification() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getCountDone() {
        return countDone;
    }

    public void setCountDone(int countDone) {
        this.countDone = countDone;
    }

    public LocalDateTime getTimeDone() {
        return timeDone;
    }

    public void setTimeDone(LocalDateTime timeDone) {
        this.timeDone = timeDone;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
