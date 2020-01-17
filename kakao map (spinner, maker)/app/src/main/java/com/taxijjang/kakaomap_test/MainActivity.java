package com.taxijjang.kakaomap_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
    JSONArray json;
    Json_Si_Task jTask ;

    ArrayList<String> arrayList, arrayList2,arrayList3;
    ArrayAdapter<String> arrayAdapter, arrayAdapter2, arrayAdapter3;

    Spinner sp1,sp2,sp3;

    JSONObject si, gun, gu;

    JSONObject gu_x, gu_y;

    //x, y를 위도 경도로 변환 하기 위함
    public static int TO_GRID = 0;
    public static int TO_GPS = 1;

    //선택한 x,y 좌표를 위경도로 변환하여 저장할 변수
    LatXLngY select_locate;

    Data his_Data;

    RecyclerView recyclerView;

    ArrayList<Data> history_array = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MapView mapView =new MapView(this);
        mapView.setDaumMapApiKey("1331a2760f8a298542ad79b604582e8d");
        //mapViewContainer = (ViewGroup) findViewById(R.id.map_view);

        final ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        Button btn = findViewById(R.id.parsebutton);
        sp1 = findViewById(R.id.spinner);
        sp2 = findViewById(R.id.spinner2);
        sp3 = findViewById(R.id.spinner3);

        si = new JSONObject();
        gun = new JSONObject();
        gu = new JSONObject();

        gu_x = new JSONObject();
        gu_y = new JSONObject();

        final String url = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt";

        his_Data = new Data();
        Button his_btn = findViewById(R.id.his_button);

        try {
            jTask = new Json_Si_Task();
            json = jTask.execute(url).get();

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

        //히스토리 액티비티로 전환하기 위한 버튼
        his_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),History.class);

                Log.v("히스토리 어레이에 담겼느냐~ " , "size : " + history_array.size());
                Bundle bundle = new Bundle();
                bundle.putSerializable("adress",history_array);
                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(select_locate.lat, select_locate.lng);
                mapView.setMapCenterPoint(mapPoint, true);
                //true면 앱 실행 시 애니메이션 효과가 나오고 false면 애니메이션이 나오지않음.
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName("D_F");
                marker.setTag(0);
                marker.setMapPoint(mapPoint);
                // 기본으로 제공하는 BluePin 마커 모양.
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(marker);

                Toast.makeText(MainActivity.this, his_Data.si + "  " + his_Data.gun + " " + his_Data.gu + "\n"
                        + "위도 : " + his_Data.latx + " 경도 : " + his_Data.laty ,Toast.LENGTH_LONG ).show();

                history_array.add(his_Data);
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
                    JSON_Gun_Task jTask2 = new JSON_Gun_Task();
                    Gun_json = jTask2.execute("http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl."+ si.getString(city)+".json.txt").get();
                    his_Data.si = city;
                    arrayList2 = new ArrayList<>();

                    for(int i= 0 ; i<Gun_json.length(); i++){
                        try {
                            JSONObject jsonObject = Gun_json.getJSONObject(i);
                            System.out.println(jsonObject.getString("code"));
                            arrayList2.add(jsonObject.getString("value"));

                            gun.put(jsonObject.getString("value"),jsonObject.getString("code"));

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }

                    arrayAdapter2 = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            arrayList2);

                    sp2.setAdapter(arrayAdapter2);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            JSONArray Gu_json;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //spinner에서 눌려진 군의 코드 가지고 오기
                String city = parent.getItemAtPosition(position).toString();
                try{
                    JSON_Gu_Task jTask3 = new JSON_Gu_Task();
                    Gu_json = jTask3.execute("http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf."+gun.getString(city)+".json.txt").get();
                    his_Data.gun = city;
                    sp3 = findViewById(R.id.spinner3);

                    arrayList3 = new ArrayList<>();

                    for(int i =0 ; i <Gu_json.length(); i++){
                        try{
                            JSONObject jsonObject = Gu_json.getJSONObject(i);
                            arrayList3.add(jsonObject.getString("value"));

                            gu.put(jsonObject.getString("value"),jsonObject.getString("code"));
                            gu_x.put(jsonObject.getString("value"), jsonObject.getString("x"));
                            gu_y.put(jsonObject.getString("value"), jsonObject.getString("y"));

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    arrayAdapter3 = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item,
                            arrayList3);

                    sp3.setAdapter(arrayAdapter3);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //spinner3 에서 눌려진 구의 x, y 좌표 가지고 오기
                String city = parent.getItemAtPosition(position).toString();

                try{
                    select_locate = convertGRID_GPS(TO_GPS, gu_x.getDouble(city), gu_y.getDouble(city));
                    //Toast.makeText(MainActivity.this, "x : " +  gu_x.getString(city) + " y : " + gu_y.getString(city) +
                            //"Loc X : " + (select_locate.lat) + " Loc Y : " + (select_locate.lng) ,Toast.LENGTH_LONG ).show();
                    his_Data.gu = city;
                    his_Data.latx = select_locate.lat;
                    his_Data.laty = select_locate.lng;
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public class Json_Si_Task extends AsyncTask<String, Void, JSONArray>{
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
    //x, y 좌표 위도 경도변환
    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y ){
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO =  43;// 기준점 X좌표(GRID)
        double YO = 136 ; // 기1준점 Y좌표(GRID)

        //
        // LCC DFS 좌표변환 ( code : "TO_GRID"(위경도->좌표, lat_X:위도,  lng_Y:경도), "TO_GPS"(좌표->위경도,  lat_X:x, lng_Y:y) )
        //


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.x = lat_X - 1;
            rs.y = lng_Y - 1;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            System.out.println(">>>> alon 1 : " + alon);

            System.out.println(">>>> RADDEG 1 : " + RADDEG);
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;


        }
        System.out.println("++++++++++" + rs.lat + " " + rs.lng);
        return rs;
    }


}