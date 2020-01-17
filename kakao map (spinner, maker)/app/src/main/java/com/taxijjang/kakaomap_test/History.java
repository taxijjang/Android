package com.taxijjang.kakaomap_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class History extends Activity {
    ArrayList<Data> his_arr;
    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_history);
        his_arr =(ArrayList<Data>)getIntent().getSerializableExtra("adress");
        //Toast.makeText(getApplicationContext(),his_arr.size(),Toast.LENGTH_LONG).show();
        //Log.v("Test", "tel  :  "+his_arr.size());

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);

        //여기서부터 안댐
        recyclerView.setLayoutManager(layoutManager);
        /*AdressAdapter adapter = new AdressAdapter();

        adapter.addAdress(his_arr.get(0));
        recyclerView.setAdapter(adapter);*/
    }
}
