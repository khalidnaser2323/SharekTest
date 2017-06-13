package com.example.khalid.sharektest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.khalid.sharektest.Utils.Poster;
import com.example.khalid.sharektest.Utils.PostersCustomAdapter;

import java.util.ArrayList;

/**
 * Created by Abdelrahman on 6/9/2017.
 */

public class interest_tab extends Fragment
        implements AdapterView.OnItemClickListener {
    PostersCustomAdapter interestsCustomAdapter;
    ListView interestsListView;
    ArrayList<Poster> interests = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_homepage_inteests_tab, container, false);
        interestsListView = (ListView) view.findViewById(R.id.homePage_interests);
        interests = ((HomePage) getActivity()).getInterests();
        interestsCustomAdapter = new PostersCustomAdapter(getContext(), interests);
        interestsListView.setAdapter(interestsCustomAdapter);
        interestsListView.setOnItemClickListener(this);
        return view;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), ProductPage.class);

        intent.putExtra("product_title", interests.get(position).getTitle());
        intent.putExtra("product_id", interests.get(position).getPosterID());
        startActivity(intent);
    }

}