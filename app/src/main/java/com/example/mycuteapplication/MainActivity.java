package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static SharedPreferences settings;
    public static MainElement nowElement = null;
    public static List<Group> groups = new ArrayList<>();
    public static List<MainElement> mainElementList = new ArrayList<>();
    public static boolean old = false;
    public static int textSize = 30, displayWidth = 0, displayHeight = 0, maincolor = Color.rgb(211, 110, 112), theme = R.style.Base_Theme_MyCuteApplication;
    private RecyclerView recyclerView;
    private ButtonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = getSharedPreferences("settings", Context.MODE_PRIVATE);

        if (settings.getString("text", "").equals("0")) {
            textSize = 30;
        } else if (settings.getString("text", "").equals("1")) {
            textSize = 40;
        } else if (settings.getString("text", "").equals("2")) {
            textSize = 50;
        }

        if (settings.getString("theme", "").equals("pink")) {
            theme = R.style.Base_Theme_MyCuteApplication;
            maincolor = Color.rgb(211, 110, 112);
        } else if (settings.getString("theme", "").equals("blue")) {
            theme = R.style.Base_Theme_MyCuteApplication_Blue;
            maincolor = Color.rgb(93, 155, 155);
        }

        setTheme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        displayHeight = metricsB.heightPixels;
        displayWidth = metricsB.widthPixels;

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar_main);
        materialToolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(materialToolbar);

        if (mainElementList.size() == 0) {
            try {
                ReadWriteFiles.exist();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                mainElementList = ReadWriteFiles.readFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            for (MainElement e : mainElementList) {
                if (e.toDelete) {
                    e.toDelete = false;
                }
            }
        }

        if (groups.size() == 0) {
            groups = ReadWriteFiles.readGroups(this);
        }

        recyclerView = findViewById(R.id.recycleView_main);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        adapter = new ButtonAdapter(this, mainElementList, false);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        if (id == R.id.create_new) {
            nowElement = new MainElement(TypesOfFiles.WRITE);
            Intent intent = new Intent(this, Editing.class);
            startActivity(intent);
        }
        if (id == R.id.settings) {
            Intent intent = new Intent(this, Setting.class);
            startActivity(intent);
        }
        if (id == R.id.create_spisok) {
            nowElement = new MainElement(TypesOfFiles.LIST);
            Intent intent = new Intent(this, ListEdit.class);
            startActivity(intent);
        }
        if (id == R.id.search) {
            Intent intent = new Intent(this, Search.class);
            startActivity(intent);
        }
        if (id == R.id.groups) {
            Intent intent = new Intent(this, Groups.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(mi);
    }
}