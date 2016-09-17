package com.example.khalid.sharektest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//This is the signup activity
    Button homepage,loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       homepage = (Button)findViewById(R.id.LoginPage_Skip_button);
        homepage.setOnClickListener(this);
        loginbtn=(Button)findViewById(R.id.LoginPage_Login_button);
        loginbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v==homepage){
           Intent intent = new Intent(this,HomePage.class);
            startActivity(intent);
//            final String URL = "http://api.sharekeg.com/";
//
//            JsonObjectRequest req = new JsonObjectRequest(URL, null,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.i("response", response.toString());
//
//                            try {
//                                String message =response.getString("message");
//                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            // handle response
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    // handle error
//                    Log.i("error",error.toString());
//                }
//            }) {
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
////                    headers.put("CUSTOM_HEADER", "Yahoo");
//                    headers.put("Authorization", "Bearer");
//                    return headers;
//                }
//            };
//            AppController.getInstance().addToRequestQueue(req);

        }
        if (v==loginbtn){
            }
}
}
