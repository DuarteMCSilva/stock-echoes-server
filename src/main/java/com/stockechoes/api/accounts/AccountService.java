package com.stockechoes.api.accounts;

import com.stockechoes.api.GenericApiException;
import com.stockechoes.api.auth.AuthContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository accountRepository;

    @Inject
    AuthContext authContext;

    public Account getAccountFromContextOrThrow() {
        Long accountId = authContext.getAccountId();

        if(accountId == null) {
            throw new GenericApiException(Response.Status.BAD_REQUEST, "Undefined account id!");
        }

        return accountRepository.findByIdOptional(accountId)
                .orElseThrow( () -> new GenericApiException(Response.Status.BAD_REQUEST, "No account found for a given id"));
    }
}
