package com.taxijjang.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    int value;
    BackgroundTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                task = new BackgroundTask();
                task.execute();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                task.cancel(true);
            }
        });
    }

    class BackgroundTask extends AsyncTask<Void,Integer,Boolean>{
        @Override
        protected void onPreExecute(){
            value = 0;
            textView.setText("value의 값 : " + value);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void ... values){
            for(int i =0 ; i <10000; i ++){
                publishProgress(i);

                try{
                    Thread.sleep(1000);
                }catch (InterruptedException ex){}
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean  s){
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer ... values){
            textView.setText(values[0].toString());
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Boolean s) {
            super.onCancelled(s);
        }
    }
}