package edu.wgu.d288.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "last_update")
    private Date last_update;

    // ManyToOne relationship with Cart
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnoreProperties("cartItems")
    private Cart cart;

    // ManyToOne relationship with Vacation
    @ManyToOne
    @JoinColumn(name = "vacation_id")
    @JsonIgnoreProperties({"excursions", "cartItems"})
    private Vacation vacation;

    // CRITICAL FIX: @JsonIgnoreProperties prevents infinite JSON serialization loop
    // ManyToMany relationship with Excursion
    @ManyToMany
    @JoinTable(
            name = "excursion_cartitem",
            joinColumns = @JoinColumn(name = "cart_item_id"),
            inverseJoinColumns = @JoinColumn(name = "excursion_id")
    )
    @JsonIgnoreProperties({"cartItems", "vacation"})
    private Set<Excursion> excursions = new HashSet<>();

    // Add any additional methods your project needs
    public void addExcursion(Excursion excursion) {
        this.excursions.add(excursion);
        excursion.getCartItems().add(this);
    }
}