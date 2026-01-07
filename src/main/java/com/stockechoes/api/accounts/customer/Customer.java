package com.stockechoes.api.accounts.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "se_customers")
@EqualsAndHashCode(callSuper = true)
public class Customer extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @JsonIgnore
    public Long id;

    private String firstName;

    private String lastName;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public CustomerDto toDto(String email) {
        var dto = new CustomerDto();
        dto.setLastName(this.getLastName());
        dto.setFirstName(this.getFirstName());
        dto.setEmail(email);
        return dto;
    }
}
