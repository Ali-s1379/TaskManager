package com.example.taskmanager.models;


import java.util.UUID;

public class Account {
    private Long id;

    private UUID AccountId;

    private String Username;
    private String Password;
    public Account(Long id, UUID AccountId, String Username, String Password) {
        this.id = id;
        this.AccountId = AccountId;
        this.Username = Username;
        this.Password = Password;
    }
    public Account() {
        AccountId = UUID.randomUUID();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getAccountId() {
        return this.AccountId;
    }

    public void setAccountId(UUID AccountId) {
        this.AccountId = AccountId;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
