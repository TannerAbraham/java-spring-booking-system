package edu.wgu.d288.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "divisions")
@Data
public class Division {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;
    
    @Column(name = "division")
    private String division_name;
    
    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;
    
    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;
    
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "division")
    private Set<Customer> customers;
}
