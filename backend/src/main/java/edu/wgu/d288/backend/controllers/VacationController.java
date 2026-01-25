package edu.wgu.d288.backend.controllers;

import edu.wgu.d288.backend.dao.ExcursionRepository;
import edu.wgu.d288.backend.entities.Excursion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/vacations")
public class VacationController {

    @Autowired
    private ExcursionRepository excursionRepository;

    @GetMapping("/{vacationId}/excursions")
    public Set<Excursion> getExcursionsByVacation(@PathVariable Long vacationId) {
        return excursionRepository.findByVacation_Id(vacationId);
    }
}