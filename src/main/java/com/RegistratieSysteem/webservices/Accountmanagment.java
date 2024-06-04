package com.RegistratieSysteem.webservices;

import com.RegistratieSysteem.model.RegistrationRequest;
import com.RegistratieSysteem.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
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
    public Response createNewAccount(RegistrationRequest registrationRequest){
        ArrayList<String> userExists = new ArrayList<>();

        User current = new User(registrationRequest.username, registrationRequest.password, registrationRequest.diploma);
        User existing = User.getUserByName(current.getName());

        if (User.getAllUsers().contains(current) || existing != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        User.addUserToAll(current);
        System.out.println(User.getAllUsers());
        return Response.ok().build();
    }
}