package com.mebel.shop.models;

public enum Stage {
    OPEN ("Open"),
    WORKING ("Accepted"),
    DELIVERED ("Delivered"),
    CLOSED ("Denied");

    private final String name;

    Stage(String name) {this.name = name;}

    public String getName() {return name;}
}
