package com.nc.ncbackend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {

    @Id
    @GenericGenerator(name="inc" , strategy="increment")
    @GeneratedValue(generator="inc")
    private long id;

    private String name;
    private String email;
    private String password;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_role")
    private Role role;

    @ManyToOne( cascade = CascadeType.ALL )
    @JoinColumn(name="id_photo")
    private Photo photo;

    @ManyToMany(mappedBy="users")
    private List<Team> teams;

    public Person() {

    }

    public Person(String name, String email, String password) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Deprecated
    public Person(long id, String name, String email, String password) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
