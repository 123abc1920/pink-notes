package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;

public class ListEdit extends AppCompatActivity {
    private CheckBoxAdapter adapter;
    private EditText title, listText;
    private CustomListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        MaterialToolbar tb2 = findViewById(R.id.materialToolbar_list);
        tb2.inflateMenu(R.menu.menu_list);
        setSupportActionBar(tb2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tb2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListEdit.this, MainActivity.class);
                startActivity(intent);
            }
        });

        title = findViewById(R.id.title_list);
        listText = findViewById(R.id.list_name);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, MainActivity.textSize);

        if (MainActivity.old) {
            title.setText(MainActivity.nowElement.name);
        }

        listView = findViewById(R.id.listview_list);
        adapter = new CheckBoxAdapter(this, android.R.layout.simple_list_item_1, MainActivity.nowElement.checkBoxes, false);
        listView.setAdapter(adapter);

        LinearLayout mainLayout = findViewById(R.id.list_layout);
        if (MainActivity.nowElement.preference.get(0) == 0) {
            mainLayout.setBackgroundResource(R.color.white);
        }
        if (MainActivity.nowElement.preference.get(0) == 1) {
            mainLayout.setBackgroundResource(R.drawable.back_cells);
        }
        if (MainActivity.nowElement.preference.get(0) == 2) {
            mainLayout.setBackgroundResource(R.drawable.back_kos);
        }
        if (MainActivity.nowElement.preference.get(0) == 3) {
            mainLayout.setBackgroundResource(R.drawable.back_line);
        }

        MaterialButton createBtn = findViewById(R.id.create_list);
        createBtn.setEnabled(false);
        createBtn.setBackgroundColor(Color.TRANSPARENT);
        createBtn.setTextColor(MainActivity.maincolor);

        listText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (listText.getText().toString().equals("")) {
                    createBtn.setEnabled(false);
                    createBtn.setBackgroundColor(Color.TRANSPARENT);
                    createBtn.setTextColor(MainActivity.maincolor);
                } else {
                    createBtn.setEnabled(true);
                    createBtn.setBackgroundColor(MainActivity.maincolor);
                    createBtn.setTextColor(Color.WHITE);
                }
            }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.add(listText.getText().toString());
                listText.setText("");
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        if (id == R.id.save_list) {
            MainActivity.nowElement.name = title.getText().toString();

            for (int i = 0; i < MainActivity.nowElement.checkBoxes.size(); i++) {
                NewCheckBox n = (NewCheckBox) listView.getChildAt(i);
                if (n.isChecked()) {
                    if (!MainActivity.nowElement.checkBoxes.get(i).contains("@")) {
                        MainActivity.nowElement.checkBoxes.set(i, "@" + MainActivity.nowElement.checkBoxes.get(i));
                    }
                } else {
                    if (MainActivity.nowElement.checkBoxes.get(i).contains("@")) {
                        MainActivity.nowElement.checkBoxes.set(i, MainActivity.nowElement.checkBoxes.get(i).replace("@", ""));
                    }
                }
            }

            if (MainActivity.mainElementList.contains(MainActivity.nowElement)) {
                if (ReadWriteFiles.find(MainActivity.nowElement.name) == 2) {
                    Toast toast = Toast.makeText(this, "Уже есть список с таким\nименем или имя не введено!", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    try {
                        ReadWriteFiles.writeFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            } else {
                if (ReadWriteFiles.find(MainActivity.nowElement.name) == 1) {
                    Toast toast = Toast.makeText(this, "Уже есть список с таким\nименем или имя не введено!", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    MainActivity.mainElementList.add(MainActivity.nowElement);

                    try {
                        ReadWriteFiles.writeFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
        if (id == R.id.delete_list) {
            DeleteDialogList dialog = new DeleteDialogList();
            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "dialog");
        }
        if (id == R.id.preference_list) {
            Intent intent = new Intent(this, ChangePreference.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(mi);
    }

    public void okClicked() {
        MainActivity.mainElementList.remove(MainActivity.nowElement);

        try {
            ReadWriteFiles.writeFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onCheckboxClicked(View view) {
    }
}
