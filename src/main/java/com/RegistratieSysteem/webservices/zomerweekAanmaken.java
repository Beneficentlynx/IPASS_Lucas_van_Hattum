package com.RegistratieSysteem.webservices;

import com.RegistratieSysteem.model.User;
import com.RegistratieSysteem.model.Plek;
import com.RegistratieSysteem.model.ZomerWeek;
import com.RegistratieSysteem.webservices.dto.ZomerweekRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Path("zomerweek")
public class zomerweekAanmaken {
    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewZomerweek(@Context SecurityContext sc, ZomerweekRequest zomerweekRequest){
        Map<String, String> data = new HashMap<>();
        LocalDate beginDatum = LocalDate.parse(zomerweekRequest.beginDatum);
        LocalDate eindDatum = LocalDate.parse(zomerweekRequest.eindDatum);
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                User current1 = (User) sc.getUserPrincipal();
                if (current1.getName().equals("admin")){
                    ArrayList<String> userExists = new ArrayList<>();
                    ArrayList<Plek> plekken = new ArrayList<>();
                    int i = zomerweekRequest.I2;
                    int i2 = 0;
                    while (i2 < i){
                        i2 += 1;
                        Plek p1 = new Plek("I2");
                        plekken.add(p1);
                    }
                    i = zomerweekRequest.I3;
                    i2 = 0;
                    while (i2 < i){
                        i2 += 1;
                        Plek p1 = new Plek("I3");
                        plekken.add(p1);
                    }
                    i = zomerweekRequest.I4;
                    i2 = 0;
                    while (i2 < i){
                        i2 += 1;
                        Plek p1 = new Plek("I4");
                        plekken.add(p1);
                    }
                    i = zomerweekRequest.O;
                    i2 = 0;
                    while (i2 < i){
                        i2 += 1;
                        Plek p1 = new Plek("O");
                        plekken.add(p1);
                    }
                    ZomerWeek current = new ZomerWeek(zomerweekRequest.zomerweekNaam, beginDatum, eindDatum, zomerweekRequest.naamHI, plekken);
                    ZomerWeek existing = ZomerWeek.GetZomerWeekByName(current.getName());

                    if (ZomerWeek.getAllZomerweken().contains(current) || existing != null) {
                        return Response.status(Response.Status.CONFLICT).build();
                    }

                    ZomerWeek.addZomerWeekToAll(current);
                    return Response.ok().build();
                }
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path("getAllDates")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllUsedDates(@Context SecurityContext sc){
        Map<String, ArrayList<String>> data = new HashMap<>();
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                User current = (User) sc.getUserPrincipal();
                if (current.getName().equals("admin")) {
                    ArrayList<String> allDates = new ArrayList<>();
                    for (ZomerWeek zmrwk : ZomerWeek.getAllZomerweken()) {
                        allDates.add(zmrwk.getName() + " duurt van: " + zmrwk.getBeginDatum() + " tot " + zmrwk.getEindDatum());
                    }

                    data.put("allDates", allDates);
                    return Response.ok(data).build();
                }
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
