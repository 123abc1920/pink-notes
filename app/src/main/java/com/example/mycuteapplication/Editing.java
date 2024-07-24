package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

public class Editing extends AppCompatActivity {
    private LinearLayout mainLayout;
    private List<Integer> boldFormatText = new ArrayList<>();
    private List<Integer> italicFormatText = new ArrayList<>();
    private List<Integer> boldItalicFormatText = new ArrayList<>();
    private List<Integer> underLineFormatText = new ArrayList<>();
    private EditText name, text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        MaterialToolbar tb2 = findViewById(R.id.materialToolbar_editing);
        tb2.inflateMenu(R.menu.menu_edit);
        setSupportActionBar(tb2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tb2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mainElementList.contains(MainActivity.nowElement)) {
                    Intent intent = new Intent(Editing.this, Reading.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(Editing.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        name = findViewById(R.id.title_editing);
        text = findViewById(R.id.text_editing);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, MainActivity.textSize);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, MainActivity.textSize);

        if (MainActivity.old) {
            name.setText(MainActivity.nowElement.name);
            text.setText(getSpan(MainActivity.nowElement.text), TextView.BufferType.SPANNABLE);
        }

        boldFormatText = MainActivity.nowElement.boldFormatText;
        italicFormatText = MainActivity.nowElement.italicFormatText;
        boldItalicFormatText = MainActivity.nowElement.boldItalicFormatText;
        underLineFormatText = MainActivity.nowElement.underLineFormatText;

        mainLayout = findViewById(R.id.main_edit);
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

        Button bold = findViewById(R.id.bold);
        Button italic = findViewById(R.id.italic);
        Button boldItalic = findViewById(R.id.bolditalic);
        Button underline = findViewById(R.id.underline);
        Button normal = findViewById(R.id.normal);
        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startSelection = text.getSelectionStart();
                int endSelection = text.getSelectionEnd();
                for (int i = startSelection + 1; i <= endSelection; i++) {
                    boldFormatText.add(i);
                }
                text.setText(getSpan(text.getText().toString()), TextView.BufferType.SPANNABLE);
            }
        });
        italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startSelection = text.getSelectionStart();
                int endSelection = text.getSelectionEnd();
                for (int i = startSelection + 1; i <= endSelection; i++) {
                    italicFormatText.add(i);
                }
                text.setText(getSpan(text.getText().toString()), TextView.BufferType.SPANNABLE);
            }
        });
        boldItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startSelection = text.getSelectionStart();
                int endSelection = text.getSelectionEnd();
                for (int i = startSelection + 1; i <= endSelection; i++) {
                    boldItalicFormatText.add(i);
                }
                text.setText(getSpan(text.getText().toString()), TextView.BufferType.SPANNABLE);
            }
        });
        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startSelection = text.getSelectionStart();
                int endSelection = text.getSelectionEnd();
                for (int i = startSelection + 1; i <= endSelection; i++) {
                    underLineFormatText.add(i);
                }
                text.setText(getSpan(text.getText().toString()), TextView.BufferType.SPANNABLE);
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startSelection = text.getSelectionStart();
                int endSelection = text.getSelectionEnd();
                for (int i = startSelection + 1; i <= endSelection; i++) {
                    if (boldFormatText.contains(i)) {
                        boldFormatText.remove((Object) i);
                    }
                    if (italicFormatText.contains(i)) {
                        italicFormatText.remove((Object) i);
                    }
                    if (boldItalicFormatText.contains(i)) {
                        boldItalicFormatText.remove((Object) i);
                    }
                    if (underLineFormatText.contains(i)) {
                        underLineFormatText.remove((Object) i);
                    }
                }
                text.setText(getSpan(text.getText().toString()), TextView.BufferType.SPANNABLE);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        if (id == R.id.save_from_edit) {
            if (name.getText().toString().equals("")) {
                Toast toast = Toast.makeText(this, "Имя не введено!", Toast.LENGTH_LONG);
                toast.show();
            } else {
                MainActivity.nowElement.name = name.getText().toString();
                MainActivity.nowElement.text = text.getText().toString();

                MainActivity.nowElement.boldFormatText = boldFormatText;
                MainActivity.nowElement.italicFormatText = italicFormatText;
                MainActivity.nowElement.boldItalicFormatText = boldItalicFormatText;
                MainActivity.nowElement.underLineFormatText = underLineFormatText;

                if (MainActivity.mainElementList.contains(MainActivity.nowElement)) {
                    if (ReadWriteFiles.find(MainActivity.nowElement.name) == 2) {
                        Toast toast = Toast.makeText(this, "Уже есть заметка с таким именем!", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        try {
                            ReadWriteFiles.writeFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        MainActivity.old = true;
                        Intent intent = new Intent(this, Reading.class);
                        startActivity(intent);
                    }
                } else {
                    if (ReadWriteFiles.find(MainActivity.nowElement.name) == 1) {
                        Toast toast = Toast.makeText(this, "Уже есть заметка с таким\nименем или имя не введено!", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        MainActivity.mainElementList.add(MainActivity.nowElement);

                        try {
                            ReadWriteFiles.writeFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        MainActivity.old = true;
                        Intent intent = new Intent(this, Reading.class);
                        startActivity(intent);
                    }
                }
            }
        }

        if (id == R.id.settings_from_edit) {
            Intent intent = new Intent(this, ChangePreference.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(mi);
    }

    public SpannableString getSpan(String text) {
        SpannableString contentSpan = new SpannableString(text);

        for (int j = 0; j < 4; j++) {
            List<Integer> list = null;

            if (j == 0) {
                list = boldFormatText;
            } else if (j == 1) {
                list = italicFormatText;
            } else if (j == 2) {
                list = boldItalicFormatText;
            } else {
                list = underLineFormatText;
            }
            if (list.size() != 0) {
                for (int i = 1; i <= text.length(); i++) {
                    if (list.contains(i)) {
                        if (j == 0) {
                            contentSpan.setSpan(new StyleSpan(Typeface.BOLD), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (j == 1) {
                            contentSpan.setSpan(new StyleSpan(Typeface.ITALIC), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (j == 2) {
                            contentSpan.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else {
                            contentSpan.setSpan(new UnderlineSpan(), i - 1, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
            }
        }

        return contentSpan;
    }
}