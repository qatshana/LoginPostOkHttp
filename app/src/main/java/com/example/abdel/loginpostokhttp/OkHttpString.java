package com.example.abdel.loginpostokhttp;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  Created by Abdel on 5/11/2017.
 *  Aysnc class which takes url and returns string
 */

public class OkHttpString extends AsyncTask<String,Void,String> {

    OkHttpClient client = new OkHttpClient();
    @Override
    protected String doInBackground(String... params) {

        Request request = new Request.Builder()
                .url(params[0])
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            String text1="Call Failed";
            return text1;
        }

    }
}
