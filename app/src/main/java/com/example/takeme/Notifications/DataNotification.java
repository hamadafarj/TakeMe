package com.example.takeme.Notifications;

public class DataNotification {
    private String user , title , body ,notificationType;
    private Integer icon;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public DataNotification(String user, String title, String body, String notificationType, Integer icon) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.notificationType = notificationType;
        this.icon = icon;
    }

    public DataNotification() {
    }
}
