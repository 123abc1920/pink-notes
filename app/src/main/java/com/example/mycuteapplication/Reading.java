package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.IOException;
import java.util.List;

public class Reading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        MaterialToolbar materialToolbar = findViewById(R.id.materialToolbar_reading);
        materialToolbar.inflateMenu(R.menu.menu_read);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reading.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView title = findViewById(R.id.title_reading);
        TextView text = findViewById(R.id.text_reading);

        LinearLayout mainLayout = findViewById(R.id.main_read);
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

        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, MainActivity.textSize);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, MainActivity.textSize);

        if (MainActivity.old) {
            title.setText(MainActivity.nowElement.name);
            text.setText(getSpan(MainActivity.nowElement.text), TextView.BufferType.SPANNABLE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        if (id == R.id.edit_from_read) {
            MainActivity.old = true;

            Intent intent = new Intent(this, Editing.class);
            startActivity(intent);
        }
        if (id == R.id.delete_from_read) {
            DeleteDialogRead dialog = new DeleteDialogRead();

            MainActivity.mainElementList.remove(MainActivity.nowElement);
            try {
                ReadWriteFiles.writeFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            FragmentManager manager = getSupportFragmentManager();
            dialog.show(manager, "dialog");
        }
        if (id == R.id.settings_from_read) {
            Intent intent = new Intent(this, ChangePreference.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(mi);
    }

    public void okClicked() {
        MainActivity.mainElementList.remove(MainActivity.nowElement);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public SpannableString getSpan(String text) {
        SpannableString contentSpan = new SpannableString(text);

        for (int j = 0; j < 4; j++) {
            List<Integer> list;
            switch (j) {
                case 0:
                    list = MainActivity.nowElement.boldFormatText;
                    break;
                case 1:
                    list = MainActivity.nowElement.italicFormatText;
                    break;
                case 2:
                    list = MainActivity.nowElement.boldItalicFormatText;
                    break;
                default:
                    list = MainActivity.nowElement.underLineFormatText;
                    break;
            }

            if (list.size() != 0) {
                for (int i = 1; i <= text.length(); i++) {
                    if (list.contains(i)) {
                        switch (j) {
                            case 0:
                                contentSpan.setSpan(new StyleSpan(Typeface.BOLD), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                break;
                            case 1:
                                contentSpan.setSpan(new StyleSpan(Typeface.ITALIC), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                break;
                            case 2:
                                contentSpan.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                break;
                            default:
                                contentSpan.setSpan(new UnderlineSpan(), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                break;
                        }
                    }
                }
            }
        }

        return contentSpan;
    }
}