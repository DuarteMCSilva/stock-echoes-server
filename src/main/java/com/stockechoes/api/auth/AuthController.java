package com.stockechoes.api.auth;

import com.stockechoes.api.auth.user.UserCredentialsForm;
import com.stockechoes.api.auth.user.UserRegisterForm;
import com.stockechoes.api.auth.user.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthController {

    @Inject
    AuthService authService;

    /*@Inject
    UserRepository userRepository;*/

    @POST
    @Path("/login")
    public Response login(UserCredentialsForm credentials) {

        /*Optional<User> user = userRepository.findByUsernameOptional(credentials.getUsername());

        if(user.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }*/

        var authCookies = authService.getAuthCookies(credentials);
        return Response.ok().cookie(authCookies).build();
    }

    @POST
    @Path("/register")
    public String register(UserRegisterForm registerForm) {
        throw new RuntimeException("Not implemented yet.");
    }
}
