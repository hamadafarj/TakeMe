package com.example.ajialapp.modle;

import java.io.Serializable;

public class Employee implements Serializable {


    public final static String COL_ID="id";
    public final static String COL_NAME="name";
    public final static String COL_TITLE="title";
    public final static String COL_PASSWORD="password";

    public final static String TABLE_NAME="employee";
    public final static String DB_NAME="government";


    public final static String CREATE_TABLE= "CREATE TABLE " + TABLE_NAME + "(" +
            COL_ID + " TEXT NOT NULL," +
            COL_NAME + " TEXT NOT NULL," +
            COL_TITLE + " TEXT NOT NULL," +
            COL_PASSWORD + " TEXT NOT NULL"+
            ")";

    private String id;
    private String name;
    private String title;
    private String password;

    public Employee() {
    }

    public Employee(String id, String name, String title, String password) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
