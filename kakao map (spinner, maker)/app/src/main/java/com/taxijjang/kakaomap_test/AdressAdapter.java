package com.taxijjang.kakaomap_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdressAdapter extends RecyclerView.Adapter<AdressAdapter.AdressViewHolder>  {
    @NonNull
    @Override


    //onCreateViewHolder 과 onBindViewHolder는 뷰 홀더 객체가 만들어질 때와 재사용될 때 자동으로 호출 된다.
    public AdressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list, parent ,false);
        AdressViewHolder adress_h= new AdressViewHolder(inflate);
        return adress_h;
    }

    @Override
    public void onBindViewHolder(@NonNull AdressViewHolder viewHolder, int position) {
        viewHolder.textView.setText(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class AdressViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public AdressViewHolder(@NonNull View adressView){
            super(adressView);

            this.textView = adressView.findViewById(R.id.textView);
        }
    }
}
