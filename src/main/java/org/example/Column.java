package org.example;

public class Column {
    private final String name;
    private final String type; //int or varchar

    public Column(String name, String type) {
        this.name = name;
        this.type = type.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

