package com.example.khalid.sharektest.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView productCity = (TextView) convertView.findViewById(R.id.productItem_city_textView);
        ImageView productImage = (ImageView) convertView.findViewById(R.id.productItem_image_imageView);
        // Populate the data into the template view using the data object
        productTitle.setText(poster.getTitle());
        productDesc.setText(poster.getDescription());
        productPrice.setText(poster.getPrice());
        productCity.setText(poster.getCity());
//        Picasso.with(getContext())
//                .load(poster.getPicUrl())
//                .resize(75, 75)
//                .centerCrop()
//                .into(productImage);

        // Return the completed view to render on screen
        return convertView;
    }
}