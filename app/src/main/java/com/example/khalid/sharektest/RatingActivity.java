package com.example.khalid.sharektest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class RatingActivity extends AppCompatActivity implements View.OnClickListener {
    String token;
    private Button button;
    private RatingBar ratingBar;
    private JSONObject jsonObject;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(RatingActivity.this);
        if (mypreference.getBoolean("loggedIn", false)) {
            token = mypreference.getString("token", "value");
            Log.i("Token in Rate activity", token);
        }


        ratingBar = (RatingBar) findViewById(R.id.Rate_rateNumber);
        ratingBar.setStepSize(1);
        button = (Button) findViewById(R.id.Rate_Button_Submit);
        final Intent intent = getIntent();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String ob = String.valueOf(ratingBar.getRating());
                try {
                    // send string value of rating in jasonObject its key rate

                    jsonObject = new JSONObject();
                    jsonObject.put("stars", ob);
                    Log.i("Rating Request", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String URL = "https://api.sharekeg.com/poster/" + intent.getStringExtra("poster_id") + "/" + intent.getStringExtra("poster_user") + "/review";

                Toast.makeText(getApplicationContext(), "The rate is  " + ob, Toast.LENGTH_SHORT).show();

                // initiate the connection with server
                final JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("response", response.toString());
                                Toast.makeText(RatingActivity.this, "Thank you for Rating", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RatingActivity.this, HomePage.class);
                                startActivity(intent);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                        if (error.networkResponse != null) {
                            if (error.networkResponse.statusCode == 400) {
                                Toast.makeText(getApplicationContext(), "The rate is not valid ", Toast.LENGTH_SHORT).show();
                            }
                        }
                        Log.i("error", error.toString());
                    }
                }) {

                    public String getBodyContentType() {
                        return "application/json";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
//
                        Utils utils = new Utils();

                        return utils.getRequestHeaders(token);
                    }
                };

                // K.A: missing a line to add request in request queue
                AppController.getInstance().addToRequestQueue(req);

            }
        });


    }

    @Override
    public void onClick(View v) {

    }
}
