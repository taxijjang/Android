package com.taxijjang.kakaomap_test;

import androidx.appcompat.app.AppCompatActivity;
import net.daum.mf.map.api.MapView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    JSONArray json;
    Json_Si_Task jTask ;

    ArrayList<String> arrayList, arrayList2,arrayList3;
    ArrayAdapter<String> arrayAdapter, arrayAdapter2, arrayAdapter3;

    Spinner sp1,sp2,sp3;

    JSONObject si, gun, gu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("1331a2760f8a298542ad79b604582e8d");
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);
        container.addView(mapView);

        Button btn = findViewById(R.id.parsebutton);
        sp1 = findViewById(R.id.spinner);
        si = new JSONObject();
        final String url = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";

        try {
            jTask = new Json_Si_Task();
            json = jTask.execute(url).get();
            System.out.print("제이스스스스스스스ㅡ슨 " +json + "**");
            System.out.println("끝");

            sp1 = findViewById(R.id.spinner);
            arrayList = new ArrayList<>();

            System.out.println(json.length());


            for(int i= 0 ; i<json.length(); i++){
                try {
                    JSONObject jsonObject = json.getJSONObject(i);
                    arrayList.add(jsonObject.getString("value"));

                    si.put(jsonObject.getString("value"),jsonObject.getString("code"));

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    arrayList);

            sp1.setAdapter(arrayAdapter);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Json 조회",Toast.LENGTH_SHORT).show();

            }
        });

        //시 spinner 항목 눌렸을때 이벤트 확인
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            JSONArray Gun_json ;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //spinner에서 눌려진 시의 코드 가지고 오기
                String city = parent.getItemAtPosition(position).toString();
                try {
                    //눌려진 시의 코드를 확인하기 위해 toast 메세지로 확인
                    Toast.makeText(MainActivity.this, si.getString(city), Toast.LENGTH_LONG).show();
                    Gun_json = new JSON_Gun_Task().execute("http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl."+ si.getString(city)+".json.txt").get();

                    sp2 = findViewById(R.id.spinner2);
                    arrayList2 = new ArrayList<>();

                    System.out.println(Gun_json.length());


                    for(int i= 0 ; i<Gun_json.length(); i++){
                        try {
                            JSONObject jsonObject = Gun_json.getJSONObject(i);
                            arrayList2.add(jsonObject.getString("value"));

                            gun.put(jsonObject.getString("value"),jsonObject.getString("code"));

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                    arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            arrayList2);

                    sp2.setAdapter(arrayAdapter);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private class JSON_Gun_Task extends AsyncTask<String, Void, JSONArray>{
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
    private class Json_Si_Task extends AsyncTask<String, Void, JSONArray>{
        JSONArray jArr;

        @Override
        protected JSONArray doInBackground(String ... strUrl){
            try {
                URL url = new URL(strUrl[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuffer builder = new StringBuffer();

                String inputString = null;
                while((inputString = bufferedReader.readLine()) != null){
                    builder.append(inputString);
                }
                String json = builder.toString();
                jArr = new JSONArray(json);

                System.out.println("제이슨 어레이 됫나연" + jArr);
                System.out.print("json 일부값 추출 " + jArr);

                urlConnection.disconnect();
                bufferedReader.close();
                stream.close();

            }catch(IOException | JSONException e){
                e.printStackTrace();
            }
            return jArr;
        }

        @Override
        protected void onPostExecute(JSONArray result){
            super.onPostExecute(result);
        }
    }
}