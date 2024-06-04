package com.RegistratieSysteem.LoginSec;

import com.RegistratieSysteem.model.LoginRequest;
import com.RegistratieSysteem.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;


import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.AbstractMap;
import java.util.Calendar;

@Path("authentication")
public class AuthenticationResource {
    // Generate key
    public final static Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LoginRequest LoginRequest) {
        System.out.println(User.getAllUsers());
        try {
            Boolean correctDetails = User.checkLogin(LoginRequest.username, LoginRequest.password);
            System.out.println(correctDetails);
            if (!correctDetails) {
                throw new IllegalArgumentException(("No user found"));
            }
            Boolean admin = false;
            if (LoginRequest.username.equals("admin")){
                admin = true;
            }
            String token = createToken(LoginRequest.username, admin);
            return Response.ok(new AbstractMap.SimpleEntry<>("JWT", token)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String username, Boolean admin) {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 60);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("admin", admin)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}