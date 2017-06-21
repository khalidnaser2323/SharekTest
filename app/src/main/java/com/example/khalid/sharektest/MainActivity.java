package com.example.khalid.sharektest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    String token;
    //This is the login activity
    Button homepage, loginbtn, singUp;
    EditText userName, password;
    ProgressDialog pDialog;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        homepage = (Button) findViewById(R.id.LoginPage_Skip_button);
//        homepage.setOnClickListener(this);
        loginbtn = (Button) findViewById(R.id.LoginPage_Login_button);
        loginbtn.setOnClickListener(this);
        singUp = (Button) findViewById(R.id.LoginPage_SignUp_button);
        singUp.setOnClickListener(this);

        pDialog = new ProgressDialog(this);
        userName = (EditText) findViewById(R.id.LoginPage_UserName_editText);
        password = (EditText) findViewById(R.id.LoginPage_Password_editText);


    }

    @Override
    public void onClick(View v) {
//        if (v == homepage) {
//            Intent intent = new Intent(this, HomePage.class);
//            startActivity(intent);
//
//        } else
        if (v == loginbtn) {
            // login (authentication) request
            String user = userName.getText().toString();
            String pass = password.getText().toString();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
            } else {
                String params_Date =
                        ("{"
                                + " \"user\":" + "\"" + user + "\"" + ","
                                + " \"pass\":" + "\"" + pass + "\"" + ","
                                + "\"method\":" + "\"json\""
                                + "}");
                try {
                    jsonObject = new JSONObject(params_Date);

                    Log.i("request", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String URL = "https://api.sharekeg.com/authenticate";

                pDialog.setMessage("loading");
                pDialog.show();
                final JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("response", response.toString());
                                Toast.makeText(MainActivity.this, "Welcome to sharekeg", Toast.LENGTH_SHORT).show();

                                try {
                                    token = response.getString("token");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                mypreference.edit().putBoolean("loggedIn", true).apply();
                                mypreference.edit().putString("token", token).apply();
                                mypreference.edit().putString("myUserName", userName.getText().toString()).apply();

                                Intent intent = new Intent(MainActivity.this, HomePage.class);
                                intent.putExtra("newAuthentication", true);
//                                intent.putExtra("token", token);

                                startActivity(intent);


                                pDialog.dismiss();

                                // handle response
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error
                        pDialog.dismiss();
//                   if(error.networkResponse != null){
//                        if (error.networkResponse.statusCode == 401) {
//                            Toast.makeText(MainActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
//
//                        }
//                        } else {
//                            Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                        }
//                        Log.i("error", error.toString());
//                        String errorDescription = ;
                        Toast.makeText(MainActivity.this, Utils.GetErrorDescription(error, MainActivity.this), Toast.LENGTH_SHORT).show();


                    }
                }) {

                    public String getBodyContentType() {
                        return "application/json";
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        HashMap<String, String> headers = new HashMap<String, String>();
//                        headers.put("Content-Type", "application/json");
//                        return headers;
                        Utils utils = new Utils();

                        return utils.getRequestHeaders(null);
                    }
                };
                AppController.getInstance().addToRequestQueue(req);
            }

        } else if (v == singUp) {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);

        }
    }

    @Override
    public void onBackPressed() {

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);


    }
}
