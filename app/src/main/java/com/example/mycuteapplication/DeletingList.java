package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class DeletingList extends AppCompatActivity {
    private CheckBoxAdapter adapter;
    private CustomListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleting_list);

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar_deletinglist);
        materialToolbar.inflateMenu(R.menu.menu_delete_list);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeletingList.this, ListEdit.class);
                startActivity(intent);
            }
        });

        TextView tv = findViewById(R.id.title_deletinglist);
        tv.setText(MainActivity.nowElement.name);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, MainActivity.textSize);

        listView = findViewById(R.id.listview_deletingList);
        adapter = new CheckBoxAdapter(this, android.R.layout.simple_list_item_1, MainActivity.nowElement.checkBoxes, true);
        listView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        if (id == R.id.delete_list_element) {
            for (int i = 0; i < MainActivity.nowElement.checkBoxes.size(); i++) {
                NewCheckBox n = (NewCheckBox) listView.getChildAt(i);
                if (n.toDelete) {
                    if (n.isChecked()) {
                        MainActivity.nowElement.checkBoxes.remove("@" + n.getText());
                    } else {
                        MainActivity.nowElement.checkBoxes.remove(n.getText().toString());
                    }
                }
            }

            Intent intent = new Intent(DeletingList.this, ListEdit.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(mi);
    }
}