package com.nc.ncbackend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class DemoData {

    @Id
    private long id;
    private String value;
    private LocalDate someDate;

    public DemoData() {
    }

    public DemoData(long id, String value, LocalDate someDate) {
        this.id = id;
        this.value = value;
        this.someDate = someDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getSomeDate() {
        return someDate;
    }

    public void setSomeDate(LocalDate someDate) {
        this.someDate = someDate;
    }
}
