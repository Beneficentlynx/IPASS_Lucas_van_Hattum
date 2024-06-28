package com.RegistratieSysteem.webservices;

import com.RegistratieSysteem.webservices.dto.RegistrationRequest;
import com.RegistratieSysteem.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Path("account")
public class Accountmanagment {

    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewAccount(@Context SecurityContext sc, RegistrationRequest registrationRequest){
        Map<String, String> data = new HashMap<>();
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                User current1 = (User) sc.getUserPrincipal();
                System.out.println(current1.getName());
                if (current1.getName().equals("admin")){
                    ArrayList<String> userExists = new ArrayList<>();

                    User current = new User(registrationRequest.username, registrationRequest.password, registrationRequest.diploma);
                    User existing = User.getUserByName(current.getName());

                    if (User.getAllUsers().contains(current) || existing != null) {
                        return Response.status(Response.Status.CONFLICT).build();
                    }

                    User.addUserToAll(current);
                    return Response.ok().build();
                }
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}







