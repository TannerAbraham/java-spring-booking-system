package edu.wgu.d288.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "excursions")
@Getter
@Setter
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excursion_id")
    private Long id;

    @Column(name = "excursion_title")
    private String excursion_title;

    @Column(name = "excursion_price")
    private Double excursion_price;

    @Column(name = "image_url")
    private String image_URL;  // ✅ FIXED: Changed from image_url to image_URL

    @Column(name = "create_date")
    private Date create_date;

    @Column(name = "last_update")
    private Date last_update;

    // CRITICAL FIX: @JsonIgnoreProperties prevents infinite JSON serialization loop
    // When serializing Excursion, it will include vacation but ignore vacation.excursions
    @ManyToOne
    @JoinColumn(name = "vacation_id")
    @JsonIgnoreProperties("excursions")
    private Vacation vacation;

    // ManyToMany relationship with CartItem
    // CRITICAL FIX: Also prevent loop with cart items
    @ManyToMany(mappedBy = "excursions")
    @JsonIgnoreProperties({"excursions", "cart"})
    private Set<CartItem> cartItems = new HashSet<>();

    // Add any additional methods your project needs
    // For example, if you need to add cartItems:
    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.getExcursions().add(this);
    }
}