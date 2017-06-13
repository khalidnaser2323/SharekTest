package com.example.khalid.sharektest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.CommonTags_PagerAdapter;
import com.example.khalid.sharektest.Utils.Poster;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class CommonTags extends AppCompatActivity {
    String tagName;
    String token;
    ArrayList<Poster> CommonTags_interests = new ArrayList<>(), CommonTags_shares = new ArrayList<>();
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home_page);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Shares"));
        tabLayout.addTab(tabLayout.newTab().setText("Interests"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(CommonTags.this);
        if (mypreference.getBoolean("loggedIn", false)) {
//            token = cameIntent.getStringExtra("token");
            token = mypreference.getString("token", "value");
            Log.i("Token in Home", token);
        }

        Intent intent = getIntent();
        tagName = intent.getStringExtra("value");
        String url = "https://api.sharekeg.com/tag/" + tagName;
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("Response: ", response.toString());
                        pDialog.dismiss();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonResponse = response.getJSONObject(i);
                                String posterId = jsonResponse.get("_id").toString();
                                String title = jsonResponse.get("title").toString();
                                String description = jsonResponse.get("description").toString();
                                String price = jsonResponse.getJSONObject("price").get("min").toString();
                                String duration = jsonResponse.getJSONObject("duration").get("max").toString();
                                if (jsonResponse.get("type").toString().equals("offer")) {
                                    Poster share = new Poster(posterId, title, description, price, duration, "");
                                    CommonTags_shares.add(share);
                                } else if (jsonResponse.get("type").toString().equals("request")) {
                                    Poster interest = new Poster(posterId, title, description, price, duration, "");
                                    CommonTags_interests.add(interest);
                                }
                                ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                                tabLayout.setupWithViewPager(viewPager);
                                CommonTags_PagerAdapter adapter = new CommonTags_PagerAdapter
                                        (getSupportFragmentManager(), tabLayout.getTabCount());
                                viewPager.setAdapter(adapter);
                                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                                    @Override
                                    public void onTabSelected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabUnselected(TabLayout.Tab tab) {

                                    }

                                    @Override
                                    public void onTabReselected(TabLayout.Tab tab) {

                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//
                Utils utils = new Utils();

                return utils.getRequestHeaders(token);
            }
        };

        AppController.getInstance().addToRequestQueue(req);

    }

    public ArrayList<Poster> getCommonInterests() {
        return CommonTags_interests;
    }

    public ArrayList<Poster> getCommonShares() {
        return CommonTags_shares;
    }
}
