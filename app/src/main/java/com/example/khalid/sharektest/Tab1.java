package com.example.khalid.sharektest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONObject;

import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Khalid on 7/30/2016.
 */
public class Tab1 extends android.support.v4.app.Fragment implements View.OnClickListener {
    private static final int REQUEST_TAKE_poster_PHOTO = 1;
    private static int REQUEST_LOAD_poster_IMAGE = 2;
    SharedPreferences mypreference;
    NetworkImageView imageView;
    String url;
    String token;
    TextView myPhone, myPoints, myMail, myUserName, myAddress, myWork;

    Bitmap photo = null;

    public Tab1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab1, container, false);
        myAddress = (TextView) view.findViewById(R.id.myProfile_about_address);
        myPhone = (TextView) view.findViewById(R.id.myProfile_about_phone);
        myPoints = (TextView) view.findViewById(R.id.myProfile_about_points);
        myMail = (TextView) view.findViewById(R.id.myProfile_about_mail);
        myWork = (TextView) view.findViewById(R.id.myProfile_about_work);
        myUserName = (TextView) view.findViewById(R.id.myProfile_about_name);
        imageView = (NetworkImageView) view.findViewById(R.id.myProfile_imageView);
        //    imageView.setOnClickListener(this);
        mypreference = PreferenceManager.getDefaultSharedPreferences(getContext());
        url = "https://api.sharekeg.com/user/" + mypreference.getString("myUserName", "");

/*
/*
        ImageRequest ir = new ImageRequest(url + "/image", new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
//                pDialog.dismiss();
                imageView.setImageBitmap(response);

            }
        }, 300, 300, null, null);
*//*


        AppController.getInstance().addToRequestQueue(ir);

*/

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageView.setImageUrl(url + "/image", imageLoader);

        token = mypreference.getString("token", "value");

        JsonObjectRequest req = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("My profile data: ", response.toString());
//                        pDialog.hide();
//                            setTabs();

                        try {
                            myPhone.setText(response.getString("phone"));
                            myPoints.setText(response.getString("points"));
                            myMail.setText(response.getString("email"));
                            myWork.setText(response.getString("work"));
                            myUserName.setText(mypreference.getString("myUserName", ""));
                            myAddress.setText(response.getString("address"));

                        } catch (Exception e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), Utils.GetErrorDescription(error, getContext()), Toast.LENGTH_LONG).show();
            }
        }) {

            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Utils utils = new Utils();
                return utils.getRequestHeaders(token);
            }
        };
        AppController.getInstance().addToRequestQueue(req);


        return view;

    }


    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Option")
                .setItems(new String[]{"Camera", "Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePictureIntent, REQUEST_TAKE_poster_PHOTO);

                        } else {
                            Intent intent = new Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, REQUEST_LOAD_poster_IMAGE);

                        }

                    }
                }).create().show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_TAKE_poster_PHOTO && resultCode == RESULT_OK && null != data) {

                photo = (Bitmap) data.getExtras().get("data");


                Toast.makeText(getContext(), "Your profile image  is selected Successfully", Toast.LENGTH_LONG).show();


            }
            if (requestCode == REQUEST_LOAD_poster_IMAGE && resultCode == RESULT_OK && null != data && data.getData() != null) {

                Uri filePath = data.getData();
                photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                Toast.makeText(getContext(), "Your profile image is selected Successfully", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Log.i("Image_Error", e.toString());
            Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_LONG).show();
        }

    }


}

