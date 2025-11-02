package com.stockechoes.api.portfolio.portfolios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockechoes.api.accounts.Account;
import com.stockechoes.api.portfolio.transactions.Transaction;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name="se_portfolios")
public class Portfolio extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public Long id;

    private String name;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", nullable = false)
    public Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Portfolio() { }

    public Portfolio(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Portfolio{id=" + id + ", name='" + name + ", account='"+ account.id + "}";
    }
}
