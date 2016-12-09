package com.example.khalid.sharektest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import com.example.khalid.sharektest.Utils.GPSTracker;
import com.example.khalid.sharektest.Utils.SpinnerCustomArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    EditText fname,lname,uname,email,phone,day,year,month,pass,repass;
    Spinner gender;
    CheckBox checkBox;
    Button sign,getlocation;
    String genderitem;
    Boolean check;
    double latitude=0 ,longitude = 0;
    JSONObject jsonObject;

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
         
            genderitem = parent.getItemAtPosition(position).toString().toLowerCase();}

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
            String params_Date=
                  ("{"
            +" \"user\":" +"\""+uname.getText().toString()+"\""+","
                    +" \"pass\":" +"\""+pass.getText().toString()+"\""+","
                    +"     \"name\": {"
                    +" \"first\":"+"\""+fname.getText().toString()+"\""+","
                    +"    \"last\":"+"\""+lname.getText().toString()+"\""
                    +"},"
                    +"\"email\":"+"\""+email.getText().toString()+"\""+","
                    +"    \"birth\": {"
                    +"\"day\":"+"\""+day.getText().toString()+"\""+","
                    +"        \"month\":"+"\""+month.getText().toString()+"\""+","
                    +"        \"year\":"+"\""+year.getText().toString()+"\""
                    +"} ,"
                    +"\"location\": {"
                    +"\"latitude\":"+"\""+longitude+"\""+","
                    +"        \"longitude\":"+"\""+longitude+"\""
                    +"},"
                    +"\"phone\":"+"\""+phone.getText().toString()+"\""+","
                    +"    \"gender\":"+"\""+genderitem+"\""

                    +"}");
            try {
                jsonObject = new JSONObject(params_Date);
//                Log.i("hph","fhdhf");
                Log.i("hhh",jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            }
        else {gps.showSettingsAlert();}}
        else if (v==sign){
            if (pass.equals(repass)) {

            if (check=checkBox.isChecked()&&longitude!=latitude){





                final String URL = "http://api.sharekeg.com/user";

            final JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("response", response.toString());
//                            Toast.makeText(SignUp.this, "Welcome to sharekeg, Please verify your mail and login", Toast.LENGTH_SHORT).show();



                            Intent intent = new Intent(SignUp.this, MainActivity.class);
//                            intent.putExtra("loggedin",true);
                            startActivity(intent);
//


                            // handle response
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
                    return headers;
                }
            };
            AppController.getInstance().addToRequestQueue(req);}
            else {
                Toast.makeText(this, "Review your Data and Recheck your Location", Toast.LENGTH_LONG).show();
            }
        }
            else {
                Toast.makeText(SignUp.this, " Invalid Password ", Toast.LENGTH_LONG).show();
            }

}
    }
}
