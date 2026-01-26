package edu.wgu.d288.backend.controllers;

import edu.wgu.d288.backend.dao.ExcursionRepository;
import edu.wgu.d288.backend.entities.Excursion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/vacations")
public class VacationController {

    @Autowired
    private ExcursionRepository excursionRepository;

    @GetMapping("/{vacationId}/excursions")
    public Map<String, Object> getExcursionsByVacation(@PathVariable Long vacationId) {
        Set<Excursion> excursions = excursionRepository.findByVacation_Id(vacationId);

        Map<String, Object> response = new HashMap<>();
        Map<String, Set<Excursion>> embedded = new HashMap<>();
        embedded.put("excursions", excursions);
        response.put("_embedded", embedded);

        return response;
    }
}