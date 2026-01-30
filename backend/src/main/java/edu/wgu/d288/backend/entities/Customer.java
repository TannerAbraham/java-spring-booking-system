package edu.wgu.d288.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "customer_first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "customer_last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "address")
    @NotBlank(message = "Address is required")
    private String address;

    @Column(name = "postal_code")
    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Postal code must be valid (e.g., 12345 or 12345-6789)")
    private String postal_code;

    @Column(name = "phone")
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\(\\d{3}\\)\\d{3}-\\d{4}$", message = "Phone must be in format (XXX)XXX-XXXX")
    private String phone;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Cart> carts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}