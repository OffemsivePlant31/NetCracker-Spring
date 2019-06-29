package com.nc.ncbackend.service.impl;

import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.repository.PersonRepository;
import com.nc.ncbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public Person findOne(long id) {
        return personRepository.findOne(id);
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

    @Override
    public void deleteAll() {
        personRepository.deleteAll();
    }

    @Override
    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
