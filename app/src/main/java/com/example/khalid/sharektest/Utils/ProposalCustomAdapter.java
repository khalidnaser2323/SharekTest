package com.example.khalid.sharektest.Utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khalid.sharektest.R;

import java.util.ArrayList;


/**
 * Created by Khalid on 4/8/2017.
 */
public class ProposalCustomAdapter extends ArrayAdapter<Proposal> {
    public ProposalCustomAdapter(Context context, ArrayList<Proposal> posters) {
        super(context, 0, posters);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Proposal proposal = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.proposal_row, parent, false);
        }
        // Lookup view for data population
        TextView proposalTitle = (TextView) convertView.findViewById(R.id.proposalItem_title_textView);
//        TextView proposalDesc = (TextView) convertView.findViewById(R.id.proposalItem_description_textView);
        TextView proposalPrice = (TextView) convertView.findViewById(R.id.proposalItem_price_textView);
        TextView proposalDuration = (TextView) convertView.findViewById(R.id.proposalItem_duration_textView);
        TextView proposalPieces = (TextView) convertView.findViewById(R.id.proposalItem_pieces_textView);
        TextView proposalFrom = (TextView) convertView.findViewById(R.id.proposalItem_from_textView);
        // Populate the data into the template view using the data object
        String price = proposal.getPrice() + " LE";
        String duration = proposal.getDuration() + " Days";
        String pieces = proposal.getPieces() + " Pieces";
        proposalTitle.setText(proposal.getTitle());
//        proposalDesc.setText(proposal.getDescription());
        proposalPrice.setText(price);
        proposalDuration.setText(duration);
        proposalPieces.setText(pieces);
        String startDate = "From " + getDate(proposal.getStartDate());
        proposalFrom.setText(startDate);
        return convertView;
    }

    private String getDate(String start) {
        String time1 = start.substring(0, start.indexOf("T"));
        return time1;
    }
}
