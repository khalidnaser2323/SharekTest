package com.example.khalid.sharektest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khalid on 7/30/2016.
 */
public class Tab1 extends android.support.v4.app.Fragment {


    String token;
    TextView myPhone, myPoints, myMail;
    public Tab1(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab1, container, false);
        myPhone = (TextView) view.findViewById(R.id.myProfile_about_phone);
        myPoints = (TextView) view.findViewById(R.id.myProfile_about_points);
        myMail = (TextView) view.findViewById(R.id.myProfile_about_mail);

        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(getContext());

        token = mypreference.getString("token", "value");

        String url = "https://api.sharekeg.com/user/" + mypreference.getString("myUserName", "");
        JsonObjectRequest req = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("My profile data: ", response.toString());
//                        pDialog.hide();
//                            setTabs();

                        try {
                            myPhone.setText(response.getString("phone"));
                            myPoints.setText(response.getString("points"));
                            myMail.setText(response.getString("email"));


                        } catch (Exception e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), Utils.GetErrorDescription(error, getContext()), Toast.LENGTH_LONG).show();
            }
        }) {

            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Utils utils = new Utils();
                return utils.getRequestHeaders(token);
            }
        };
        AppController.getInstance().addToRequestQueue(req);


        return view;

    }


}

