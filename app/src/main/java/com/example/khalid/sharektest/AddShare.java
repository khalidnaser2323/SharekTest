package com.example.khalid.sharektest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.khalid.sharektest.Utils.AppController;
import com.example.khalid.sharektest.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class AddShare extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_TAKE_poster_PHOTO = 1;
    private static int REQUEST_LOAD_poster_IMAGE = 2;
    EditText interestTitle, pieces, startDate, description, duration, tags, price, endDate, guarantee;
    //    Spinner gender,catagory;
    CheckBox agreement;
    Switch negotiable;
    Button post, addImage;
    Boolean check;
    //    String genderitem, catitem;
    JSONObject jsonObject;
    String token;
    Bitmap photo = null;
    ProgressDialog loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_share);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        interestTitle = (EditText) findViewById(R.id.addShare_editText_Title);
        pieces = (EditText) findViewById(R.id.addShare_editText_Pieces);
        startDate = (EditText) findViewById(R.id.addShare_editText_fromDate);
        description = (EditText) findViewById(R.id.addShare_editText_Description);
        duration = (EditText) findViewById(R.id.addShare_editText_Duration);
        endDate = (EditText) findViewById(R.id.addShare_editText_toDate);
        price = (EditText) findViewById(R.id.addShare_editText_price);
        guarantee = (EditText) findViewById(R.id.addShare_editText_guarantee);
        post = (Button) findViewById(R.id.addShare_button_postShare);
        post.setOnClickListener(this);
        addImage = (Button) findViewById(R.id.addShare_button_addImage);
        addImage.setOnClickListener(this);
        tags = (EditText) findViewById(R.id.addShare_editText_tags);
        agreement = (CheckBox) findViewById(R.id.addShare_checkBox_agreement);
        negotiable = (Switch) findViewById(R.id.addShare_editText_Negotiable);
//        sun = (CheckBox) findViewById(R.id.addShare_checkBox_Sun);
//        mon = (CheckBox) findViewById(R.id.addShare_checkBox_Mon);
//        tue = (CheckBox) findViewById(R.id.addShare_checkBox_Tue);
//        wen = (CheckBox) findViewById(R.id.addShare_checkBox_Wen);
//        thu = (CheckBox) findViewById(R.id.addShare_checkBox_Thu);
//        fri = (CheckBox) findViewById(R.id.addShare_checkBox_Fri);
        SharedPreferences mypreference = PreferenceManager.getDefaultSharedPreferences(AddShare.this);
        token = mypreference.getString("token", "value");
        Log.i("Token", token);
        loading = new ProgressDialog(this);
        loading.setMessage("Loading...");


    }

    @Override
    public void onClick(View v) {
        if (v == post) {

            if (!guarantee.getText().toString().isEmpty()) {
                String title = interestTitle.getText().toString();
                String interestDescription = description.getText().toString();
                String interestTag = tags.getText().toString();
                String interestPieces = pieces.getText().toString();
                String interestPrice = price.getText().toString();
                String interestDuration = duration.getText().toString();
//                String interstStartDate = startDate.getText().toString();
//                String interstEndDate = endDate.getText().toString();

                int guaranteePayment = Integer.parseInt(guarantee.getText().toString());

                ArrayList<String> tagsArr = new ArrayList<String>(Arrays.asList(interestTag.split("#")));
                JSONArray tagsJsonArr = new JSONArray();
                for (int i = 1; i < tagsArr.size(); i++) {
                    tagsJsonArr.put(tagsArr.get(i));
                }

                Log.d("tags", tagsJsonArr.toString());


                if (check = agreement.isChecked()) {
                    try {
                        //K.A: To make it easy to create a request, create a json file in resources and parse it
                        InputStream is = getApplicationContext().getResources().openRawResource(R.raw.poster_request);
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        String string_request = new String(buffer, "UTF-8");
                        Log.i("Parsed JSON file", string_request);
                        jsonObject = new JSONObject(string_request);
                        jsonObject.put("type", "offer");
                        jsonObject.put("title", title);
                        jsonObject.put("description", interestDescription);
                        jsonObject.put("tags", tagsJsonArr);
                        jsonObject.put("pieces", interestPieces);
                        jsonObject.put("guarantee", guaranteePayment);
                        jsonObject.put("negotiable", negotiable.isChecked());
                        jsonObject.getJSONObject("price").put("min", interestPrice);
                        jsonObject.getJSONObject("price").put("max", interestPrice);
                        jsonObject.getJSONObject("duration").put("max", interestDuration);
                        jsonObject.getJSONObject("duration").put("min", "1");
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("sat", sat.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("sun", sun.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("mon", mon.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("tue", tue.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("wen", wen.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("thu", thu.isChecked());
//                    jsonObject.getJSONObject("availability").getJSONObject("days").put("fri", fri.isChecked());
                        jsonObject.getJSONObject("availability").getJSONObject("from").put("hour", "12");
                        jsonObject.getJSONObject("availability").getJSONObject("from").put("minute", "0");
                        jsonObject.getJSONObject("availability").getJSONObject("to").put("hour", "11");
                        jsonObject.getJSONObject("availability").getJSONObject("to").put("minute", "59");

                        Utils utils = new Utils();
                        jsonObject.put("image", utils.convertBitMapToString(photo));
                        Log.i("Final_Poster_Request", jsonObject.toString());
                        loading.show();

                        final String URL = "https://api.sharekeg.com/poster";

                        final JsonObjectRequest req = new JsonObjectRequest(URL, jsonObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.i("response", response.toString());
                                        loading.dismiss();
                                        Toast.makeText(AddShare.this, "Done", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(AddShare.this, HomePage.class);
                                        startActivity(intent);


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // handle error
                                loading.dismiss();
                                Toast.makeText(AddShare.this, Utils.GetErrorDescription(error, AddShare.this), Toast.LENGTH_SHORT).show();
                                Log.i("error", error.toString());
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddShare.this, "oops! Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.i("Error parsing JSON", e.toString());
                    }
                } else {
                    Toast.makeText(AddShare.this, "Please assure that you agree to the terms", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(AddShare.this, "Please enter guarantee payment", Toast.LENGTH_LONG).show();
            }
        } else if (v == addImage) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddShare.this);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_TAKE_poster_PHOTO && resultCode == RESULT_OK && null != data) {

//                Uri filePath = data.getData();

//                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                photo = (Bitmap) data.getExtras().get("data");

                Toast.makeText(this, "Poster image  is selected Successfully", Toast.LENGTH_LONG).show();


            }
            if (requestCode == REQUEST_LOAD_poster_IMAGE && resultCode == Activity.RESULT_OK && null != data && data.getData() != null) {

                Uri filePath = data.getData();

                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                Toast.makeText(this, "Poster image is selected Successfully", Toast.LENGTH_LONG).show();


            }
        } catch (Exception e) {
            Log.i("Image_Error", e.toString());
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        }

    }
}
