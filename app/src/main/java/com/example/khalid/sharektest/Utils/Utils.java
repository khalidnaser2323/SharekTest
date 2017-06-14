package com.example.khalid.sharektest.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.khalid.sharektest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khalid on 4/29/2017.
 */
public class Utils {
    public static String GetErrorDescription(VolleyError error, Context context) {

        String json = null;

        //K.A: to handle error message
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case 400:
                    json = new String(response.data);
                    Log.i("Connection Error", json);
                    json = trimMessage(json, "errors");
                    break;
                case 401:
                    Activity activity = (Activity) context;
                    Log.i("Error activity", activity.toString());
                    if (activity.toString().contains("MainActivity")) {
                        json = context.getResources().getString(R.string.error_401);
                    } else {
                        json = context.getResources().getString(R.string.error_unauthorized);
                    }
                    break;

                case 502:
                    json = context.getResources().getString(R.string.error_502);
                    break;
                case 500:
                    json = context.getResources().getString(R.string.error_500);
                    break;

                default:
                    json = context.getResources().getString(R.string.error_default);
            }

        } else {
            json = "Please check your internet connectivity";
        }

        return json;
    }

    public static String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    public static String GetErrorByKey(String errorMessage) {
        String error = null;
        try {
            JSONObject errorsObj = new JSONObject(errorMessage);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return error;
    }

    public Map<String, String> getRequestHeaders(String token) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        if (token != null) {
            headers.put("Authorization", "Bearer " + token);
        }
        return headers;
    }

    public String convertBitMapToString(Bitmap image) {
        String photo = "";
        if (image != null) {
            image = getResizedBitmap(image, 150, 150);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            photo = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        return photo;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }

}
