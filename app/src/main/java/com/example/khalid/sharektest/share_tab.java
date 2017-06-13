package com.example.khalid.sharektest;

/**
 * Created by Abdelrahman on 6/9/2017.
 */


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

public class share_tab extends Fragment
        implements AdapterView.OnItemClickListener {

    PostersCustomAdapter sharesCustomAdapter;
    ListView sharesListView;
    ArrayList<Poster> shares = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_homepage_shares_tab, container, false);
        sharesListView = (ListView) view.findViewById(R.id.homePage_shares);
        shares = ((HomePage) getActivity()).getShares();
        sharesCustomAdapter = new PostersCustomAdapter(getContext(), shares);
        sharesListView.setAdapter(sharesCustomAdapter);
        sharesListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), ProductPage.class);

        intent.putExtra("product_title", shares.get(position).getTitle());
        intent.putExtra("product_id", shares.get(position).getPosterID());
        startActivity(intent);
    }


}