package com.stockechoes.api.auth;

import com.stockechoes.api.auth.forms.AuthCredentialsForm;
import com.stockechoes.api.auth.forms.RegisterForm;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(AuthCredentialsForm credentials) {

        authService.assertValidCredentials(credentials);

        NewCookie authCookies = authService.getAuthCookies(credentials);
        return Response.ok().cookie(authCookies).build();
    }

    @POST
    @Path("/register")
    public Response register(RegisterForm registerForm) {
        authService.createAccount(registerForm);

        return Response.status(Response.Status.CREATED).build();

    }
}
