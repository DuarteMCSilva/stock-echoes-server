package com.stockechoes.api.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockechoes.api.accounts.customer.Customer;
import com.stockechoes.api.accounts.customer.CustomerDto;
import com.stockechoes.api.portfolio.portfolios.Portfolio;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@Table(name = "se_accounts")
public class Account extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @JsonIgnore
    public Long id;

    @OneToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;

    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Portfolio> portfolios = new ArrayList<>();

    @JsonIgnore
    private Instant createdAt = Instant.now();

    public Account(Customer customer, String email) {
        this.customer = customer;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", email='" + email + ", owner= '"+ customer.toString() + "}";
    }

    public AccountDto toDto() {
        var dto = new AccountDto();
        CustomerDto customerDto = this.getCustomer().toDto(this.getEmail());
        dto.setCustomer(customerDto);
        dto.setPortfolios(this.getPortfolios());
        return dto;
    }
}
