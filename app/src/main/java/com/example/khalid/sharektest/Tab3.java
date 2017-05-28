package com.example.khalid.sharektest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.Poster;
import com.example.khalid.sharektest.Utils.PostersCustomAdapter;
import com.example.khalid.sharektest.Utils.Proposal;
import com.example.khalid.sharektest.Utils.ProposalCustomAdapter;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khalid on 7/30/2016.
 */
public class Tab3 extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    ListView listView;
    ArrayList<Proposal> proposals = new ArrayList<>();
    ProposalCustomAdapter proposalCustomAdapter;
    String token, myUserName, price, duration, pieces, posterID, user, startDate;
    JSONObject proposalRequest;
    boolean isAccepted;

    public Tab3() {
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
        View view = inflater.inflate(R.layout.tab3, container, false);
        listView = (ListView) view.findViewById(R.id.myProfile_listView);
        listView.setOnItemClickListener(this);
        listView.setOnItemClickListener(this);
        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = mypreference.getString("token", "value");
        myUserName = mypreference.getString("myUserName", "value");
        Log.i("Token in My proposals", token);


        String tag_json_arry = "json_array_req";

        String url = "https://api.sharekeg.com/proposals";

//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("Response: ", response.toString());
//                        pDialog.hide();
                        if (response.length() == 0) {
                            Toast.makeText(getContext(), "You have no proposals", Toast.LENGTH_LONG).show();
                        } else {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonResponse = response.getJSONObject(i);
//                                String title = jsonResponse.get("title").toString();
//                                String description = jsonResponse.get("description").toString();
                                    price = jsonResponse.get("price").toString();
                                    duration = jsonResponse.get("duration").toString();
                                    pieces = jsonResponse.get("pieces").toString();
                                    startDate = jsonResponse.get("from").toString();
                                    posterID = jsonResponse.get("posterId").toString();
                                    user = jsonResponse.get("user").toString();
                                    isAccepted = jsonResponse.getBoolean("accepted");

                                    // get poster data
                                    String url = "https://api.sharekeg.com/poster/" + posterID;
                                    JsonObjectRequest req = new JsonObjectRequest(url,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    Log.i("Poster_data: ", response.toString());
                                                    try {
                                                        String title = response.get("title").toString();
                                                        Proposal proposal = new Proposal(title, "Hey, I want to offer you a deal ", price, duration, pieces, startDate, posterID, isAccepted, user);
                                                        proposals.add(proposal);

                                                        ProposalCustomAdapter proposalCustomAdapter = new ProposalCustomAdapter(getContext(), proposals);
                                                        listView.setAdapter(proposalCustomAdapter);

                                                    } catch (JSONException e) {
                                                        Toast.makeText(getContext(), "Failed to get poster data, please check your connection", Toast.LENGTH_SHORT).show();
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("Error: ", error.toString());
                                        }
                                    }) {

                                        public String getBodyContentType() {
                                            return "application/json";
                                        }

                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
//                                        HashMap<String, String> headers = new HashMap<String, String>();
//                                        headers.put("Content-Type", "application/json");
//                                        headers.put("Authorization", "Bearer " + token);
//                                        return headers;
                                            Utils utils = new Utils();

                                            return utils.getRequestHeaders(token);
                                        }
                                    };
                                    AppController.getInstance().addToRequestQueue(req);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//               Log.i("Error: ", error.getMessage());
                Toast.makeText(getContext(), "It looks like you cannot connect to the internet", Toast.LENGTH_LONG).show();
//                pDialog.hide();
            }
        }) {

            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Authorization", "Bearer " + token);
//                return headers;
                Utils utils = new Utils();

                return utils.getRequestHeaders(token);
            }
        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);


        return view;


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Proposal proposal = proposals.get(position);
        if (proposal.isAccepted()) {
            Toast.makeText(getContext(), "This proposal is accepted", Toast.LENGTH_LONG).show();
        } else {
            new AlertDialog.Builder(getContext())
                    .setTitle("Title")
                    .setMessage("Someone offers a deal for " + proposal.getDuration() + " days, Start date is " + proposal.getStartDate().substring(0, proposal.getStartDate().indexOf("T")))
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            try {
                                InputStream is = getContext().getResources().openRawResource(R.raw.accept_request);
                                int size = is.available();
                                byte[] buffer = new byte[size];
                                is.read(buffer);
                                is.close();
                                String string_request = new String(buffer, "UTF-8");
                                Log.i("Parsed JSON file", string_request);

                                proposalRequest = new JSONObject(string_request);
                                proposalRequest.put("accept", true);
                                Log.i("Final_request", proposalRequest.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.i("Request_exception", e.toString());
                            }
                            final String URL = "https://api.sharekeg.com/poster/" + proposal.getPosterId() + "/" + proposal.getUser() + "/react";
                            Log.i("Acceptance_URL", URL);

                            final JsonObjectRequest req = new JsonObjectRequest(URL, proposalRequest,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.i("response", response.toString());
                                            Toast.makeText(getContext(), "You have accepted this proposal, Your contact details wil be sent by email", Toast.LENGTH_LONG).show();
//                                                Intent intent = new Intent(ProductPage.this,HomePage.class);
//                                                startActivity(intent);

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // handle error
                                    if (error.networkResponse != null) {
                                        if (error.networkResponse.statusCode == 400) {
//                                            Toast.makeText(ProductPage.this, "Making proposal failed, Please check your data to send", Toast.LENGTH_SHORT).show();
                                            Log.i("Error string", error.networkResponse.data.toString());
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
//                                    HashMap<String, String> headers = new HashMap<String, String>();
//                                    headers.put("Content-Type", "application/json");
//                                    headers.put("Authorization", "Bearer " + token);
//                                    return headers;
                                    Utils utils = new Utils();

                                    return utils.getRequestHeaders(token);
                                }
                            };
                            AppController.getInstance().addToRequestQueue(req);
                        }
                    })
                    .setNegativeButton("Refuse", null).show();
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Proposal proposal = proposals.get(position);

        String url = "https://api.sharekeg.com/user/" + proposal.getUser();
        JsonObjectRequest req = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response: ", response.toString());
//                        pDialog.dismiss();


                        try {
                            LayoutInflater inflater = LayoutInflater.from(getContext());
                            final View yourCustomView = inflater.inflate(R.layout.owner_info_dialog, null);

                            final TextView ownerName = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_name);
                            String userFullName = response.getJSONObject("name").get("first").toString() + " " + response.getJSONObject("name").get("last").toString();
                            ownerName.setText(userFullName);
                            final TextView ownerGender = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_gender);
                            ownerGender.setText(response.get("gender").toString());
                            final TextView ownerPoints = (TextView) yourCustomView.findViewById(R.id.ownerInfoDialog_points);
                            ownerPoints.setText(response.get("points").toString());
                            AlertDialog dialog = new AlertDialog.Builder(getContext())
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
                Toast.makeText(getContext(), Utils.GetErrorDescription(error, getContext()), Toast.LENGTH_SHORT).show();
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
}