package com.example.khalid.sharektest.Utils;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.khalid.sharektest.R;
        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;

/**
 * Created by User on 19/10/2016.
 */
public class CustomAdapterSearchPage  extends ArrayAdapter<SearchObject> {
    public CustomAdapterSearchPage(Context context, ArrayList<SearchObject> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        SearchObject user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_row_search, parent, false);
        }
        // Lookup view for data population
        TextView pdName = (TextView) convertView.findViewById(R.id.customsearchrow_productname_textview);
        TextView pdLoction = (TextView) convertView.findViewById(R.id.customsearchrow_loction_textView);
        TextView pdDes= (TextView) convertView.findViewById(R.id.customsearchrow_descproduct_textView);
        ImageView pdPic= (ImageView) convertView.findViewById(R.id.customsearchrow_productpic_imageView);
        // Populate the data into the template view using the data object
        pdName.setText(user.getProductname());
        pdLoction.setText(user.getProductloction());
        pdDes.setText(user.getProductdes());
        Picasso.with(getContext())
                .load(user.getUrlProductpic())
                .resize(75, 75)
                .centerCrop()
                .into(pdPic);

        // Return the completed view to render on screen
        return convertView;
    }

}