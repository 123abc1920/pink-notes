package com.example.mycuteapplication;

import java.util.ArrayList;
import java.util.List;

public class Group {
    public List<String> elementsName = new ArrayList<>();
    public String name = "";
    public transient NewCheckBox checkBoxes = null;
    public transient List<MainElement> elements = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }
}
