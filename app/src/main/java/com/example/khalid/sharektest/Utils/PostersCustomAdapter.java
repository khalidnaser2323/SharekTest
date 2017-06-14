package com.example.khalid.sharektest.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.khalid.sharektest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Khalid on 11/15/2016.
 */
public class PostersCustomAdapter extends ArrayAdapter<Poster> {
    public PostersCustomAdapter(Context context, ArrayList<Poster> posters) {
        super(context, 0, posters);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Poster poster = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }
        // Lookup view for data population
        TextView productTitle = (TextView) convertView.findViewById(R.id.productItem_title_textView);
        TextView productDesc = (TextView) convertView.findViewById(R.id.productItem_description_textView);
        TextView productPrice = (TextView) convertView.findViewById(R.id.productItem_price_textView);
        // // TODO: 3/17/2017 change city to duration 
        TextView productCity = (TextView) convertView.findViewById(R.id.productItem_city_textView);
        NetworkImageView posterImage = (NetworkImageView) convertView.findViewById(R.id.productItem_image_imageView);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String imageURL = "https://api.sharekeg.com/poster/" + poster.getPosterID() + "/image";
        posterImage.setImageUrl(imageURL, imageLoader);
        // Populate the data into the template view using the data object
        String price = poster.getPrice() + "LE";
        String duration = poster.getDuration() + "Days";
        productTitle.setText(poster.getTitle());
        productDesc.setText(poster.getDescription());
        productPrice.setText(price);
        productCity.setText(duration);
        return convertView;
    }
}