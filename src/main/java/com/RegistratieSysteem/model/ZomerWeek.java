package com.RegistratieSysteem.model;

import java.io.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class ZomerWeek implements Principal, Serializable {

    private String zomerweekNaam;
    private LocalDate beginDatum;
    private LocalDate eindDatum;
    private String naamHI;
    private ArrayList<Plek> plekken = new ArrayList<>();
    public static ArrayList<ZomerWeek> alleZomerweken= new ArrayList<>();

    public ZomerWeek (String zomerweekNaam, LocalDate beginDatum, LocalDate eindDatum, String naamHI, ArrayList<Plek> plekken){
        this.zomerweekNaam = zomerweekNaam;
        this.beginDatum = beginDatum;
        this.eindDatum = eindDatum;
        this.naamHI = naamHI;
        this.plekken = plekken;
    }

    public static ZomerWeek GetZomerWeekByName(String name) {
        for(ZomerWeek zomerweek : alleZomerweken){
            if (zomerweek.getName().equals(name)){
                return zomerweek;
            }
        }
        return null;
    }

    public static ArrayList<ZomerWeek> getAllZomerweken() {
        return alleZomerweken;
    }

    public static void addZomerWeekToAll(ZomerWeek current) {
        alleZomerweken.add(current);
    }


    public LocalDate getBeginDatum(){
        return beginDatum;
    }

    public LocalDate getEindDatum(){
        return eindDatum;
    }

    public ArrayList<Plek> getPlekken(){
        return plekken;
    }

    @Override
    public String getName() {
        return zomerweekNaam;
    }

    public void addPlek(Plek plek){
        plekken.add(plek);
    }

    public void verwijderPlek(Plek plek){
        plekken.remove(plek);
    }

    @Override
    public String toString(){
        String returnString = "Zomerweeknaam: " + zomerweekNaam +" begindatum: " + beginDatum + " einddatum: " + eindDatum + " naam HI: " + naamHI;
        for (Plek p : plekken){
            returnString += " " + p.toString();
        }

        return returnString;
    }

    public static ArrayList<ZomerWeek> readZomerWeek() {
        ArrayList<ZomerWeek> zomerweken = new ArrayList<>();
        try {
            FileInputStream fi = new FileInputStream("C:\\Users\\lucas\\IdeaProjects\\IPASS_Lucas_Van_Hattum\\src\\main\\java\\com\\RegistratieSysteem\\DataStorage\\ZomerWeek.obj");
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects

            Boolean loop = true;
            try{
                while(loop) {
                    ZomerWeek zmrwk = (ZomerWeek) oi.readObject();

                    if (zmrwk != null) {
                        zomerweken.add(zmrwk);
                    } else {
                        loop = false;
                    }
                }

            }  catch( Exception e ) {
                System.out.println(e);
            }

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Error initializing stream");
        }
        ZomerWeek.alleZomerweken = zomerweken;
        return zomerweken;
    }

    public static void writeZomerWeek(ArrayList<ZomerWeek> zomerweken) throws IOException {
        FileOutputStream f = new FileOutputStream("C:\\Users\\lucas\\IdeaProjects\\IPASS_Lucas_Van_Hattum\\src\\main\\java\\com\\RegistratieSysteem\\DataStorage\\ZomerWeek.obj");
        ObjectOutputStream o = new ObjectOutputStream(f);

        // Write objects to file
        for (ZomerWeek zmrwk : zomerweken) {
            o.writeObject(zmrwk);
        }
        o.writeObject(null);
        o.close();
        f.close();
    }
}
