package com.stockechoes.api.accounts;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/account")
public class AccountController {

    @Inject
    AccountRepository accountRepository;

    @Inject
    AccountService accountService;

    @GET
    public Response getAccount() {
        AccountDto account = accountService.getAccountDetails();

        return Response.ok().entity(account).build();
    }

    @GET
    @Path("/all")
    @IfBuildProfile("dev") // TODO: ERASE - just for dev
    public List<Account> all() {
        return accountRepository.listAll();
    }
}
