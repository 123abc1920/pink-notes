package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;

public class DeletingFiles extends AppCompatActivity {
    protected static MainElement nowButton = null;
    protected static MainElement change = null;
    protected static CheckBox select;
    protected static ButtonAdapter adapter;
    protected static RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleting_files);

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar_delete);
        materialToolbar.inflateMenu(R.menu.menu_delete);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeletingFiles.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        select = findViewById(R.id.select_all);
        select.setTextColor(MainActivity.maincolor);
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[]{
                        MainActivity.maincolor,
                        MainActivity.maincolor
                });

        select.setButtonTintList(colorStateList);
        select.setBackgroundColor(Color.WHITE);
        select.setTextSize(MainActivity.textSize);

        recyclerView = findViewById(R.id.recycleView_deletingfiles);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        adapter = new ButtonAdapter(this, MainActivity.mainElementList, true);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        if (id == R.id.accept_delete) {
            MainActivity.mainElementList.removeIf(nextElement -> nextElement.toDelete);

            try {
                ReadWriteFiles.writeFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Intent intent = new Intent(DeletingFiles.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(mi);
    }

    public void onCheckboxClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            for (MainElement e : MainActivity.mainElementList) {
                e.toDelete = true;
            }
        } else {
            for (MainElement e : MainActivity.mainElementList) {
                e.toDelete = false;
            }
        }

        adapter.notifyDataSetChanged();
    }
}