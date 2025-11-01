package com.stockechoes.api.accounts;

import com.stockechoes.api.GenericApiException;
import com.stockechoes.api.auth.AuthContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/account")
public class AccountController {

    @Inject
    AccountRepository accountRepository;

    @Inject
    AuthContext authContext;

    @GET
    public Response getAccount() {
        var sub = authContext.getAccountId();

        var accountOpt = accountRepository.findByIdOptional(sub);

        if(accountOpt.isEmpty()) {
            throw new GenericApiException(Response.Status.NOT_FOUND, "No user account corresponds to authentication token");
        }

        return Response.ok().entity(accountOpt.get()).build();
    }
}
