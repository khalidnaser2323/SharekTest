package com.example.khalid.sharektest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Abdelrahman on 6/12/2017.
 */

public class Tags extends AppCompatActivity implements View.OnClickListener {
    EditText textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView = (EditText) findViewById(R.id.Tags_TextView_first);
        button = (Button) findViewById(R.id.tag_Button_search);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CommonTags.class);
        intent.putExtra("value", textView.getText().toString());
        startActivity(intent);
    }
}

