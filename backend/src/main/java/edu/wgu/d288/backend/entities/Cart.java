package edu.wgu.d288.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "carts")
@Data
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    
    @Column(name = "package_price")
    private BigDecimal package_price;
    
    @Column(name = "party_size")
    private Integer party_size;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;
    
    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;
    
    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;
    
    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItem> cartItems;
}
