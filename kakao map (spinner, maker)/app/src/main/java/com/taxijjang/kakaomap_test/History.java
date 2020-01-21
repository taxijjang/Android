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
    ArrayList<Data> new_arr ;
    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        new_arr =(ArrayList<Data>)getIntent().getSerializableExtra("adress");
        //Toast.makeText(getApplicationContext(),his_arr.size(),Toast.LENGTH_LONG).show();
        //Log.v("Test", "tel  :  "+his_arr.size());

        for(Data d : new_arr){
            his_arr.add(d);
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //recyclerView.setHasFixedSize(true);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);


        //여기서부터 안댐
        //recyclerView.setLayoutManager(layoutManager);

        for(Data d : his_arr){
            System.out.println("his 값 출력 " + d.si + " " + d.gun + " " + d.gu + " " + new_arr.size());
        }
        /*AdressAdapter adapter = new AdressAdapter();

        adapter.addAdress(his_arr.get(0));
        recyclerView.setAdapter(adapter);*/
    }
    @Override

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("adress",his_arr);
    }

}
