package com.RegistratieSysteem.model;

import java.util.Objects;

public class Plek {
    private String minDiploma;
    private String persoon;

    public Plek(String minDiploma){
        this.minDiploma = minDiploma;
    }

    public Boolean magInschrijven (String diploma){
        if (minDiploma.equals("i2")){
            return true;
        }
        else if (minDiploma.equals("I3")){
            if (diploma.equals("I3") || diploma.equals("I4") || diploma.equals("I4+") || diploma.equals("O")){
                return true;
            }
        }
        else if (minDiploma.equals("I4")){
            if (diploma.equals("I4") || diploma.equals("I4+") || diploma.equals("O")){
                return true;
            }
        }
        return false;
    }

    public void inschrijvenInstructeur(String naam, String diploma){
        if (magInschrijven(diploma) && persoon != null){
            persoon = naam;
        }
    }

    @Override
    public String toString(){
        return "Diploma: " + minDiploma + " naam: " + persoon;
    }

}
