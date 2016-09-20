package com.example.khalid.sharektest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.khalid.sharektest.Utils.SpinnerCustomArrayAdapter;

public class AddShare extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText name, phone, mail, ShareTitle, description, price, country, city;
    Spinner gender, duration, catagory;
    CheckBox checkBox;
    Button post;
    Boolean check;
    String genderitem, catitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_share);
        name = (EditText) findViewById(R.id.addshare_name_textView17);
        phone = (EditText) findViewById(R.id.addshare_phone_textView19);
        mail = (EditText) findViewById(R.id.addshare_email_textView18);
        ShareTitle = (EditText) findViewById(R.id.addshare_sharetitle_textView12);
        description = (EditText) findViewById(R.id.addshare_description_textView16);
        price = (EditText) findViewById(R.id.addshare_price_textView13);
        country = (EditText) findViewById(R.id.addshare_Country_textView14);
        city = (EditText) findViewById(R.id.addshare_city_textView15);
        checkBox = (CheckBox) findViewById(R.id.addshare_checkBox2);
        post = (Button) findViewById(R.id.addshare_post_button);
        post.setOnClickListener(this);

        gender = (Spinner) findViewById(R.id.addshare_gender_spinner2);
        catagory = (Spinner) findViewById(R.id.addshare_catagory_spinner);
        catagory.setOnItemSelectedListener(this);


        // Spinner click listener
        gender.setOnItemSelectedListener(this);

        // Spinner Drop down elements


        // Creating adapter for spinner
        SpinnerCustomArrayAdapter genderarr = new SpinnerCustomArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        genderarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderarr.add("Male");
        genderarr.add("Female");
        genderarr.add("Gender");

        gender.setAdapter(genderarr);
        gender.setSelection(genderarr.getCount()); //display hint
        SpinnerCustomArrayAdapter catagoryarr = new SpinnerCustomArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        catagoryarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catagoryarr.add("sss");
        catagoryarr.add("fffffff");
        catagoryarr.add("Catagory");

        catagory.setAdapter(catagoryarr);
        catagory.setSelection(catagoryarr.getCount());


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.addshare_gender_spinner2) {
            genderitem = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item

        } else if (spinner.getId() == R.id.addshare_catagory_spinner) {
            catitem = parent.getItemAtPosition(position).toString();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == post) {
            String Sname = name.getText().toString();
            String Sphone = phone.getText().toString();
            String Smail = mail.getText().toString();
            String SshareTitle = ShareTitle.getText().toString();
            String Sdescription = description.getText().toString();
            String Sprice = price.getText().toString();
            String Scountry = country.getText().toString();
            String Scity = city.getText().toString();
            if (genderitem.equals("Gender")){
            Toast.makeText(AddShare.this, "Please select gender", Toast.LENGTH_SHORT).show();}
            if (catitem.equals("Catagory")){Toast.makeText(AddShare.this, "Please select catagory", Toast.LENGTH_SHORT).show();}


            if (check = checkBox.isChecked()) {
                Toast.makeText(this, Sname + " .." + '\n' + Sphone + " .." + '\n' +
                                Smail + " .." + '\n' + Scity + " .." + '\n' + Scountry + " .." + '\n' + Sprice + " .." + '\n' + Sdescription + " .." + '\n' + SshareTitle + " .." + '\n' + catitem + " .." + '\n' + genderitem
                        , Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
