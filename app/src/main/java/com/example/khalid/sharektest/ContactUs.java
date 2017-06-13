package com.example.khalid.sharektest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity implements View.OnClickListener {

    Button send;
    EditText name, phone, email, message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        send = (Button) findViewById(R.id.cous_send_Button);
        send.setOnClickListener(this);

        name = (EditText) findViewById(R.id.Cous_name_editText);
        name.setOnClickListener(this);
        message = (EditText) findViewById(R.id.cous_mes_editText);
        message.setOnClickListener(this);
        email = (EditText) findViewById(R.id.cous_email_editText);
        email.setOnClickListener(this);
        phone = (EditText) findViewById(R.id.cous_phone_edittext);
        phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String n = name.getText().toString();
        String p = phone.getText().toString();
        String em = email.getText().toString();
        String mas = message.getText().toString();
        String mail = "Name :  " + n + "/n" +
                "Phone :   " + p + "/n" +
                "Email :   " + em + "/n" +
                "Message : " + mas;

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@sharekeg.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
        i.putExtra(Intent.EXTRA_TEXT, mail);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }


        Toast.makeText(this, "message is sent", Toast.LENGTH_LONG).show();


    }


}
