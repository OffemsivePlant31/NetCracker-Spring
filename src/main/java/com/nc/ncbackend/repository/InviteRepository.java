package com.nc.ncbackend.repository;

import com.nc.ncbackend.pojo.Invite;
import com.nc.ncbackend.pojo.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InviteRepository extends CrudRepository<Invite, Long> {

    List<Invite> findByAnsweredIsFalseAndPersonTo(Person personTo);
}
