package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.ArrayList;

public class ChangePreference extends AppCompatActivity {
    private java.util.List<Integer> preference = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_preference);

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar_preferenсe);
        materialToolbar.inflateMenu(R.menu.menu_preference);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.nowElement.type.getType().equals("w")) {
                    Intent intent = new Intent(ChangePreference.this, Editing.class);
                    startActivity(intent);
                } else if (MainActivity.nowElement.type.getType().equals("l")) {
                    Intent intent = new Intent(ChangePreference.this, ListEdit.class);
                    startActivity(intent);
                }

                finish();
            }
        });

        preference = MainActivity.nowElement.preference;

        ShapeableImageView emptyButton = findViewById(R.id.emptyButton);
        emptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preference.set(0, 0);
            }
        });
        ShapeableImageView cellButton = findViewById(R.id.cellButton);
        cellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preference.set(0, 1);
            }
        });
        ShapeableImageView kosButton = findViewById(R.id.kosButton);
        kosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.nowElement.preference.set(0, 2);
            }
        });
        ShapeableImageView lineButton = findViewById(R.id.lineButton);
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preference.set(0, 3);
            }
        });

        CustomListView listView = findViewById(R.id.listview_groups);
        GroupAdapter adapter = new GroupAdapter(this, android.R.layout.simple_list_item_1, MainActivity.groups, false);
        listView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preference, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        if (id == R.id.save_from_prefs) {
            MainActivity.nowElement.preference = preference;

            try {
                ReadWriteFiles.writeGroup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (MainActivity.nowElement.type.getType().equals("w")) {
                Intent intent = new Intent(this, Editing.class);
                startActivity(intent);
            } else if (MainActivity.nowElement.type.getType().equals("l")) {
                Intent intent = new Intent(this, ListEdit.class);
                startActivity(intent);
            }

            finish();
        }

        return super.onOptionsItemSelected(mi);
    }
}