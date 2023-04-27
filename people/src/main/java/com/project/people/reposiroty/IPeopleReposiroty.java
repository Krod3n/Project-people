package com.project.people.reposiroty;

import com.project.people.Model.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPeopleReposiroty extends JpaRepository<People, Integer> {
    List<People> findBylastnameStartingWith(String lastname);
}
