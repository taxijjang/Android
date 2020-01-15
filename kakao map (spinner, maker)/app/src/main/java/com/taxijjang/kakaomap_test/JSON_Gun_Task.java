package com.taxijjang.kakaomap_test;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSON_Gun_Task extends AsyncTask<String, Void, JSONArray> {
    JSONArray jArr;

    @Override
    protected JSONArray doInBackground(String ... strUrl){
        try{
            URL url = new URL(strUrl[0]);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuffer builder = new StringBuffer();

            String inputString = null;

            while((inputString = bufferedReader.readLine()) !=null){
                builder.append(inputString);
            }

            String json = builder.toString();
            jArr = new JSONArray(json);

            urlConnection.disconnect();
            stream.close();
            bufferedReader.close();

        }catch(IOException | JSONException e){
            e.printStackTrace();
        }
        return jArr;
    }

    @Override
    protected void onPostExecute(JSONArray jArr){
        super.onPostExecute(jArr);
    }


}