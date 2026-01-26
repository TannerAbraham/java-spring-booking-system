package edu.wgu.d288.backend.controllers;

import edu.wgu.d288.backend.dao.ExcursionRepository;
import edu.wgu.d288.backend.entities.Excursion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/vacations")
public class VacationController {

    @Autowired
    private ExcursionRepository excursionRepository;

    @GetMapping("/{vacationId}/excursions")
    public Map<String, Object> getExcursionsByVacation(@PathVariable Long vacationId) {
        Set<Excursion> excursions = excursionRepository.findByVacation_Id(vacationId);

        // Convert to list and add _links to each excursion
        List<Map<String, Object>> excursionList = excursions.stream().map(excursion -> {
            Map<String, Object> excursionMap = new HashMap<>();
            excursionMap.put("id", excursion.getId());
            excursionMap.put("excursion_title", excursion.getExcursion_title());
            excursionMap.put("excursion_price", excursion.getExcursion_price());
            excursionMap.put("image_URL", excursion.getImage_URL());
            excursionMap.put("create_date", excursion.getCreate_date());
            excursionMap.put("last_update", excursion.getLast_update());

            // Add _links
            Map<String, Object> links = new HashMap<>();
            Map<String, String> self = new HashMap<>();
            self.put("href", "http://localhost:8080/api/excursions/" + excursion.getId());
            links.put("self", self);
            excursionMap.put("_links", links);

            return excursionMap;
        }).collect(Collectors.toList());

        // Build response
        Map<String, Object> embedded = new HashMap<>();
        embedded.put("excursions", excursionList);

        Map<String, Object> response = new HashMap<>();
        response.put("_embedded", embedded);

        return response;
    }
}