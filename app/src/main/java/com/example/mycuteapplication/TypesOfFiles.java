package com.example.mycuteapplication;

public enum TypesOfFiles {
    GRAPHIC("g"),
    WRITE("w"),
    LIST("l");

    private String type;

    TypesOfFiles(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
