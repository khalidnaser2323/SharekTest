package com.example.khalid.sharektest;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.CustomAdapterSearchPage;
import com.example.khalid.sharektest.Utils.MyFirebaseInstanceIDService;
import com.example.khalid.sharektest.Utils.Poster;
import com.example.khalid.sharektest.Utils.PostersCustomAdapter;
import com.example.khalid.sharektest.Utils.Utils;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String token;
    ArrayList<Poster> interests = new ArrayList<>(), shares = new ArrayList<>();
    ProgressDialog pDialog;
    TextView nav_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent cameIntent = getIntent();

        if (cameIntent.getBooleanExtra("newAuthentication", false)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        FirebaseInstanceId.getInstance().deleteInstanceId();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(HomePage.this);
        if (mypreference.getBoolean("loggedIn", false)) {
//            token = cameIntent.getStringExtra("token");
            token = mypreference.getString("token", "value");
            Log.i("Token in Home", token);
        }

        String notificationToken = FirebaseInstanceId.getInstance().getToken();
        if (notificationToken != null) {
            Log.i("Notification Token", notificationToken);
        }


        getPosters();
/*

        String url = "https://api.sharekeg.com/posters";

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
                                    shares.add(share);
                                } else if (jsonResponse.get("type").toString().equals("request")) {
                                    Poster interest = new Poster(posterId, title, description, price, duration, "");
                                    interests.add(interest);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                        SharesCustomAdapter = new PostersCustomAdapter(getApplicationContext(), shares);
                        sharesListView.setAdapter(SharesCustomAdapter);

                        interestsCustomAdapter = new PostersCustomAdapter(getApplicationContext(), interests);
                        interestsListView.setAdapter(interestsCustomAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(HomePage.this, "It looks like you cannot connect to the internet", Toast.LENGTH_LONG).show();
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
*/

/*
        sharesListView.setOnItemClickListener(this);
        interestsListView.setOnItemClickListener(this);*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        nav_userName = (TextView) header.findViewById(R.id.HomePage_Email_textView);
        nav_userName.setText(mypreference.getString("myUserName", ""));
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(HomePage.this);

            if (mypreference.getBoolean("loggedIn", false)) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            } else {
                super.onBackPressed();
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(HomePage.this, "Settings", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_refresh) {
            Toast.makeText(HomePage.this, "refreshed", Toast.LENGTH_LONG).show();
            getPosters();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Intent intent = new Intent(this, MyProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(this, Tags.class);


            startActivity(intent);


        } else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);

        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(this, help.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact_us) {
            Intent intent = new Intent(this, ContactUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_intrest) {
            Intent intent = new Intent(this, AddIntrest.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_share) {
            Intent intent = new Intent(this, AddShare.class);
            startActivity(intent);

        } else if (id == R.id.nav_log_out) {
            SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(HomePage.this);
            mypreference.edit().remove("token").apply();
            mypreference.edit().putBoolean("loggedIn", false).apply();
            mypreference.edit().remove("myUserName").apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ProductPage.class);
        if (parent.getId() == R.id.homePage_listView_shares) {
            intent.putExtra("product_title", shares.get(position).getTitle());
            intent.putExtra("product_id", shares.get(position).getPosterID());

        } else {
            intent.putExtra("product_title", interests.get(position).getTitle());
            intent.putExtra("product_id", interests.get(position).getPosterID());
        }

        startActivity(intent);

    }*/

    public ArrayList<Poster> getInterests() {
        return interests;
    }

    public ArrayList<Poster> getShares() {
        return shares;
    }

    public void getPosters() {
        shares.clear();
        interests.clear();
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Shares"));
        tabLayout.addTab(tabLayout.newTab().setText("Interests"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        String url = "https://api.sharekeg.com/posters";

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
                                    shares.add(share);
                                } else if (jsonResponse.get("type").toString().equals("request")) {
                                    Poster interest = new Poster(posterId, title, description, price, duration, "");
                                    interests.add(interest);
                                }
                                ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
                                tabLayout.setupWithViewPager(viewPager);
                                PagerAdapter adapter = new PagerAdapter
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
                pDialog.dismiss();
                Toast.makeText(HomePage.this, "You cannot connect to the internet", Toast.LENGTH_LONG).show();
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

}
