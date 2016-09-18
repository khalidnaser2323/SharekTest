package com.example.khalid.sharektest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddIntrest extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,View.OnClickListener{
    EditText name ,phone,mail,intrestTitle,description,time,country,city;
    Spinner gender,duration,catagory;
    CheckBox checkBox;
    Button post;
    Boolean check;
    String genderitem,catitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intrest);
        name=(EditText)findViewById(R.id.addintrest_name_textView17);
        phone = (EditText) findViewById(R.id.addintrest_phone_textView19);
        mail=(EditText)findViewById(R.id.addintrest_email_textView18);
        intrestTitle=(EditText)findViewById(R.id.addintrest_intresttitle_textView12);
        description=(EditText)findViewById(R.id.addintrest_description_textView16);
        time=(EditText)findViewById(R.id.addintrest_durationtime_textView13);
        country=(EditText)findViewById(R.id.addintrest_Country_textView14);
        city=(EditText)findViewById(R.id.addintrest_city_textView15);
        checkBox=(CheckBox)findViewById(R.id.addintrest_checkBox2);
        post=(Button)findViewById(R.id.addintrest_post_button);
        post.setOnClickListener(this);

        gender= (Spinner) findViewById(R.id.addintrest_gender_spinner2);
        catagory=(Spinner)findViewById(R.id.addintrest_catagory_spinner);
        catagory.setOnItemSelectedListener(this);


        // Spinner click listener
        gender.setOnItemSelectedListener(this);

        // Spinner Drop down elements


        // Creating adapter for spinner
        SpinnerCustomArrayAdapter genderarr =  new SpinnerCustomArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);

    genderarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    genderarr.add("Male");
    genderarr.add("Female");
    genderarr.add("Gender");

    gender.setAdapter(genderarr);
    gender.setSelection(genderarr.getCount()); //display hint
    SpinnerCustomArrayAdapter catagoryarr =  new SpinnerCustomArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);

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
        if(spinner.getId() == R.id.addintrest_gender_spinner2)
        {
             genderitem = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item

        }
        else if(spinner.getId() == R.id.addintrest_catagory_spinner)
        {
             catitem = parent.getItemAtPosition(position).toString();
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {if (v==post){
        String Sname=name.getText().toString();
        String Sphone=phone.getText().toString();
        String Smail=mail.getText().toString();
        String SintrestTitle=intrestTitle.getText().toString();
        String Sdescription=description.getText().toString();
        String Stime=time.getText().toString();
        String Scountry=country.getText().toString();
        String Scity=city.getText().toString();
        if (check=checkBox.isChecked()){
            Toast.makeText(AddIntrest.this, Sname+" .."+'\n'+Sphone+" .."+'\n'+
                    Smail+" .."+'\n'+Scity+" .."+'\n'+Scountry+" .."+'\n'+Stime+" .."+'\n'+Sdescription+" .."+'\n'+SintrestTitle+" .."+'\n'+catitem+" .."+'\n'+genderitem
                    , Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(AddIntrest.this, "Please", Toast.LENGTH_SHORT).show();
        }



    }}



    // make a Custom ArrayAdapter to Spinner to make hint
public class SpinnerCustomArrayAdapter extends ArrayAdapter<String>{
    public SpinnerCustomArrayAdapter(Context context, int resource) {
        super(context, resource);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = super.getView(position, convertView, parent);
        if (position == getCount()) {
            ((TextView)v.findViewById(android.R.id.text1)).setText("");
            ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
        }

        return v;
    }

    @Override
    public int getCount() {
        return super.getCount()-1; // you dont display last item. It is used as hint.
    }

}

}
