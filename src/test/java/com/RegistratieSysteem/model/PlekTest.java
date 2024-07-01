package com.RegistratieSysteem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PlekTest {
    Plek p;
    ZomerWeek zmrwk;
    Plek p2;
    User user;

    @BeforeEach
    void init() {
        zmrwk = new ZomerWeek("Week 1", LocalDate.parse("2024-10-10"), LocalDate.parse("2024-10-17"), "Lucas", new ArrayList<Plek>());
        p = new Plek("I4");
        zmrwk.addPlek(p);
        p2 = new Plek("I4");
        p2.inschrijvenInstructeur("Lucas", "I2");
        user = new User("test", "test", "I2");
    }


    @Test
    void schrijfUit() {
        p2.SchrijfUit();
        assertEquals(null, p2.getPersoon());
    }

    @Test
    void magInschrijven() {
        assertEquals(true, p.magInschrijven("I4"));
    }

    @Test
    void inschrijvenInstructeurTeLaagDiploma() {
        p.inschrijvenInstructeur("Lucas", "I3");
        assertEquals(null, p.getPersoon());
    }

    @Test
    void inschrijvenInstructeurCorrectDiploma() {
        p.inschrijvenInstructeur("Lucas", "O");
        assertEquals("Lucas", p.getPersoon());
    }

}