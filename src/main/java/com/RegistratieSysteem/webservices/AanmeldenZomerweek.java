package com.RegistratieSysteem.webservices;

import com.RegistratieSysteem.model.User;
import com.RegistratieSysteem.model.Plek;
import com.RegistratieSysteem.model.ZomerWeek;
import com.RegistratieSysteem.webservices.dto.ZomerweekRequest;
import com.RegistratieSysteem.webservices.dto.aanmeldenSelect;
import com.RegistratieSysteem.webservices.dto.inschrijvenOpPlek;
import org.glassfish.jersey.message.internal.StringBuilderUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Path("aanmelden")
public class AanmeldenZomerweek {
    @Path("getAllZomerweken")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllUsedDates(@Context SecurityContext sc){
        Map<String, ArrayList<String>> data = new HashMap<>();
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                ArrayList<String> allNames = new ArrayList<>();
                for (ZomerWeek zmrwk : ZomerWeek.getAllZomerweken()){
                    allNames.add(zmrwk.getName());
                }
                data.put("allNames", allNames);
                System.out.println(data);
                return Response.ok(data).build();

            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path("selectZomerweken")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response selectZomerweken(@Context SecurityContext sc, aanmeldenSelect aanmeldenSelect){

        Map<String, ArrayList<Map<String, String>>> data = new HashMap<>();
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                ArrayList<Plek> alleZomerweekPlekken = new ArrayList<>();
                ZomerWeek zmrwk = ZomerWeek.GetZomerWeekByName(aanmeldenSelect.zomerWeekNaam);
                alleZomerweekPlekken = zmrwk.getPlekken();
                ArrayList<Map<String, String>> returnPlekken = new ArrayList<>();
                for (Plek p : alleZomerweekPlekken){
                    Map<String, String> eenPlekMaarDanInEenArray = new HashMap<>();
                    eenPlekMaarDanInEenArray.put(p.getMinDiploma(),p.getPersoon());
                    returnPlekken.add(eenPlekMaarDanInEenArray);
                }

                data.put("allPlekken", returnPlekken);
                return Response.ok(data).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path("schrijfInOpPlek")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response inschrijvenOpPlek(@Context SecurityContext sc, inschrijvenOpPlek inschrijvenopplek ){
        if (sc.getUserPrincipal() != null) {
            if (sc.getUserPrincipal() instanceof User) {
                User user = (User) sc.getUserPrincipal();
                ZomerWeek actieveZomerweek = ZomerWeek.GetZomerWeekByName(inschrijvenopplek.week);
                if (!user.getName().equals("admin")) {
                    String instructeurniveau = "";
                    if ("O ".equals(inschrijvenopplek.gekozenPlek.charAt(0) + " ")) {
                        instructeurniveau = "O";
                    } else {
                        instructeurniveau = "I" + inschrijvenopplek.gekozenPlek.charAt(1);
                    }
                    assert actieveZomerweek != null;
                    for (Plek p : actieveZomerweek.getPlekken()){
                        if(p.getPersoon() != null) {
                            if (p.getPersoon().equals(user.getName())) {
                                return Response.status(Response.Status.UNAUTHORIZED).build();
                            }
                        }
                    }
                    System.out.println("2");
                    if (inschrijvenopplek.gekozenPlek.equals(instructeurniveau + " plek: Geen inschrijving bekend")) {
                        for (Plek p : actieveZomerweek.getPlekken()) {
                            if(p.getPersoon() == null) {
                                if (p.getMinDiploma().equals(instructeurniveau)) {
                                    System.out.println("mag inschrijven: " + p.magInschrijven(user.getDiploma()));
                                    if (p.magInschrijven(user.getDiploma())) {

                                        actieveZomerweek.verwijderPlek(p);
                                        System.out.println(p);
                                        p.inschrijvenInstructeur(user.getName(), user.getDiploma());
                                        System.out.println(p);
                                        actieveZomerweek.addPlek(p);

                                        break;
                                    }
                                }
                            }
                        }

                    }
                    System.out.println(actieveZomerweek.getPlekken());
                    return Response.ok().build();
                }

             }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
