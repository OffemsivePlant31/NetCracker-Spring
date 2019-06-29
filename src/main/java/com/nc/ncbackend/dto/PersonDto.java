package com.nc.ncbackend.dto;

import com.nc.ncbackend.pojo.Person;

public class PersonDto {


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String name;
    private String email;
    private long photo;

    public PersonDto() {
    }

    public PersonDto(Person person) {
        parse(person);
    }

    public void parse(Person person){
        id = person.getId();
        name = person.getName();
        email = person.getEmail();
        if(person.getPhoto() != null){
            photo = person.getPhoto().getId();
        }


    }
}
