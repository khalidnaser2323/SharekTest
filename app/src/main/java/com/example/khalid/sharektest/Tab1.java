package com.example.khalid.sharektest;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
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
public class Tab1 extends android.support.v4.app.Fragment implements View.OnClickListener {
    SharedPreferences mypreference;
    ImageView imageView;


    String token;
    TextView myPhone, myPoints, myMail, myUserName, myLocation, myWork;

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
        myWork = (TextView) view.findViewById(R.id.myProfile_about_work);
        myUserName = (TextView) view.findViewById(R.id.myProfile_about_name);
        imageView = (ImageView) view.findViewById(R.id.myProfile_imageView);
        imageView.setOnClickListener(this);
        mypreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        String url = "https://api.sharekeg.com/user/" + mypreference.getString("myUserName", "");

        ImageRequest ir = new ImageRequest(url + "/image", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
//                pDialog.dismiss();
                imageView.setImageBitmap(response);

            }
        }, 300, 300, null, null);

        AppController.getInstance().addToRequestQueue(ir);


        token = mypreference.getString("token", "value");

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
                            myWork.setText(response.getString("work"));
                            myUserName.setText(mypreference.getString("myUserName", ""));
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


    @Override
    public void onClick(View v) {


    }
}

