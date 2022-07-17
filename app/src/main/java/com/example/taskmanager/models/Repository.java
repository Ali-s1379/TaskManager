package com.example.taskmanager.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static Repository instance;
    private static List<User> userList;
    private static User currentUser;


    private Repository(){
        userList = new ArrayList<>();
    }
    //singleton class
    public static Repository getInstance(){
        if (instance == null){
            instance = new Repository();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public void addUser(User user) throws Exception{
        if (checkUsername(user)){
            throw new Exception("User Already Registered");
        }else{
            userList.add(user);
        }
    }
    public boolean login(String username,String password){
        try {
            User tempUser = new User(username,password);
            if (userList.contains(tempUser)) {
                setCurrentUser(tempUser);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private boolean checkUsername(User user){
        for (User users:userList){
            if (users.getUsername().equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }

    public Task getTask(UUID uuid) {
        for (Task task : currentUser.getTasks()[0])
            if (task.getId().equals(uuid))
                return task;
        for (Task task : currentUser.getTasks()[1])
            if (task.getId().equals(uuid))
                return task;
        for (Task task : currentUser.getTasks()[2])
            if (task.getId().equals(uuid))
                return task;

        return null;
    }

    public void addTask(Task task) {
        if (task.isDone()) {
            addDoneTask(task);
        }else if (task.isInProgress()) {
            addInProgressTask(task);
        }else {
            addToBeDoneTask(task);
        }
    }

    public void addDoneTask(Task task) {
        currentUser.getTasks()[0].add(task);
    }

    public void addInProgressTask(Task task) {
        currentUser.getTasks()[1].add(task);
    }

    public void addToBeDoneTask(Task task) {
        currentUser.getTasks()[2].add(task);
    }

    public List<Task> getDoneTasks() {
        return currentUser.getTasks()[0];
    }

    public List<Task> getInProgressTasks() {
        return currentUser.getTasks()[1];
    }

    public List<Task> getToBeDoneTasks() {
        return currentUser.getTasks()[2];
    }

    public void updateTask(Task task) throws Exception {
        Task temp = getTask(task.getId());
        if (temp == null)
            throw new Exception("This task does not exist");

        temp.setTitle(task.getTitle());
        temp.setDescription(task.getDescription());
        temp.setDate(task.getDate());
        temp.setDone(task.isDone());
        temp.setInProgress(task.isInProgress());
    }

    public void deleteTask(String status, Task task) throws Exception {
        switch (status) {
            case "done":
                Task temp0 = getTask(task.getId());
                if (temp0 == null)
                    throw new Exception("This task does not exist");

                currentUser.getTasks()[0].remove(temp0);
                break;
            case "inProgress":
                Task temp1 = getTask(task.getId());
                if (temp1 == null)
                    throw new Exception("This task does not exist");

                currentUser.getTasks()[1].remove(temp1);
                break;
            case "toBeDone":
                Task temp2 = getTask(task.getId());
                if (temp2 == null)
                    throw new Exception("This task does not exist");

                currentUser.getTasks()[2].remove(temp2);
                break;
        }

    }

//    public void deleteAll() {
//        database.execSQL("delete from "+ TaskDBSchema.Task.USER);
//    }

    public static void main(String[] args) {
        User temp = new User("ali","abcd");
        userList.add(temp);
        setCurrentUser(temp);

    }
}
