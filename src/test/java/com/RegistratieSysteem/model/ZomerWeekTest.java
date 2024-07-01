package com.RegistratieSysteem.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ZomerWeekTest {
    ZomerWeek zmrwk;
    Plek p1;
    Plek p2;

    @BeforeEach
    public void init(){
        zmrwk = new ZomerWeek("Week 1", LocalDate.parse("2024-10-10"), LocalDate.parse("2024-10-17"), "Lucas", new ArrayList<Plek>());
        ZomerWeek.addZomerWeekToAll(zmrwk);
        p1 = new Plek("I2");
        p2 = new Plek("I4");
        zmrwk.addPlek(p2);
    }

    @Test
    void getZomerWeekByName() {
        assertEquals(zmrwk.toString(), ZomerWeek.GetZomerWeekByName("Week 1").toString());
    }

    @Test
    void addPlek() {
        zmrwk.addPlek(p1);
        assertEquals(2, zmrwk.getPlekken().toArray().length);
    }

    @Test
    void verwijderPlek() {
        zmrwk.verwijderPlek(p2);
        assertEquals(0, zmrwk.getPlekken().toArray().length);
    }
}