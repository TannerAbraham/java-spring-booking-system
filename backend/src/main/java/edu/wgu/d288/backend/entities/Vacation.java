package edu.wgu.d288.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vacations")
@Getter
@Setter
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long id;

    @Column(name = "vacation_title")
    private String vacation_title;

    @Column(name = "description")
    private String description;

    @Column(name = "travel_fare_price")
    private Double travel_price;  // ✅ FIXED: Changed from travel_fare_price to travel_price

    @Column(name = "image_url")
    private String image_URL;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "last_update")
    private Date last_update;

    // CRITICAL FIX: @JsonIgnoreProperties prevents infinite JSON serialization loop
    // When serializing Vacation, it will include excursions but ignore excursion.vacation
    @OneToMany(mappedBy = "vacation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("vacation")
    private Set<Excursion> excursions = new HashSet<>();

    // Add any additional methods your project needs
    // For example, to add excursions:
    public void addExcursion(Excursion excursion) {
        this.excursions.add(excursion);
        excursion.setVacation(this);
    }
}