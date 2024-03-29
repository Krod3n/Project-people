package com.project.people.controller;

import com.project.people.Model.People;
import com.project.people.reposiroty.IPeopleReposiroty;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@SpringBootApplication
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private IPeopleReposiroty peopleReposiroty;

    @GetMapping("/find/{id}")
    public Optional<People> findPeople(@PathVariable Integer id){
        return peopleReposiroty.findById(id);
    }

    @GetMapping("/all")
    public List<People> getAllPeople() {
        return peopleReposiroty.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        peopleReposiroty.deleteById(id);
    }

    record NewPeople(String firstname, String lastname, String email, String phone, String country) {
    }

    @PostMapping("/add")
    public void addPeople(@RequestBody NewPeople request) {
        People people = new People();
        people.setFirstname(request.firstname());
        people.setLastname(request.lastname());
        people.setEmail(request.email());
        people.setPhone(request.phone());
        people.setCountry(request.country());
        peopleReposiroty.saveAndFlush(people);

    }

    @PutMapping("/edit/{id}")
    public void editPeople(@PathVariable("id") Integer id, @RequestBody NewPeople request) {
        People edit = peopleReposiroty.findById(id).orElseThrow();
        edit.setFirstname(request.firstname());
        edit.setLastname(request.lastname());
        edit.setEmail(request.email());
        edit.setPhone(request.phone());
        edit.setCountry(request.country());

        peopleReposiroty.saveAndFlush(edit);

    }

    @GetMapping("name/{lastname}")
    public List<People> findLastname(@PathVariable("lastname") String lastname) {
       return peopleReposiroty.findBylastnameStartingWith(lastname);
    }

    @GetMapping("/pays/{pays}")
    public List<People> peoplePays(@PathVariable("pays")String pays) {
        List<People> listpeople = peopleReposiroty.findAll();
                List<People> country = listpeople.stream()
                .filter(p->p.getCountry().equals(pays))
                .collect(Collectors.toList());
        return country;
    }

    @GetMapping("/{country}/{telephone}")
    public List<People> getPeopleCountrylastname(@PathVariable String country,@PathVariable String telephone){
        List<People> peopleList = peopleReposiroty.findByCountry(country);

        return peopleList.stream()
                .filter(people -> people.getPhone().startsWith(telephone))
                .collect(Collectors.toList());
    }

}