package com.example.takeme.Notifications;

public class Sender {
    private DataNotification data;
    private String to;

    public Sender() {
    }

    public Sender(DataNotification data, String to) {
        this.data = data;
        this.to = to;
    }

    public DataNotification getData() {
        return data;
    }

    public void setData(DataNotification data) {
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
