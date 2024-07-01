package com.RegistratieSysteem.model;

import java.io.Serializable;
import java.security.Principal;
import java.util.Objects;

public class Plek implements Serializable {
    private String minDiploma;
    private String persoon;

    public Plek(String minDiploma){
        this.minDiploma = minDiploma;
    }

    public String getPersoon(){
        return persoon;
    }

    public String getMinDiploma(){
        return minDiploma;
    }

    public void SchrijfUit(){
        persoon = null;
    }

    public Boolean magInschrijven (String diploma){
        switch (minDiploma) {
            case "I2" -> {
                return true;
            }
            case "I3" -> {
                if (diploma.equals("I3") || diploma.equals("I4") || diploma.equals("I4+") || diploma.equals("O")) {
                    return true;
                }
            }
            case "I4" -> {
                if (diploma.equals("I4") || diploma.equals("I4+") || diploma.equals("O")) {
                    return true;
                }
            }
            case "O" -> {
                if (diploma.equals("O")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void inschrijvenInstructeur(String naam, String diploma){
        if (persoon == null){
            if (magInschrijven(diploma)){
                persoon = naam;
            }
        }
    }

    @Override
    public String toString(){
        return "Diploma: " + minDiploma + " naam: " + persoon;
    }

}
