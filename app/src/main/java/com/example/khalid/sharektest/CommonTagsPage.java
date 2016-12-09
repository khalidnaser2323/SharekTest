package com.example.khalid.sharektest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.khalid.sharektest.Utils.CustomAdapterSearchPage;
import com.example.khalid.sharektest.Utils.SearchObject;
import com.example.khalid.sharektest.Utils.TagObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommonTagsPage extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView slistView;
    CustomAdapterTagPage adapterTagPage;
    ArrayList<TagObject>tagObjects=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tags_page);
        slistView=(ListView)findViewById(R.id.commontags_listView);
        TagObject object1=new TagObject("books","10");
        TagObject object5=new TagObject("cars","10");
        TagObject object2=new TagObject("tools","10");
        TagObject object3=new TagObject("keys","10");
        TagObject object4=new TagObject("pens","10");
        tagObjects.add(object1);
        tagObjects.add(object2);
        tagObjects.add(object3);
        tagObjects.add(object4);
        tagObjects.add(object5);
        adapterTagPage=new CustomAdapterTagPage(this, tagObjects);
        slistView.setAdapter(adapterTagPage);

        slistView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,SearchPage.class);
        intent.putExtra("search_tag",tagObjects.get(position).getName());

        startActivity(intent);
    }

    public class CustomAdapterTagPage  extends ArrayAdapter<TagObject> {
        public CustomAdapterTagPage(Context context, ArrayList<TagObject> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            TagObject user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row_common_tags, parent, false);
            }
            // Lookup view for data population
            TextView tagName = (TextView) convertView.findViewById(R.id.customcommontags_name_textView);
            TextView tagnum= (TextView)convertView.findViewById(R.id.customcommontags_numbers_textView);

            // Populate the data into the template view using the data object
            tagName.setText("#"+user.getName());
            tagnum.setText(user.getNum()+"products");


            // Return the completed view to render on screen
            return convertView;
        }

    }
}

