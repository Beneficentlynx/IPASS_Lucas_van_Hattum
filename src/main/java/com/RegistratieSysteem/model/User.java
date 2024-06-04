package com.RegistratieSysteem.model;

import javax.security.auth.Subject;
import java.io.*;
import java.security.Principal;
import java.util.ArrayList;

public class User implements Principal, Serializable {
    private String name;
    private String password;

    private String diploma;
    private static ArrayList<User> AllUsers = new ArrayList<>();
    private int usernummer;
    private static int usercounter;

    public User(String name, String password, String diploma) {
        this.name = name;
        this.password = password;
        this.diploma = diploma;
    }

    public String getName(){
        return name;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }

    public String getPassword(){
        return password;
    }

    public static ArrayList<User> getAllUsers(){
        return AllUsers;
    }

    public static void addUserToAll(User user){
        AllUsers.add(user);
    }


    public static User getUserByName(String username){
        for(User u : AllUsers){
            if ((u.getName()).equals(username)){
                return u;
            }
        }
        return null;
    }

    public static Boolean checkLogin(String username, String password){
        User correctUsername = getUserByName(username);
        if (correctUsername != null){
            if (correctUsername.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public static void setAllUsers(ArrayList<User> users){
        AllUsers = users;
    }

    public static ArrayList<User> readUser() {
        ArrayList<User> users = new ArrayList<>();
        try {
            FileInputStream fi = new FileInputStream("C:\\Users\\lucas\\IdeaProjects\\IPASS_Lucas_Van_Hattum\\src\\main\\java\\com\\RegistratieSysteem\\DataStorage\\User.obj");
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects

            Boolean loop = true;
            try{
                while(loop) {
                    User usr = (User) oi.readObject();

                    if (usr != null) {
                        users.add(usr);
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
        User.AllUsers = users;
        return users;
    }

    public static void writeUser(ArrayList<User> users) throws IOException {
        FileOutputStream f = new FileOutputStream("C:\\Users\\lucas\\IdeaProjects\\IPASS_Lucas_Van_Hattum\\src\\main\\java\\com\\RegistratieSysteem\\DataStorage\\User.obj");
        ObjectOutputStream o = new ObjectOutputStream(f);

        // Write objects to file
        for (User usr : users) {
            o.writeObject(usr);
        }
        o.writeObject(null);
        o.close();
        f.close();
    }


}

