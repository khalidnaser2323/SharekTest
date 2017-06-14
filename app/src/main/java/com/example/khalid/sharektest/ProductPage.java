package com.example.khalid.sharektest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Map;

public class ProductPage extends AppCompatActivity implements View.OnClickListener {
    TextView Pname, Pprice, Pperiod, PType, Pdescription, Ppeices, productTags, guaranteePayment, negotiable;
    NetworkImageView Ppic;
    Button conatct, showInfo;
    ProgressDialog pDialog;
    String token, productId, proposalDate, propopsalDuration, title, price, proposalPieces, ownerUsername;
    JSONObject proposalRequest;
    SharedPreferences mypreference;
    LinearLayout offerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Pdescription = (TextView) findViewById(R.id.Product_TextView_Description);
        Pname = (TextView) findViewById(R.id.Product_TextView_ProductName);
        Pprice = (TextView) findViewById(R.id.product_price_textView);
        PType = (TextView) findViewById(R.id.Product_TextView_Interest);
        Pperiod = (TextView) findViewById(R.id.Product_TextView_Duration);
        productTags = (TextView) findViewById(R.id.Product_TextView_Tag);
        Ppeices = (TextView) findViewById(R.id.Product_TextView_Pieces);
        guaranteePayment = (TextView) findViewById(R.id.Product_TextView_Guarantee);
        negotiable = (TextView) findViewById(R.id.Product_TextView_negotiable);
        Ppic = (NetworkImageView) findViewById(R.id.Product_ImageView_ProductImage);
        offerData = (LinearLayout) findViewById(R.id.ProductPage_linearLayout_offerData);
        conatct = (Button) findViewById(R.id.Product_Button_Contact);
        showInfo = (Button) findViewById(R.id.Product_Button_ShowInfo);
        conatct.setOnClickListener(this);
        showInfo.setOnClickListener(this);
        Pdescription.setMovementMethod(new ScrollingMovementMethod());

        mypreference = PreferenceManager.getDefaultSharedPreferences(ProductPage.this);

        if (mypreference.getBoolean("loggedIn", false)) {
            token = mypreference.getString("token", null);
            Log.i("token", token);
            // to skip login page
        }


        Intent cameIntent = getIntent();
        productId = cameIntent.getStringExtra("product_id");
        if (cameIntent.getBooleanExtra("notification", false)) {
            conatct.setVisibility(View.GONE);
            showInfo.setVisibility(View.GONE);
        } else {
            conatct.setVisibility(View.VISIBLE);
            showInfo.setVisibility(View.VISIBLE);
        }

        //Todo: Use fetched posters, do not call server to get poster again
        String tag_json_object = "json_object_req";

        String url = "https://api.sharekeg.com/poster/" + productId;

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
//        ImageRequest ir = new ImageRequest(url + "/image", new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                pDialog.dismiss();
//                Ppic.setImageBitmap(response);
////                Picasso.with(getApplicationContext())
////                        .load(String.valueOf(response))
////                        .resize(300, 300)
////                        .centerCrop()
////                        .into(Ppic);
//            }
//        }, 300, 300, null, null);

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        Ppic.setImageUrl(url + "/image", imageLoader);

        JsonObjectRequest req = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response: ", response.toString());
                        pDialog.dismiss();


