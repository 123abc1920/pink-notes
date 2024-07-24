package com.example.mycuteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.data.Entry;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class Graph extends AppCompatActivity {
    private static List<Entry> listOfGraph = new ArrayList<>();
    //private LineChart chart = null;
    private EditText text1 = null;
    private EditText text2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(MainActivity.theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        MaterialToolbar tb = findViewById(R.id.materialToolbar_graph);
        tb.inflateMenu(R.menu.menu_graph);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Graph.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //chart = findViewById(R.id.main_graph);
        //chart.setGridBackgroundColor(rgb(211, 110, 112));

        text1 = findViewById(R.id.textGraph1);
        text2 = findViewById(R.id.textGraph2);
        Button button = findViewById(R.id.addPoint);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*listOfGraph.add(new Entry(Float.parseFloat(text1.getText().toString()), Float.parseFloat(text2.getText().toString())));
                LineDataSet dataset = new LineDataSet(listOfGraph, "");
                LineData data = new LineData(dataset);
                chart.setData(data);
                chart.invalidate();
                LinearLayout ll=findViewById(R.id.buttonLayout);
                TextView tv=new TextView(graph.this);
                tv.setText("X="+text1.getText()+"  Y="+text2.getText());
                tv.setTextColor(rgb(211, 110, 112));
                tv.setGravity(Gravity.CENTER);
                tv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });
                ll.addView(tv);*/
                text1.setText("");
                text2.setText("");
            }
        });

        if (MainActivity.old) {
            /*LineDataSet dataset = new LineDataSet(listOfGraph, "");
            LineData data = new LineData(dataset);
            chart.setData(data);
            chart.invalidate();*/
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_graph, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        /*if (id == R.id.save_from_graph) {
            try {
                readWriteFiles.writeEntry(MainActivity.nowName, listOfGraph);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }*/

        return super.onOptionsItemSelected(mi);
    }
}