package com.RegistratieSysteem.webservices;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.AbstractMap;

@Path("content")
public class ContentResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContent() {
        var error = new AbstractMap.SimpleEntry<>("error", "a");
//        return Response.status(409).entity(error).build();

        return Response.ok(error).build();
    }
}
