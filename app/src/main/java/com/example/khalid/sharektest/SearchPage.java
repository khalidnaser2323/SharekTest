package com.example.khalid.sharektest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SearchPage extends AppCompatActivity {

    Spinner s,s2,s3,s4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] arraySpinner = new String[]{
                "1", "2", "3", "4", "5"
        };
      s = (Spinner) findViewById(R.id.SerachPage_category_Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setPrompt("Pick a category");
        s.setAdapter(adapter);

        s2 =(Spinner) findViewById(R.id.SerachPage_city_Spinner);
        s2.setPrompt("City");
    }

}
