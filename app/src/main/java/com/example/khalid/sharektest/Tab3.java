package com.example.khalid.sharektest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.Proposal;
import com.example.khalid.sharektest.Utils.ProposalCustomAdapter;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Khalid on 7/30/2016.
 */
public class Tab3 extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener, AdapterView.OnClickListener {


    ListView listView;
    TextView noProposals;
    ArrayList<Proposal> proposals = new ArrayList<>();
    ProposalCustomAdapter proposalCustomAdapter;
    String token, myUserName, price, duration, pieces, posterID, user, startDate;
    JSONObject proposalRequest;
    boolean isAccepted;
    Button showUsersInfo, showPostersInfo;
    String proposalUserName, proposalPosterID;

    public Tab3() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.tab3, container, false);
        listView = (ListView) view.findViewById(R.id.myProfile_listView);
        listView.setOnItemClickListener(this);
        listView.setOnItemClickListener(this);
        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        token = mypreference.getString("token", "value");
        myUserName = mypreference.getString("myUserName", "value");
        Log.i("Token in My proposals", token);
        noProposals = (TextView) view.findViewById(R.id.ProductPage_Proposals_NoProposals);

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
                            noProposals.setVisibility(view.VISIBLE);
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
                                    if (isAccepted == false) {
                                        Proposal proposal = new Proposal("Some one proposed to you", "Hey, I want to offer you a deal ", price, duration, pieces, startDate, posterID, isAccepted, user);
                                        proposals.add(proposal);
                                        noProposals.setVisibility(view.GONE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            ProposalCustomAdapter proposalCustomAdapter = new ProposalCustomAdapter(getContext(), proposals);
                            listView.setAdapter(proposalCustomAdapter);


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
        proposalUserName = proposal.getUser();
        proposalPosterID = proposal.getPosterId();

        if (proposal.isAccepted()) {
            Toast.makeText(getContext(), "This proposal is accepted", Toast.LENGTH_LONG).show();
            //K.A: Disabled for next release
//            Intent intent = new Intent(getContext(),RatingActivity.class);
//            intent.putExtra("poster_user",proposal.getUser());
//            intent.putExtra("poster_id",proposal.getPosterId());
//            startActivity(intent);

        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View yourCustomView = inflater.inflate(R.layout.proposal_data_dialog, null);
            final TextView start = (TextView) yourCustomView.findViewById(R.id.ProposalInfoDialog_TextView_startDate);
            String startDate = "Start date:     " + proposal.getStartDate().substring(0, proposal.getStartDate().indexOf("T"));
            start.setText(startDate);
            final TextView duration = (TextView) yourCustomView.findViewById(R.id.ProposalInfoDialog_TextView_duration);
            String durationStr = "Duration:     " + proposal.getDuration() + "    Days";
            duration.setText(durationStr);
            final TextView pieces = (TextView) yourCustomView.findViewById(R.id.ProposalInfoDialog_TextView_pieces);
            String piecesStr = "Pieces:     " + proposal.getPieces();
            pieces.setText(piecesStr);
            showUsersInfo = (Button) yourCustomView.findViewById(R.id.ProposalInfoDialog_button_userInfo);
            showUsersInfo.setOnClickListener(Tab3.this);
            showPostersInfo = (Button) yourCustomView.findViewById(R.id.ProposalInfoDialog_button_posterInfo);
            showPostersInfo.setOnClickListener(Tab3.this);
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("")
                    .setView(yourCustomView)
                    .setPositiveButton("Accept", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            try {
                                proposalRequest = new JSONObject();
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
                                    Utils utils = new Utils();

                                    return utils.getRequestHeaders(token);
                                }
                            };
                            AppController.getInstance().addToRequestQueue(req);
                        }
                    }).setNegativeButton("Ignore", null).create();
            dialog.show();

        }

    }

    @Override
    public void onClick(View v) {
        if (v == showUsersInfo) {
            String url = "https://api.sharekeg.com/user/" + proposalUserName;
            JsonObjectRequest req = new JsonObjectRequest(url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Response: ", response.toString());

                            try {
                                LayoutInflater inflater = LayoutInflater.from(getContext());
                                final View yourCustomView = inflater.inflate(R.layout.owner_info_dialog, null);

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
                                AlertDialog dialog = new AlertDialog.Builder(getContext())
                                        .setTitle("Owner Info")
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
        } else if (v == showPostersInfo) {
            Intent intent = new Intent(getContext(), ProductPage.class);
            intent.putExtra("product_id", proposalPosterID);
            startActivity(intent);
        }

    }
}