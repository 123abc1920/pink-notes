package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.appbar.MaterialToolbar;

public class Setting extends AppCompatActivity {
    private String size, theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        size = MainActivity.settings.getString("text", "");
        theme = MainActivity.settings.getString("theme", "");

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar_setting);
        materialToolbar.inflateMenu(R.menu.menu_set);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MainActivity.settings.edit();
                editor.putString("text", size);
                editor.putString("theme", theme);
                editor.apply();

                Intent intent = new Intent(Setting.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ToggleButton size1 = findViewById(R.id.size1);
        size1.setTextColor(MainActivity.maincolor);
        ToggleButton size2 = findViewById(R.id.size2);
        size2.setTextColor(MainActivity.maincolor);
        ToggleButton size3 = findViewById(R.id.size3);
        size3.setTextColor(MainActivity.maincolor);
        TextView example = findViewById(R.id.example);
        example.setTextColor(MainActivity.maincolor);

        ToggleButton theme1 = findViewById(R.id.theme1);
        ToggleButton theme2 = findViewById(R.id.theme2);

        if (size.equals("0")) {
            size1.setChecked(true);
            example.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        } else if (size.equals("1")) {
            size2.setChecked(true);
            example.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        } else if (size.equals("2")) {
            size3.setChecked(true);
            example.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        }

        if (theme.equals("pink")) {
            theme1.setChecked(true);
            theme1.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(181, 184, 177)));
        } else if (theme.equals("blue")) {
            theme2.setChecked(true);
            theme2.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(181, 184, 177)));
        }

        size1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                size2.setChecked(false);
                size3.setChecked(false);
                example.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);

                size = "0";
            }
        });

        size2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                size1.setChecked(false);
                size3.setChecked(false);
                example.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);

                size = "1";
            }
        });

        size3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                size2.setChecked(false);
                size1.setChecked(false);
                example.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

                size = "2";
            }
        });

        theme1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                theme2.setChecked(false);
                theme2.setBackgroundTintList(null);
                theme1.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(181, 184, 177)));
                Toast toast = Toast.makeText(this, "Тема изменится после выхода из настроек", Toast.LENGTH_LONG);
                toast.show();

                theme = "pink";
            }
        });

        theme2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                theme1.setChecked(false);
                theme1.setBackgroundTintList(null);
                theme2.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(181, 184, 177)));
                Toast toast = Toast.makeText(this, "Тема изменится после выхода из настроек", Toast.LENGTH_LONG);
                toast.show();

                theme = "blue";
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_set, menu);
        return true;
    }
}