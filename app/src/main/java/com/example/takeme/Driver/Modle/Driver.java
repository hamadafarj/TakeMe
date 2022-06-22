package com.example.takeme.Driver.Modle;

import java.io.Serializable;

public class Driver implements Serializable {

    private boolean isSelect;
    private String Id ;
    private String Name;
    private String PointOfStart;
    private String PointOfEnd;
    private String Number;
    private String Time;
    private String Phone;
    private String Token;

    public Driver() {
    }

    public Driver(boolean isSelect, String id, String name, String pointOfStart, String pointOfEnd, String number, String time, String phone) {
        this.isSelect = isSelect;
        Id = id;
        Name = name;
        PointOfStart = pointOfStart;
        PointOfEnd = pointOfEnd;
        Number = number;
        Time = time;
        Phone = phone;
    }

    public Driver(String id, String name, String pointOfStart, String pointOfEnd, String number, String time, String phone, String token) {
        Id = id;
        Name = name;
        PointOfStart = pointOfStart;
        PointOfEnd = pointOfEnd;
        Number = number;
        Time = time;
        Phone = phone;
        Token = token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getPointOfEnd() {
        return PointOfEnd;
    }

    public void setPointOfEnd(String pointOfEnd) {
        PointOfEnd = pointOfEnd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPointOfStart() {
        return PointOfStart;
    }

    public void setPointOfStart(String pointOfStart) {
        PointOfStart = pointOfStart;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

}
