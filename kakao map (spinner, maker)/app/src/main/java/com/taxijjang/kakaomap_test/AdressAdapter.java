package com.taxijjang.kakaomap_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdressAdapter extends RecyclerView.Adapter<AdressAdapter.ViewHolder>  {
    ArrayList<Data> adresss = new ArrayList<Data>();

    @NonNull
    @Override
    //onCreateViewHolder 과 onBindViewHolder는 뷰 홀더 객체가 만들어질 때와 재사용될 때 자동으로 호출 된다.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View adressView = inflater.inflate(R.layout.address_list,viewGroup,false);
        return new ViewHolder(adressView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Data adress = adresss.get(position);
        viewHolder.setItem(adress);
    }

    public void addAdress(Data adress){
        adresss.add(adress);
    }

    public void setAdresss(ArrayList<Data> adresss) {
        this.adresss = adresss;
    }

    public Data getAdress(int position){
        return adresss.get(position);
    }
    public void setAdress(int position, Data adress){
        adresss.set(position, adress);
    }

    @Override
    public int getItemCount() {
        return adresss.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;

        public ViewHolder(View adressView){
            super(adressView);

            textView = adressView.findViewById(R.id.textView);
            textView2 = adressView.findViewById(R.id.textView2);
        }

        public void setItem(Data adress){
            textView.setText(adress.getSi());
            textView2.setText(adress.getGun());
        }
    }
}
