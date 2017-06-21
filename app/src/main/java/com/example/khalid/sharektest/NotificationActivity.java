package com.example.khalid.sharektest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.NotificationAdaptor;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {
    public static final int MY_PERMISSIONS_REQUEST_CALL = 1;
    static ArrayList<com.example.khalid.sharektest.Utils.Notification> notifications = new ArrayList<>();
    ListView listView;
    NotificationAdaptor notificationAdaptor;
    JSONObject jsonObject;
    String token;
    String UserPhone;
    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkLocationPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        listView = (ListView) findViewById(R.id.Notification_listView);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Intent cameIntent = getIntent();

        try {
            if (cameIntent.getStringExtra("data") != null) {
                //  noNotifications.setVisibility(View.GONE);
                jsonObject = new JSONObject(cameIntent.getStringExtra("data"));
                Log.i("NotificationJson", jsonObject.toString());
                com.example.khalid.sharektest.Utils.Notification notification = new com.example.khalid.sharektest.Utils.Notification(jsonObject.getString("userId"), jsonObject.getString("at"), jsonObject.getString("body"), jsonObject.getString("posterId"));
                notifications.add(notification);

            }

        } catch (JSONException e) {
            e.printStackTrace();

        }
        notificationAdaptor = new NotificationAdaptor(getApplicationContext(), notifications);
        listView.setAdapter(notificationAdaptor);
        listView.setOnItemClickListener(this);
//        listView.setOnItemLongClickListener(this);
        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(NotificationActivity.this);
        token = mypreference.getString("token", "value");


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //K.A: navigate to product
//        com.example.khalid.sharektest.Utils.Notification notification = notifications.get(position);
//        Intent intent = new Intent(NotificationActivity.this, ProductPage.class);
//        intent.putExtra("product_id", notification.getPosterId());
//        intent.putExtra("notification", true);
//        startActivity(intent);

        final com.example.khalid.sharektest.Utils.Notification notification = notifications.get(position);

        String url = "https://api.sharekeg.com/user/" + notification.getUserId();
        JsonObjectRequest req = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response: ", response.toString());
//                        pDialog.dismiss();


                        try {
                            LayoutInflater inflater = LayoutInflater.from(NotificationActivity.this);
                            final View yourCustomView = inflater.inflate(R.layout.owner_info_dialog, null);
                            final Button ownerCall = (Button) yourCustomView.findViewById(R.id.ownerInfoDialog_Button_call);
                            ownerCall.setVisibility(View.VISIBLE);
                            ownerCall.setOnClickListener(NotificationActivity.this);
                            if (jsonObject.has("phone")) {
                                UserPhone = jsonObject.get("phone").toString();
                            } else {
                                ownerCall.setVisibility(View.GONE);
                            }
                            final TextView ownerName = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_name);
                            String userFullName = response.getJSONObject("name").get("first").toString() + " " + response.getJSONObject("name").get("last").toString();
                            ownerName.setText(userFullName);
                            final TextView ownerAddress = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_homeAddress);
                            if (response.has("address")) {
                                ownerAddress.setText(response.get("address").toString());
                            } else {
                                ownerAddress.setText("Not provided");
                            }


                            final TextView ownerWork = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_work);

                            if (response.has("work")) {
                                ownerWork.setText(response.get("work").toString());
                            } else {
                                ownerWork.setText("Not provided");
                            }
                            final TextView ownerPoints = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_points);
                            ownerPoints.setText(response.get("points").toString());
                            AlertDialog dialog = new AlertDialog.Builder(NotificationActivity.this)
                                    .setTitle("")
                                    .setView(yourCustomView)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                        }
                                    }).create();
                            dialog.show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error: ", error.toString());
//                pDialog.dismiss();
                Toast.makeText(NotificationActivity.this, Utils.GetErrorDescription(error, NotificationActivity.this), Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //K.A: show user Info

//        final com.example.khalid.sharektest.Utils.Notification notification = notifications.get(position);
//
//        String url = "https://api.sharekeg.com/user/" + notification.getUserId();
//        JsonObjectRequest req = new JsonObjectRequest(url,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i("Response: ", response.toString());
////                        pDialog.dismiss();
//
//
//                        try {
//                            LayoutInflater inflater = LayoutInflater.from(NotificationActivity.this);
//                            final View yourCustomView = inflater.inflate(R.layout.owner_info_dialog, null);
//                            final Button ownerCall = (Button) yourCustomView.findViewById(R.id.ownerInfoDialog_Button_call);
//                            ownerCall.setVisibility(View.VISIBLE);
//                            ownerCall.setOnClickListener(NotificationActivity.this);
//                            UserPhone = response.get("phone").toString();
//                            final TextView ownerName = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_name);
//                            String userFullName = response.getJSONObject("name").get("first").toString() + " " + response.getJSONObject("name").get("last").toString();
//                            ownerName.setText(userFullName);
//                            final TextView ownerAddress = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_homeAddress);
//                            if (response.has("address")) {
//                                ownerAddress.setText(response.get("address").toString());
//                            } else {
//                                ownerAddress.setText("Not provided");
//                            }
//
//
//                            final TextView ownerWork = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_work);
//
//                            if (response.has("work")) {
//                                ownerWork.setText(response.get("work").toString());
//                            } else {
//                                ownerWork.setText("Not provided");
//                            }
//                            final TextView ownerPoints = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_points);
//                            ownerPoints.setText(response.get("points").toString());
//                            AlertDialog dialog = new AlertDialog.Builder(NotificationActivity.this)
//                                    .setTitle("")
//                                    .setView(yourCustomView)
//                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int whichButton) {
//
//                                        }
//                                    }).create();
//                            dialog.show();
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.i("Error: ", error.toString());
////                pDialog.dismiss();
//                Toast.makeText(NotificationActivity.this, Utils.GetErrorDescription(error, NotificationActivity.this), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//
//            public String getBodyContentType() {
//                return "application/json";
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Utils utils = new Utils();
//
//                return utils.getRequestHeaders(token);
//            }
//        };
//        AppController.getInstance().addToRequestQueue(req);


        return false;
    }


    @Override
    public void onClick(View v) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + UserPhone));
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        try {
            startActivity(callIntent);
        } catch (SecurityException e) {
            Toast.makeText(NotificationActivity.this, "Please Allow app to make phone calls from settings", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(NotificationActivity.this,
                android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(NotificationActivity.this,
                    android.Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(NotificationActivity.this)
                        .setTitle("Get Call phone ")
                        .setMessage("we need to  call this number to contact")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(NotificationActivity.this,
                                        new String[]{android.Manifest.permission.CALL_PHONE},
                                        MY_PERMISSIONS_REQUEST_CALL);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(NotificationActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL);
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
            case MY_PERMISSIONS_REQUEST_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    return;


                }
                // permission was granted, yay! Do the
                // location-related task you need to do.
                if (ContextCompat.checkSelfPermission(NotificationActivity.this,
                        Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {

                    //Request location updates:
                    locationManager.requestLocationUpdates(provider, 400, 1, (android.location.LocationListener) NotificationActivity.this);
                    return;
                }


            }

        }
    }


}