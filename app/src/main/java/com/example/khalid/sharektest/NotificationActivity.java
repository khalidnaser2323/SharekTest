package com.example.khalid.sharektest;

import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    static ArrayList<com.example.khalid.sharektest.Utils.Notification> notifications = new ArrayList<>();
    ListView listView;
    NotificationAdaptor notificationAdaptor;
    JSONObject jsonObject;
    String token;
    String UserPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        listView = (ListView) findViewById(R.id.Notification_listView);
        Intent cameIntent = getIntent();
        try {
            jsonObject = new JSONObject(cameIntent.getStringExtra("data"));
            com.example.khalid.sharektest.Utils.Notification notification = new com.example.khalid.sharektest.Utils.Notification(jsonObject.getString("userId"), jsonObject.getString("at"), jsonObject.getString("body"), jsonObject.getString("posterId"));
            notifications.add(notification);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        notificationAdaptor = new NotificationAdaptor(getApplicationContext(), notifications);
        listView.setAdapter(notificationAdaptor);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
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
                            UserPhone = response.get("phone").toString();
                            final TextView ownerName = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_name);
                            String userFullName = response.getJSONObject("name").get("first").toString() + " " + response.getJSONObject("name").get("last").toString();
                            ownerName.setText(userFullName);
                            final TextView ownerAddress = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_homeAddress);
                            ownerAddress.setText(response.get("address").toString());
                            final TextView ownerWork = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_work);
                            ownerWork.setText(response.get("work").toString());
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
                            UserPhone = response.get("phone").toString();
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


        return false;
    }


    @Override
    public void onClick(View v) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + UserPhone));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

}
