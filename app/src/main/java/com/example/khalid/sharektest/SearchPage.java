package com.example.khalid.sharektest;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.khalid.sharektest.Utils.CustomAdapterSearchPage;
import com.example.khalid.sharektest.Utils.SearchObject;

import java.util.ArrayList;

public class SearchPage extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView slistView;
    String query="jgjgjg";
    CustomAdapterSearchPage customAdapterSearchPage;
    ArrayList<SearchObject> searchObjectArrayList =new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent searchIntent = getIntent();
        String s=searchIntent.getStringExtra("search_tag");
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
             query = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
            Log.d("Ssssssss",query+"Sssssssss");}
//        slistView=(ListView)findViewById(R.id.searchpage_listView);
        SearchObject object1 = new SearchObject("FFFF0","jfffffffffffffffffffffffffffffffffffffffffffffffffffff","Fayoum","http://image.slidesharecdn.com/genre-lesson-130909102044-/95/types-of-genres-3-638.jpg?cb=1378722089");
        SearchObject object2 = new SearchObject("FFFF0","jfffffffffffffffffffffffffffffffffffffffffffffffffffff","Fayoum","http://digiliteratelibrarian.weebly.com/uploads/9/1/0/6/9106594/_918552.jpg");
        SearchObject object3 = new SearchObject("FFFF0","jfffffffffffffffffffffffffffffffffffffffffffffffffffff","Fayoum","http://i.imgur.com/DvpvklR.png");

        searchObjectArrayList.add(object1);
        searchObjectArrayList.add(object2);
        searchObjectArrayList.add(object3);



        CustomAdapterSearchPage customAdapterSearchPage=new CustomAdapterSearchPage(this, searchObjectArrayList);
        slistView.setAdapter(customAdapterSearchPage);

        slistView.setOnItemClickListener(this);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_button)
                .getActionView();
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                return true;
            }

            public boolean onQueryTextSubmit(String squery) {
                //Here u can get the value "query" which is entered in the search box.
                query=squery;
                return true;

            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





    }
}
