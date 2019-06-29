package com.nc.ncbackend.controller;

import com.nc.ncbackend.dto.PersonDto;
import com.nc.ncbackend.pojo.Person;
import com.nc.ncbackend.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    PersonService personService;

    @GetMapping("/")
    public ResponseEntity<PersonDto> getPerson(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        Person person = personService.findByEmail(email);
        if((person != null)&&(password.equals(person.getPassword()))){
            return new ResponseEntity<PersonDto>(new PersonDto(person), HttpStatus.OK);
        }else{
            return new ResponseEntity<PersonDto>(HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(
            @PathVariable("id") long id
    ){
        Person person = personService.findOne(id);
        if(person != null){
            return new ResponseEntity<>(new PersonDto(person), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
