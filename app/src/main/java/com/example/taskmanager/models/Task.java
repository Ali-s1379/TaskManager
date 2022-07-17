package com.example.taskmanager.models;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private UUID id;
    private Date date;
    private boolean isDone;
    private boolean isInProgress;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }

    public Task() {
        id = UUID.randomUUID();
        date = new GregorianCalendar().getTime();
    }

    public Task(String title, String description, Date date, boolean isDone, boolean isInProgress) {
        this.title = title;
        this.description = description;
        this.id = UUID.randomUUID();
        this.date = date;
        this.isDone = isDone;
        this.isInProgress = isInProgress;
    }
}
