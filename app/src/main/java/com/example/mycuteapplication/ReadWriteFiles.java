package com.example.mycuteapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteFiles {
    private static final Gson gson = new Gson();

    public static void exist() throws IOException {
        File file = new File("data/data/com.example.mycuteapplication/files");

        if (!file.exists()) {
            new File("data/data/com.example.mycuteapplication/files").mkdirs();
            SharedPreferences.Editor editor = MainActivity.settings.edit();
            editor.putString("text", "1");
            editor.putString("theme", "pink");
            editor.apply();
            MainActivity.theme = R.style.Base_Theme_MyCuteApplication;
            MainActivity.textSize = 30;
        }

        file = new File("data/data/com.example.mycuteapplication/files/_list.json");

        if (!file.exists()) {
            file.createNewFile();
            FileWriter fw = new FileWriter("data/data/com.example.mycuteapplication/files/_list.json");
            gson.toJson(new int[0], fw);
            fw.flush();
            fw.close();
        }

        file = new File("data/data/com.example.mycuteapplication/files/_groups.json");
        if (!file.exists()) {
            file.createNewFile();
            FileWriter fw = new FileWriter("data/data/com.example.mycuteapplication/files/_groups.json");
            gson.toJson(new int[0], fw);
            fw.flush();
            fw.close();
        }
    }

    public static List<MainElement> readFile() throws IOException {
        List<MainElement> main = new ArrayList<>();
        List<JsonElement> ja = gson.fromJson(new FileReader("data/data/com.example.mycuteapplication/files/_list.json"), JsonArray.class).asList();

        for (JsonElement j : ja) {
            if (j.getAsJsonObject().get("type").getAsString().equals("WRITE")) {
                main.add(new MainElement(j.getAsJsonObject().get("name").getAsString(), j.getAsJsonObject().get("text").getAsString(), TypesOfFiles.WRITE, j.getAsJsonObject().get("italicFormatText").getAsJsonArray().asList(), j.getAsJsonObject().get("boldFormatText").getAsJsonArray().asList(), j.getAsJsonObject().get("boldItalicFormatText").getAsJsonArray().asList(), j.getAsJsonObject().get("underLineFormatText").getAsJsonArray().asList(), j.getAsJsonObject().get("preference").getAsJsonArray().asList()));
            } else if (j.getAsJsonObject().get("type").getAsString().equals("LIST")) {
                main.add(new MainElement(j.getAsJsonObject().get("name").getAsString(), j.getAsJsonObject().get("text").getAsString(), TypesOfFiles.LIST, j.getAsJsonObject().get("checkBoxes").getAsJsonArray().asList(), j.getAsJsonObject().get("preference").getAsJsonArray().asList()));
            }
        }

        return main;
    }

    public static void writeFile() throws IOException {
        FileWriter fw = new FileWriter("data/data/com.example.mycuteapplication/files/_list.json");
        gson.toJson(MainActivity.mainElementList, fw);
        fw.flush();
        fw.close();
    }

    public static List<Group> readGroups(Context context) {
        List<Group> result = new ArrayList<>();

        List<JsonElement> ja = null;
        try {
            ja = gson.fromJson(new FileReader("data/data/com.example.mycuteapplication/files/_groups.json"), JsonArray.class).asList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (JsonElement j : ja) {
            Group g = new Group(j.getAsJsonObject().get("name").getAsString());
            List<JsonElement> e = j.getAsJsonObject().get("elementsName").getAsJsonArray().asList();
            for (JsonElement je : e) {
                g.elementsName.add(je.getAsString());
            }
            for (MainElement element : MainActivity.mainElementList) {
                if (g.elementsName.contains(element.name)) {
                    g.elements.add(element);
                }
            }
            g.checkBoxes = new NewCheckBox(context);
            g.checkBoxes.setText(g.name);
            result.add(g);
        }

        return result;
    }

    public static void writeGroup() throws IOException {
        FileWriter fw = new FileWriter("data/data/com.example.mycuteapplication/files/_groups.json");
        gson.toJson(MainActivity.groups, fw);
        fw.flush();
        fw.close();
    }

    public static int find(String name) {
        int c = 0;

        for (MainElement m : MainActivity.mainElementList) {
            if (m.name.equals(name)) {
                c++;
                if (c == 2) {
                    return c;
                }
            }
        }

        return c;
    }
}