package com.example.khalid.sharektest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddIntrest extends AppCompatActivity implements View.OnClickListener {

    EditText interestTitle, pieces, startDate, description, duration, tags, price, endDate, guarantee;
    //    Spinner gender,catagory;
    CheckBox agreement;
    Switch negotiable;
    Button post, addImage;
    Boolean check;
    //    String genderitem, catitem;
    JSONObject jsonObject;
    String token;
    Bitmap photo = null;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intrest);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        interestTitle = (EditText) findViewById(R.id.addInterest_editText_Title);
        pieces = (EditText) findViewById(R.id.addInterest_editText_Pieces);
        startDate = (EditText) findViewById(R.id.addInterest_editText_fromDate);
        description = (EditText) findViewById(R.id.addInterest_editText_Description);
        duration = (EditText) findViewById(R.id.addInterest_editText_Duration);
        endDate = (EditText) findViewById(R.id.addInterest_editText_toDate);
        price = (EditText) findViewById(R.id.addInterest_editText_price);
        guarantee = (EditText) findViewById(R.id.addInterest_editText_guarantee);
        post = (Button) findViewById(R.id.addInterest_button_postInterest);
        post.setOnClickListener(this);
        addImage = (Button) findViewById(R.id.addInterest_button_addPhote);
        addImage.setOnClickListener(this);
        tags = (EditText) findViewById(R.id.addInterest_editText_tags);
        agreement = (CheckBox) findViewById(R.id.addInterest_checkBox_agreement);
        negotiable = (Switch) findViewById(R.id.addInterest_switch_Negotiable);
//        sun = (CheckBox) findViewById(R.id.addShare_checkBox_Sun);
//        mon = (CheckBox) findViewById(R.id.addShare_checkBox_Mon);
//        tue = (CheckBox) findViewById(R.id.addShare_checkBox_Tue);
//        wen = (CheckBox) findViewById(R.id.addShare_checkBox_Wen);
//        thu = (CheckBox) findViewById(R.id.addShare_checkBox_Thu);
//        fri = (CheckBox) findViewById(R.id.addShare_checkBox_Fri);
        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(AddIntrest.this);
        token = mypreference.getString("token", "value");
        Log.i("Token", token);
        loading = new ProgressDialog(this);
        loading.setMessage("Loading...");


    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Spinner spinner = (Spinner) parent;
//        if (spinner.getId() == R.id.addintrest_gender_spinner2) {
//            genderitem = parent.getItemAtPosition(position).toString();
//
//            // Showing selected spinner item
//
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

    @Override
    public void onClick(View v) {
        if (v == post) {
            if (!guarantee.getText().toString().isEmpty()) {

                String title = interestTitle.getText().toString();
                String interestDescription = description.getText().toString();
                String interestTag = tags.getText().toString();
                String interestPieces = pieces.getText().toString();
                String interestPrice = price.getText().toString();
                String interestDuration = duration.getText().toString();
//            String interstStartDate = startDate.getText().toString();
//            String interstEndDate = endDate.getText().toString();
                int guaranteePayment = Integer.parseInt(guarantee.getText().toString());


                ArrayList<String> tagsArr = new ArrayList<String>(Arrays.asList(interestTag.split("#")));
                JSONArray tagsJsonArr = new JSONArray();
                for (int i = 1; i < tagsArr.size(); i++) {
                    tagsJsonArr.put(tagsArr.get(i));
                }

                Log.i("tags string", tagsArr.toString());
                Log.i("tags", tagsJsonArr.toString());


                if (check = agreement.isChecked()) {

//                String params_Date=
//                    ( "{"
//                            +"\"type\": \"poster_request\","
//                   + "\"title\":" +"\""+SintrestTitle+"\","
//                   + "\"description\":"+ "\""+Sdescription+"\","
//                   + "\"tags\": ["+tagparts+"],"
//                +"\"pieces\":0,"
//                      +  "\"price\": {\"min\":"+ miprice+",\"max\":"+maprice+"},"
//                +"\"duration\": {\"max\":"+Stime+"}"
//
//
//
//                +"}" );
                    try {
                        //K.A: To make it easy to create a request, create a json file in resources and parse it

                        InputStream is = getApplicationContext().getResources().openRawResource(R.raw.poster_request);
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        String string_request = new String(buffer, "UTF-8");
                        Log.i("Parsed JSON file", string_request);
                        jsonObject = new JSONObject(string_request);
                        jsonObject.put("type", "request");
                        jsonObject.put("title", title);
                        jsonObject.put("description", interestDescription);
                        jsonObject.put("tags", tagsJsonArr);
                        jsonObject.put("pieces", interestPieces);
                        jsonObject.put("guarantee", guaranteePayment);
                        jsonObject.put("negotiable", negotiable.isChecked());
                        jsonObject.getJSONObject("price").put("min", interestPrice);
                        jsonObject.getJSONObject("price").put("max", interestPrice);
                        jsonObject.getJSONObject("duration").put("max", interestDuration);
                        jsonObject.getJSONObject("duration").put("min", "1");
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("sat",sat.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("sun",sun.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("mon",mon.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("tue",tue.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("wen",wen.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("thu",thu.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("fri",fri.isChecked());
                        jsonObject.getJSONObject("availability").getJSONObject("from").put("hour", "12");
                        jsonObject.getJSONObject("availability").getJSONObject("from").put("minute", "0");
                        jsonObject.getJSONObject("availability").getJSONObject("to").put("hour", "11");
                        jsonObject.getJSONObject("availability").getJSONObject("to").put("minute", "59");

                        Log.i("Final_Poster_Request", jsonObject.toString());

                        loading.show();
                        final String URL = "https://api.sharekeg.com/poster";

                        final JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        loading.dismiss();
                                        Log.i("response", response.toString());
                                        Toast.makeText(AddIntrest.this, "Done", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(AddIntrest.this, HomePage.class);
                                        startActivity(intent);

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // handle error
                                loading.dismiss();
                                Toast.makeText(AddIntrest.this, Utils.GetErrorDescription(error, AddIntrest.this), Toast.LENGTH_SHORT).show();
                                Log.i("error", error.toString());
                            }
                        }) {

                            public String getBodyContentType() {
                                return "application/json";
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
//                        HashMap<String, String> headers = new HashMap<String, String>();
//                        headers.put("Content-Type", "application/json");
//                        headers.put("Authorization", "Bearer " + token);
//                        return headers;
                                Utils utils = new Utils();

                                return utils.getRequestHeaders(token);
                            }
                        };
                        AppController.getInstance().addToRequestQueue(req);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddIntrest.this, "oops! Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.i("Error parsing JSON", e.toString());
                    }


                } else {
                    Toast.makeText(AddIntrest.this, "Please assure that you agree to the terms", Toast.LENGTH_SHORT).show();
                }

            }
        }


    }
}


