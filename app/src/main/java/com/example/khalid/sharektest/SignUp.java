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

import com.example.khalid.sharektest.Utils.GPSTracker;
import com.example.khalid.sharektest.Utils.SpinnerCustomArrayAdapter;

public class SignUp extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    EditText fname,lname,uname,email,phone,day,year,month,pass,repass;
    Spinner gender;
    CheckBox checkBox;
    Button sign,getlocation;
    String genderitem;
    Boolean check;
    double latitude=0 ,longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fname=(EditText)findViewById(R.id.signup_fristname_editText);
        lname=(EditText)findViewById(R.id.signup_lastname_editText);
        uname=(EditText)findViewById(R.id.signup_username_editText);
        email=(EditText)findViewById(R.id.signup_email_editText);
        phone=(EditText)findViewById(R.id.signup_phone_editText);
        day=(EditText)findViewById(R.id.signup_day_editText);
        year=(EditText)findViewById(R.id.signup_year_editText);
        month=(EditText)findViewById(R.id.signup_month_editText);
        pass=(EditText)findViewById(R.id.signup_password_editText);
        repass=(EditText)findViewById(R.id.signup_repassword_editText2);
        gender=(Spinner) findViewById(R.id.signup_gender_spinner);
        checkBox=(CheckBox) findViewById(R.id.signup_checkBox);
        sign=(Button) findViewById(R.id.signup_signup_button);
        getlocation=(Button) findViewById(R.id.signup_getlocation_button);
        gender.setOnItemSelectedListener(this);
        sign.setOnClickListener(this);
        getlocation.setOnClickListener(this);
        SpinnerCustomArrayAdapter genderarr = new SpinnerCustomArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        genderarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderarr.add("Male");
        genderarr.add("Female");
        genderarr.add("Gender");

        gender.setAdapter(genderarr);
        gender.setSelection(genderarr.getCount());
        
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         
            genderitem = parent.getItemAtPosition(position).toString();}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        
    }


    @Override
    public void onClick(View v) {
        if (v==getlocation){
            GPSTracker gps = new GPSTracker(this);
        if (gps.isGPSEnabled){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            }
        else {gps.showSettingsAlert();}}
        else if (v==sign){
            if (check=checkBox.isChecked()&&longitude!=latitude&&pass.equals(repass)){
            String t =fname.getText().toString()+'\n'+phone.getText().toString()+'\n'+String.valueOf(latitude)+'\n'+String.valueOf(longitude);
                Toast.makeText(SignUp.this, t, Toast.LENGTH_LONG).show();}
            else {
                Toast.makeText(this, "Review your Data", Toast.LENGTH_SHORT).show();
            }
        }

}}
