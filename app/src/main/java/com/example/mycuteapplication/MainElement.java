package com.example.mycuteapplication;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainElement {
    public String name = "", text = "";
    public TypesOfFiles type;
    public List<Integer> italicFormatText = new ArrayList<>();
    public List<Integer> boldFormatText = new ArrayList<>();
    public List<Integer> boldItalicFormatText = new ArrayList<>();
    public List<Integer> underLineFormatText = new ArrayList<>();
    public List<Integer> preference = new ArrayList<>(Arrays.asList(0));
    public List<String> checkBoxes = new ArrayList<>();
    public transient boolean toDelete;

    public MainElement(TypesOfFiles type) {
        this.type = type;
    }

    public MainElement(String name, String text, TypesOfFiles type, List<JsonElement> italicFormatText, List<JsonElement> boldFormatText, List<JsonElement> boldItalicFormatText, List<JsonElement> underLineFormatText, List<JsonElement> preference) {
        this.name = name;
        this.text = text;
        this.type = type;

        for (int i = 0; i < preference.size(); i++) {
            this.preference.set(i, preference.get(i).getAsInt());
        }

        for (JsonElement i : italicFormatText) {
            this.italicFormatText.add(i.getAsInt());
        }
        for (JsonElement i : boldFormatText) {
            this.boldFormatText.add(i.getAsInt());
        }
        for (JsonElement i : boldItalicFormatText) {
            this.boldItalicFormatText.add(i.getAsInt());
        }
        for (JsonElement i : underLineFormatText) {
            this.underLineFormatText.add(i.getAsInt());
        }
    }

    public MainElement(String name, String text, TypesOfFiles type, List<JsonElement> checkBoxes, List<JsonElement> preference) {
        this.name = name;
        this.text = text;
        this.type = type;

        for (int i = 0; i < preference.size(); i++) {
            this.preference.set(i, preference.get(i).getAsInt());
        }

        for (JsonElement i : checkBoxes) {
            this.checkBoxes.add(i.getAsString());
        }
    }
}
