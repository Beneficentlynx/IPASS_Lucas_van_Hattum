package com.RegistratieSysteem.LoginSec;

import com.RegistratieSysteem.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
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
        System.out.println(sc);
        System.out.println(sc.getUserPrincipal());
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                User current = (User) sc.getUserPrincipal();
                System.out.println(current.getName());
                data.put("username", current.getName());
                return Response.ok(data).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
