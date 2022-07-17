package com.example.taskmanager.models;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<Task>[] tasks = new ArrayList[3];

    public User(String username, String password){// throws Exception{
        this.username = username;
        this.password = password;
        ArrayList<Task> done = new ArrayList<>();
        ArrayList<Task> inProgress = new ArrayList<>();
        ArrayList<Task> toBeDone = new ArrayList<>();
        tasks[0] = done;
        tasks[1] = inProgress;
        tasks[2] = toBeDone;
    }

    public void setTasks(ArrayList<Task>[] tasks) {
        this.tasks = tasks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Task>[] getTasks() {
        return tasks;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User){
            return username.equals(((User) obj).username) && (password.equals(((User) obj).password));
        }else return false;
    }
}
