package com.RegistratieSysteem.setup;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("restservices")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.RegistratieSysteem.webservices,com.RegistratieSysteem.LoginSec");
        register(RolesAllowedDynamicFeature.class);
    }
}