                        try {

                            JSONArray tags = response.getJSONArray("tags");
                            if (tags.length() == 0) {
                                String noTags = "No tags";
                                productTags.setText(noTags);
                            } else {
                                // change in tag
                                productTags.setText(tags.get(0).toString());

                            }
                            title = response.get("title").toString();
                            Pname.setText(title);
                            Pdescription.setText(response.get("description").toString());
                            price = response.getJSONObject("price").get("min").toString();
                            Pprice.setText(price);
                            String duration = response.getJSONObject("duration").get("max").toString();
                            Pperiod.setText(duration);
                            String type = response.get("type").toString();
                            PType.setText(type);
                            if (type.equals("request")) {
                                offerData.setVisibility(View.GONE);
                            }
                            String peices = response.get("pieces").toString();
                            Ppeices.setText(peices);
                            ownerUsername = response.getString("user");
                            if (ownerUsername.equals(mypreference.getString("myUserName", ""))) {
                                conatct.setVisibility(View.GONE);
                                showInfo.setVisibility(View.GONE);
                            }
                            if (response.has("negotiable")) {
                            if (response.getBoolean("negotiable")) {
                                negotiable.setText("Yes");
                            } else {
                                negotiable.setText("No");
                            }
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error: ", error.toString());
                pDialog.dismiss();
                Toast.makeText(ProductPage.this, "It looks like you cannot connect to the internet", Toast.LENGTH_LONG).show();
//                pDialog.hide();
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

//        AppController.getInstance().addToRequestQueue(ir);
        AppController.getInstance().addToRequestQueue(req);


    }

    @Override
    public void onClick(View v) {
        if (v == conatct) {

            LayoutInflater inflater = LayoutInflater.from(ProductPage.this);
            final View yourCustomView = inflater.inflate(R.layout.proposal_dialog, null);

            final EditText date = (EditText) yourCustomView.findViewById(R.id.propsalDialog_editText_date);
            final EditText duration = (EditText) yourCustomView.findViewById(R.id.propsalDialog_editText_duration);
            final EditText pieces = (EditText) yourCustomView.findViewById(R.id.propsalDialog_editText_pieces);
            AlertDialog dialog = new AlertDialog.Builder(ProductPage.this)
                    .setTitle("")
                    .setView(yourCustomView)
                    .setPositiveButton("Send Request", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            proposalDate = date.getText().toString();
                            propopsalDuration = duration.getText().toString();
                            proposalPieces = pieces.getText().toString();
                            if (proposalDate.isEmpty() || propopsalDuration.isEmpty() || proposalPieces.isEmpty()) {
                                Toast.makeText(ProductPage.this, "Please enter proposal data", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    InputStream is = getApplicationContext().getResources().openRawResource(R.raw.proposal_request);
                                    int size = is.available();
                                    byte[] buffer = new byte[size];
                                    is.read(buffer);
                                    is.close();
                                    String string_request = new String(buffer, "UTF-8");
                                    Log.i("Parsed JSON file", string_request);

                                    proposalRequest = new JSONObject(string_request);
                                    proposalRequest.put("pieces", proposalPieces);
                                    proposalRequest.put("title", title);
                                    proposalRequest.put("price", price);
                                    proposalRequest.put("duration", propopsalDuration);
                                    proposalRequest.put("from", proposalDate);
                                    Log.i("Final_request", proposalRequest.toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.i("Request_exception", e.toString());
                                }
                                pDialog.show();

                                final String URL = "https://api.sharekeg.com/poster/" + productId + "/propose";

                                final JsonObjectRequest req = new JsonObjectRequest(URL, proposalRequest,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.i("response", response.toString());
                                                pDialog.dismiss();
                                                Toast.makeText(ProductPage.this, "Proposal made successfully", Toast.LENGTH_LONG).show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        pDialog.dismiss();
                                        Toast.makeText(ProductPage.this, Utils.GetErrorDescription(error, ProductPage.this), Toast.LENGTH_SHORT).show();
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


                        }
                    })
                    .setNegativeButton("Cancel", null).create();
            dialog.show();


        }
        if (v == showInfo) {
            LayoutInflater inflater = LayoutInflater.from(ProductPage.this);
            final View yourCustomView = inflater.inflate(R.layout.owner_info_dialog, null);
            String url = "https://api.sharekeg.com/user/" + ownerUsername;
            JsonObjectRequest req = new JsonObjectRequest(url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Response: ", response.toString());
                            pDialog.dismiss();


                            try {

                                /*LayoutInflater inflater = LayoutInflater.from(ProductPage.this);
                                final View yourCustomView = inflater.inflate(R.layout.owner_info_dialog, null);
                                */// we need to get image of owner name
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
                                AlertDialog dialog = new AlertDialog.Builder(ProductPage.this)
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
                    pDialog.dismiss();
                    Toast.makeText(ProductPage.this, Utils.GetErrorDescription(error, ProductPage.this), Toast.LENGTH_SHORT).show();
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

    }
}
