package com.example.khalid.sharektest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khalid.sharektest.Utils.CustomAdapterSearchPage;
import com.example.khalid.sharektest.Utils.Poster;
import com.example.khalid.sharektest.Utils.PostersCustomAdapter;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ArrayList<Poster> posters = new ArrayList<>();
    PostersCustomAdapter postersCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent cameIntent=new Intent();

        listView = (ListView) findViewById(R.id.homePage_listView);
        Poster poster1 = new Poster("Book", "Amazing Book", "10", "Fayoum", "");
        Poster poster2 = new Poster("Book", "Amazing Book", "10", "Fayoum", "");
        Poster poster3 = new Poster("Book", "Amazing Book", "10", "Fayoum", "");
        Poster poster4 = new Poster("Book", "Amazing Book", "10", "Fayoum", "");
        Poster poster5 = new Poster("Book", "Amazing Book", "10", "Fayoum", "");
        posters.add(poster1);
        posters.add(poster2);
        posters.add(poster3);
        posters.add(poster4);
        posters.add(poster5);

        PostersCustomAdapter postersCustomAdapter = new PostersCustomAdapter(getApplicationContext(), posters);
        listView.setAdapter(postersCustomAdapter);

//        listView.setOnItemClickListener(this);


        if(cameIntent.getBooleanExtra("loggedIn",false)){
            SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(HomePage.this);
            mypreference.edit().putBoolean("loggedIn", true).apply();
            String token = cameIntent.getStringExtra("token");
            mypreference.edit().putString("token",token);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Intent intent = new Intent(this,MyProfile.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_categories) {
            Intent intent = new Intent(this,CommonTagsPage.class);
            startActivity(intent);
        } else if (id == R.id.nav_about_us) {


        } else if (id == R.id.nav_contact_us) {
            Intent intent = new Intent(this,ContactUs.class);
            startActivity(intent);}
        else if (id == R.id.nav_add_intrest) {
            Intent intent = new Intent(this,AddIntrest.class);
            startActivity(intent);}
        else if (id == R.id.nav_add_share) {
            Intent intent = new Intent(this,AddShare.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
