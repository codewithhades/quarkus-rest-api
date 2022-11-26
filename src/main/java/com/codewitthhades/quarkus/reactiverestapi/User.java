package com.codewitthhades.quarkus.reactiverestapi;

import java.util.UUID;

class User {

    private String id;
    private String name;
    private String surname;

    //For Jackson
    private User() {
    }

    public User(String name, String surname) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}