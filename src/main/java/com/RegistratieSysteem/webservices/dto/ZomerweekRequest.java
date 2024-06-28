package com.RegistratieSysteem.webservices.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZomerweekRequest {
    public String zomerweekNaam;
    public String beginDatum;
    public String eindDatum;
    public String naamHI;
    public int I2;
    public int I3;
    public int I4;
    public int O;
}
