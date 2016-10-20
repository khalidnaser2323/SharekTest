package com.example.khalid.sharektest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.SpinnerCustomArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddIntrest extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,View.OnClickListener {
    EditText name, phone, mail, intrestTitle, description, time, tags, maxprice,minprice;
    Spinner gender, duration, catagory;
    CheckBox checkBox;
    Button post;
    Boolean check;
    String genderitem, catitem;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intrest);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (EditText) findViewById(R.id.addintrest_name_textView17);
        phone = (EditText) findViewById(R.id.addintrest_phone_textView19);
        mail = (EditText) findViewById(R.id.addintrest_email_textView18);
        intrestTitle = (EditText) findViewById(R.id.addintrest_title_textView12);
        description = (EditText) findViewById(R.id.addshare_description_textView16);
        time = (EditText) findViewById(R.id.addintrest_durationtime_textView13);
        minprice = (EditText) findViewById(R.id.addintrest_minprice_textView15);
        maxprice = (EditText) findViewById(R.id.addintrest_maxprice_textView15);
        checkBox = (CheckBox) findViewById(R.id.addintrest_checkBox2);
        post = (Button) findViewById(R.id.addshare_post_button);
        post.setOnClickListener(this);

        gender = (Spinner) findViewById(R.id.addintrest_gender_spinner2);
        tags = (EditText) findViewById(R.id.addintrest_tags_edittext);
//        catagory.setOnItemSelectedListener(this);


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
//        catagoryarr.add("sss");
//        catagoryarr.add("fffffff");
//        catagoryarr.add("Catagory");
//
//        catagory.setAdapter(catagoryarr);
//        catagory.setSelection(catagoryarr.getCount());


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.addintrest_gender_spinner2) {
            genderitem = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item

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
            String SintrestTitle = intrestTitle.getText().toString();
            String Sdescription = description.getText().toString();
            String Stime = time.getText().toString();
            String maprice = minprice.getText().toString();
            String miprice = maxprice.getText().toString();
            String Stag=tags.getText().toString();

            ArrayList<String>parts = new ArrayList<String>(Arrays.asList(Stag.split("#")));
            int x = parts.size();
            int i ;
            String tagparts="";
            for (i=0;i<x;i++){
                if (i==x){tagparts =tagparts+"\""+ parts.get(i) +"\"";}
                else {tagparts =tagparts+"\""+ parts.get(i) +"\",";}

                 }

            Log.d("Sdasds",parts.toString());


            if (check = checkBox.isChecked()) {
                String params_Date=
                    ( "{"
                            +"\"type\": \"request\","
                   + "\"title\":" +"\""+SintrestTitle+"\","
                   + "\"description\":"+ "\""+Sdescription+"\","
                   + "\"tags\": ["+tagparts+"],"
                +"\"pieces\":0,"
                      +  "\"price\": {\"min\":"+ miprice+",\"max\":"+maprice+"},"
                +"\"duration\": {\"max\":"+Stime+"}"



                +"}" );
                try {
                    jsonObject = new JSONObject(params_Date);
                    Log.i("hph","fhdhf");
                    Log.i("hhh",jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("params_Date",params_Date);
                final String URL = "http://api.sharekeg.com/poster";

                final JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("response", response.toString());
                                Toast.makeText(AddIntrest.this, "Done", Toast.LENGTH_LONG).show();




                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                        Log.i("error",error.toString());
                    }
                }) {

                    public String getBodyContentType()
                    {
                        return "application/json";
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization",MainActivity.token);
                        return headers;
                    }
                };
                AppController.getInstance().addToRequestQueue(req);}


            } else {
                Toast.makeText(AddIntrest.this, "Please", Toast.LENGTH_SHORT).show();
            }


        }
    }


