package com.stockechoes.api.transactions;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionsRepository implements PanacheRepository<Transaction> {
}
