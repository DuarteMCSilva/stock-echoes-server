package com.stockechoes.api.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockechoes.api.accounts.customer.Customer;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
public class Account extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @JsonIgnore
    public Long id;

    @OneToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer owner;

    private String email;

    @JsonIgnore
    private Instant createdAt = Instant.now();

    public Account(Customer customer, String email) {
        this.owner = customer;
        this.email = email;
    }
}
