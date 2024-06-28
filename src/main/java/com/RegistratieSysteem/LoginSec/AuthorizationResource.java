package com.RegistratieSysteem.LoginSec;

import com.RegistratieSysteem.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Path("authorization")
public class AuthorizationResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response isAuthorized(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("getuser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUser(@Context SecurityContext sc) {
        Map<String, String> data = new HashMap<>();
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                User current = (User) sc.getUserPrincipal();
                data.put("username", current.getName());
                return Response.ok(data).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("getallusers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getallusers(@Context SecurityContext sc) {
        Map<String, ArrayList<String>> data = new HashMap<>();
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                User current = (User) sc.getUserPrincipal();
                if (current.getName().equals("admin")){
                    ArrayList<String> allusernames = new ArrayList<>();
                    ArrayList<User> allUsers = new ArrayList<>();
                    allUsers = User.getAllUsers();
                    for(User user : allUsers) {
                        allusernames.add(user.getName());
                    }
                    data.put("allUserNames", allusernames);
                    return Response.ok(data).build();
                }
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
