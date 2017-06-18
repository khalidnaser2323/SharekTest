package com.example.khalid.sharektest;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.GPSTracker;
import com.example.khalid.sharektest.Utils.SpinnerCustomArrayAdapter;
import com.example.khalid.sharektest.Utils.Utils;
import com.example.khalid.sharektest.tour.tour_welcome;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class SignUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int REQUEST_TAKE_poster_PHOTO = 1;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0x8000000;
    private static int REQUEST_LOAD_poster_IMAGE = 2;
    EditText fname, lname, uname, email, phone, day, year, month, pass, repass, work, haddress;
    TextView limit_haddress, limit_phone, limit_pass, limit_mail, limit_username;
    Spinner gender;
    Button sign, getlocation, uploadNationalID;
    String genderitem;
    double latitude = 0, longitude = 0;
    JSONObject jsonObject;
    String token;
    boolean getLocationBtnClicked;
    Bitmap photo = null;
    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fname = (EditText) findViewById(R.id.signup_fristname_editText);
        haddress = (EditText) findViewById(R.id.signup_address_editText);
        haddress.setOnClickListener(this);
        lname = (EditText) findViewById(R.id.signup_lastname_editText);
        uname = (EditText) findViewById(R.id.signup_username_editText);
        uname.setOnClickListener(this);
        email = (EditText) findViewById(R.id.signup_email_editText);
        email.setOnClickListener(this);
        phone = (EditText) findViewById(R.id.signup_phone_editText);
        phone.setOnClickListener(this);
        day = (EditText) findViewById(R.id.signup_day_editText);
        year = (EditText) findViewById(R.id.signup_year_editText);
        month = (EditText) findViewById(R.id.signup_month_editText);
        pass = (EditText) findViewById(R.id.signup_password_editText);
        pass.setOnClickListener(this);
        repass = (EditText) findViewById(R.id.signup_repassword_editText2);
        gender = (Spinner) findViewById(R.id.signup_gender_spinner);
        work = (EditText) findViewById(R.id.signup_Work_editText);
        sign = (Button) findViewById(R.id.signup_signup_button);
        uploadNationalID = (Button) findViewById(R.id.signup_uploadNationalId_button);
        getlocation = (Button) findViewById(R.id.signup_getlocation_button);
        limit_username = (TextView) findViewById(R.id.username);
        limit_mail = (TextView) findViewById(R.id.mail);
        limit_pass = (TextView) findViewById(R.id.pass);
        limit_phone = (TextView) findViewById(R.id.phone);
        limit_haddress = (TextView) findViewById(R.id.haddress);
        gender.setOnItemSelectedListener(this);
        sign.setOnClickListener(this);
        getlocation.setOnClickListener(this);
        uploadNationalID.setOnClickListener(this);

        SpinnerCustomArrayAdapter genderarr = new SpinnerCustomArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item);

        genderarr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderarr.add("Male");
        genderarr.add("Female");
        genderarr.add("Gender");

        gender.setAdapter(genderarr);
        gender.setSelection(genderarr.getCount());
        getLocationBtnClicked = false;
        checkLocationPermission();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        genderitem = parent.getItemAtPosition(position).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v == getlocation) {
            GPSTracker gps = new GPSTracker(this);
            if (gps.isGPSEnabled) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                Log.i("Sign Up activity", "Location button is clicked");
                Log.i("Longitude", String.valueOf(longitude));
                Log.i("latitude", String.valueOf(latitude));
                if (latitude != 0 && longitude != 0) {
                    getLocationBtnClicked = true;
                    Toast.makeText(SignUp.this, "Location is set successfully", Toast.LENGTH_SHORT).show();
                } else {
                    longitude = 30.8394507;
                    latitude = 29.3202185;
                    //K.A: this is to get over location issue, must be changed ASAP
                    Log.i("get_location_failed", "Setting location to Fayoum  as default");
                    getLocationBtnClicked = true;
                    Toast.makeText(SignUp.this, "Location is set successfully", Toast.LENGTH_SHORT).show();

                }
            } else {
                gps.showSettingsAlert();
            }
        } else if (v == sign) {

            if (photo == null) {

                sign.setOnClickListener(null);
                if (pass.getText().toString().equals(repass.getText().toString())) {

                    Utils utils = new Utils();
                    if (getLocationBtnClicked) {
                        String params_Date =
                                ("{"
                                        + " \"user\":" + "\"" + uname.getText().toString() + "\"" + ","
                                        + " \"pass\":" + "\"" + pass.getText().toString() + "\"" + ","
                                        + "\"address\":" + "\"" + haddress.getText().toString() + "\"" + ","
                                        + "     \"name\": {"
                                        + " \"first\":" + "\"" + fname.getText().toString() + "\"" + ","
                                        + "    \"last\":" + "\"" + lname.getText().toString() + "\""
                                        + "},"
                                        + "\"email\":" + "\"" + email.getText().toString() + "\"" + ","
                                        + "    \"birth\": {"
                                        + "\"day\":" + "\"" + day.getText().toString() + "\"" + ","
                                        + "        \"month\":" + "\"" + month.getText().toString() + "\"" + ","
                                        + "        \"year\":" + "\"" + year.getText().toString() + "\""
                                        + "} ,"
                                        + "\"location\": {"
                                        + "\"latitude\":" + "\"" + 29.3202185 + "\"" + ","
                                        + "        \"longitude\":" + "\"" + 30.8394507 + "\""
                                        + "},"
                                        + "\"phone\":" + "\"" + phone.getText().toString() + "\"" + ","
                                        + "    \"gender\":" + "\"" + genderitem + "\"" + ","
                                        + "    \"work\":" + "\"" + work.getText() + "\"" + ","
                                        + "    \"image\":" + "\"" + "0" + "\""
                                        + "}");
                        try {
                            jsonObject = new JSONObject(params_Date);
                            Log.i("sign_up_request", jsonObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        final String URL = "https://api.sharekeg.com/user";

                        final JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.i("response", response.toString());
                                        Toast.makeText(SignUp.this, "Welcome to sharekeg", Toast.LENGTH_SHORT).show();

                                        try {
                                            token = response.getString("token");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(SignUp.this);
                                        mypreference.edit().putBoolean("loggedIn", true).apply();
                                        mypreference.edit().putString("token", token).apply();
                                        mypreference.edit().putString("myUserName", uname.getText().toString()).apply();

                                        // Go to tour guide


                                        Intent intent = new Intent(SignUp.this, tour_welcome.class);
//                                    intent.putExtra("loggedIn", true);
//                                    intent.putExtra("token", token);
                                        //   intent.putExtra("newAuthentication", true);
                                        startActivity(intent);
//


                                        // handle response
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // handle error
//                            String errorDescription = ;

                                Toast.makeText(SignUp.this, Utils.GetErrorDescription(error, SignUp.this), Toast.LENGTH_LONG).show();


                            }
                        }) {

                            public String getBodyContentType() {
                                return "application/json";
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
//                            HashMap<String, String> headers = new HashMap<String, String>();
//                            headers.put("Content-Type", "application/json");
//                            return headers;
                                Utils utils = new Utils();

                                return utils.getRequestHeaders(null);
                            }
                        };
                        AppController.getInstance().addToRequestQueue(req);
                    } else {
                        Toast.makeText(this, "Review your Data and Recheck your Location", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUp.this, " Invalid Password ", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(SignUp.this, "Please upload your Profile picture", Toast.LENGTH_LONG).show();
            }
        } else if (v == uploadNationalID) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
            builder.setTitle("Choose Option")
                    .setItems(new String[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == 0) {
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takePictureIntent, REQUEST_TAKE_poster_PHOTO);


                            } else {
                                Intent intent = new Intent(
                                        Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, REQUEST_LOAD_poster_IMAGE);

                            }

                        }
                    }).create().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_TAKE_poster_PHOTO && resultCode == RESULT_OK && null != data) {

                photo = (Bitmap) data.getExtras().get("data");

                Toast.makeText(this, "National ID  is selected Successfully", Toast.LENGTH_LONG).show();


            }
            if (requestCode == REQUEST_LOAD_poster_IMAGE && resultCode == Activity.RESULT_OK && null != data && data.getData() != null) {

                Uri filePath = data.getData();

                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Toast.makeText(this, "National ID is selected Successfully", Toast.LENGTH_LONG).show();


            }
        } catch (Exception e) {
            Log.i("Image_Error", e.toString());
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        }

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(SignUp.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(SignUp.this)
                        .setTitle("Get Home Location ")
                        .setMessage("Kindly, we need your location to give an easy contact to your neighbors")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(SignUp.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(SignUp.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    return;


                }
                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(SignUp.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    locationManager.requestLocationUpdates(provider, 400, 1, (android.location.LocationListener) SignUp.this);
                    return;
                }


            }

        }
    }
}
