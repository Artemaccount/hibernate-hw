package com.example.hibernatehw.controller;

import com.example.hibernatehw.model.Person;
import com.example.hibernatehw.service.PersonCrudService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
public class AuthController {
    private final PersonCrudService service;

    public AuthController(PersonCrudService service) {
        this.service = service;
    }

    @Secured("ROLE_READ")
    @GetMapping("/persons/by-city")
    public List<Person> getPersonsByCity(@RequestParam("city") String city) {
        return service.findByCityOfLiving(city);
    }

    @RolesAllowed("WRITE")
    @GetMapping("/persons/by-age")
    public List<Person> getPersonsByAge(@RequestParam("age") int age) {
        return service.findByAgeLessThanOrderBy(age);
    }

    @PreAuthorize("hasRole('ROLE_WRITE') or hasRole('ROLE_DELETE')")
    @GetMapping("/persons/by-nameandsurname")
    public Optional<Person> getPersonsByNameAndSurname(@RequestParam("name") String name,
                                                       @RequestParam("surname") String surname) {
        return service.findByNameAndSurname(name, surname);
    }
}
