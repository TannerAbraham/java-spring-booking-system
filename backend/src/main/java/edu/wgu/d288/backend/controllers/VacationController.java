package edu.wgu.d288.backend.controllers;

import edu.wgu.d288.backend.dao.ExcursionRepository;
import edu.wgu.d288.backend.entities.Excursion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/vacations")
public class VacationController {

    @Autowired
    private ExcursionRepository excursionRepository;

    @GetMapping("/{vacationId}/excursions")
    public Set<Excursion> getExcursionsForVacation(@PathVariable Long vacationId) {
        System.out.println("===============================================");
        System.out.println("Fetching excursions for vacation ID: " + vacationId);

        Set<Excursion> excursions = excursionRepository.findByVacation_Id(vacationId);

        System.out.println("Found " + excursions.size() + " excursions");
        excursions.forEach(e -> System.out.println("  - " + e.getExcursion_title()));
        System.out.println("===============================================");

        return excursions;
    }
}