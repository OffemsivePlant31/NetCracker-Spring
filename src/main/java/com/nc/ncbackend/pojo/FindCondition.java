package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class FindCondition {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    private String description;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_gamepoint")
    transient private GamePoint point;

    public FindCondition() {
    }

    public FindCondition(String description) {
        this.description = description;
    }

    @Deprecated
    public FindCondition(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GamePoint getPoint() {
        return point;
    }

    public void setPoint(GamePoint point) {
        this.point = point;
    }
}
