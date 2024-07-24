package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    private List<MainElement> currents = new ArrayList<>();
    private RecyclerView recyclerView;
    private ButtonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar_search);
        materialToolbar.inflateMenu(R.menu.menu_search);
        setSupportActionBar(materialToolbar);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycleView_search);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        adapter = new ButtonAdapter(this, currents, false);
        recyclerView.setAdapter(adapter);

        EditText textView = findViewById(R.id.text_search);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currents.clear();

                String value = textView.getText().toString();
                for (MainElement e : MainActivity.mainElementList) {
                    if (e.name.contains(value) && !value.equals("")) {
                        currents.add(e);
                    }
                }

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }


}