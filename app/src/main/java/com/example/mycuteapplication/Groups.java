package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Groups extends AppCompatActivity {
    protected static List<MainElement> elementsList = new ArrayList<>();
    protected static List<Group> groupsList = new ArrayList<>();
    private GroupAdapter listviewAdapter;
    protected static ButtonAdapter recycleAdapter;
    protected static RecyclerView recyclerView;
    protected static CustomListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        groupsList.clear();
        groupsList.addAll(MainActivity.groups);

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar_groups);
        materialToolbar.inflateMenu(R.menu.menu_groups);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Groups.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recycleView_groups);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        recycleAdapter = new ButtonAdapter(this, elementsList, false);
        recyclerView.setAdapter(recycleAdapter);

        listView = findViewById(R.id.listview_groups);
        listviewAdapter = new GroupAdapter(this, android.R.layout.simple_list_item_1, groupsList, true);
        listView.setAdapter(listviewAdapter);

        EditText groupName = findViewById(R.id.group_name);

        MaterialButton createBtn = findViewById(R.id.create_group);
        createBtn.setEnabled(false);
        createBtn.setBackgroundColor(Color.WHITE);
        createBtn.setTextColor(MainActivity.maincolor);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Group g = new Group(groupName.getText().toString());

                MainActivity.groups.add(g);
                groupsList.add(g);
                listviewAdapter.notifyDataSetChanged();

                try {
                    ReadWriteFiles.writeGroup();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                createBtn.setEnabled(false);
                createBtn.setBackgroundColor(Color.WHITE);
                createBtn.setTextColor(MainActivity.maincolor);
            }
        });

        groupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                groupsList.clear();
                elementsList.clear();

                boolean add = true;

                String text = groupName.getText().toString();
                createBtn.setEnabled(false);
                createBtn.setBackgroundColor(Color.WHITE);
                createBtn.setTextColor(MainActivity.maincolor);

                if (text.equals("")) {
                    groupsList.addAll(MainActivity.groups);
                    add = false;
                } else {
                    for (Group g : MainActivity.groups) {
                        if (g.name.contains(text)) {
                            groupsList.add(g);
                        }
                        if (g.name.equals(text)) {
                            add = false;
                        }
                    }

                    for (Group g : groupsList) {
                        for (int i = 0; i < listView.getChildCount(); i++) {
                            NewCheckBox n = (NewCheckBox) listView.getChildAt(i);
                            if (n.isChecked()) {
                                for (MainElement e : g.elements) {
                                    if (!elementsList.contains(e)) {
                                        elementsList.add(e);
                                    }
                                }
                            }
                        }
                    }
                }

                listviewAdapter.notifyDataSetChanged();
                recycleAdapter.notifyDataSetChanged();

                recyclerView.setAdapter(recycleAdapter);
                listView.invalidate();

                if (add) {
                    createBtn.setEnabled(true);
                    createBtn.setBackgroundColor(MainActivity.maincolor);
                    createBtn.setTextColor(Color.WHITE);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();

        if (id == R.id.delete_group) {
            elementsList.clear();

            for (int i = 0; i < groupsList.size(); i++) {
                NewCheckBox n = (NewCheckBox) listView.getChildAt(i);
                if (n.toDelete) {
                    MainActivity.groups.remove(groupsList.get(i));
                }
            }

            try {
                ReadWriteFiles.writeGroup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            groupsList.clear();
            groupsList.addAll(MainActivity.groups);

            listviewAdapter.notifyDataSetChanged();
            recycleAdapter.notifyDataSetChanged();

            listView.setAdapter(listviewAdapter);
            recyclerView.setAdapter(recycleAdapter);
        }

        return super.onOptionsItemSelected(mi);
    }
}