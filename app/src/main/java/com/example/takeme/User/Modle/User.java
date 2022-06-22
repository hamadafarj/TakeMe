package com.example.takeme.User.Modle;

public class User {
    private String Name , Description , PhoneNumber , PointStart ;


    public User() {
    }

    public User(String name, String description, String phoneNumber, String pointStart) {
        Name = name;
        Description = description;
        PhoneNumber = phoneNumber;
        PointStart = pointStart;
    }






    public String getPointStart() {
        return PointStart;
    }

    public void setPointStart(String pointStart) {
        PointStart = pointStart;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
