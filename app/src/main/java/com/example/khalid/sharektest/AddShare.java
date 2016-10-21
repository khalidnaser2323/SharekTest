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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddShare extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText name, phone, mail, ShareTitle, description, maxprice, minprice, tags,duration;
    Spinner gender,  catagory;
    CheckBox checkBox;
    Button post;
    Boolean check;
    String genderitem, catitem;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_share);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (EditText) findViewById(R.id.addshare_name_textView17);
        phone = (EditText) findViewById(R.id.addshare_phone_textView19);
        mail = (EditText) findViewById(R.id.addshare_email_textView18);
        ShareTitle = (EditText) findViewById(R.id.addintrest_title_textView12);
        description = (EditText) findViewById(R.id.addshare_description_textView16);
        maxprice = (EditText) findViewById(R.id.addshare_maxprice_textView15);
        minprice = (EditText) findViewById(R.id.addshare_minprice_textView14);
        duration = (EditText) findViewById(R.id.addshare__duration_edittext);
        checkBox = (CheckBox) findViewById(R.id.addshare_checkBox2);
        tags=(EditText)findViewById(R.id.addshare_tags_textView13) ;
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
            String Smasxprice = maxprice.getText().toString();
            String Sminprice = maxprice.getText().toString();
            String Sduration = duration.getText().toString();
            String Stags=tags.getText().toString();
            if (genderitem.equals("Gender")){
            Toast.makeText(AddShare.this, "Please select gender", Toast.LENGTH_SHORT).show();}
            if (catitem.equals("Catagory")){Toast.makeText(AddShare.this, "Please select catagory", Toast.LENGTH_SHORT).show();}


            if (check = checkBox.isChecked()) {
                ArrayList<String> parts = new ArrayList<String>(Arrays.asList(Stags.split("#")));
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
                                    +"\"type\": \"offer\","
                                    + "\"title\":" +"\""+SshareTitle+"\","
                                    + "\"description\":"+ "\""+Sdescription+"\","
                                    + "\"tags\": ["+tagparts+"],"
                                    +"\"pieces\":0,"
                                    +  "\"price\": {\"min\":"+ Sminprice+",\"max\":"+Smasxprice+"},"
                                    +"\"duration\": {\"max\":"+Sduration+"}"



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
                                    Toast.makeText(AddShare.this, "Done", Toast.LENGTH_LONG).show();




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
                Toast.makeText(this, "Please", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
