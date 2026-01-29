package edu.wgu.d288.backend.dao;

import edu.wgu.d288.backend.entities.Excursion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin("http://localhost:4200")
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    // Method name uses underscore to access nested property: vacation.id
    // Parameter name must match what's used in the URL query string
    Set<Excursion> findByVacation_Id(@Param("vacationId") Long vacationId);
}