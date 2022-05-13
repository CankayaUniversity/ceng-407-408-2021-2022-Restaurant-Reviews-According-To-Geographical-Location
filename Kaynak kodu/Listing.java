package com.Coskun.eatie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Listing extends RecyclerView.Adapter<Listing.ListerHolder> {
   private ArrayList<Restourant> RestourantList;
    private Context context;
    private String user;
    private OnItemClickListener listener;
    public Listing(ArrayList<Restourant> Rest,String a,Context context)
    {
       this.RestourantList=Rest;
       this.context=context;
       this.user=a;
   }
    @NonNull
    @Override
    public ListerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.restourant_item,parent,false);
        return new ListerHolder(v);

    }

    public void onBindViewHolder(@NonNull ListerHolder holder, int position) {
        Restourant restourant=RestourantList.get(position);
        holder.setData(restourant);
    }
    @Override
    public int getItemCount() {
        return RestourantList.size();
    }

    class ListerHolder extends RecyclerView.ViewHolder
    {

        TextView RestourantName,RestourantRate,RestourantSum;
         public ListerHolder(View CurrentItem)
        {
            super(CurrentItem);
           RestourantName=(TextView) CurrentItem.findViewById(R.id.ResName);
           RestourantRate=(TextView) CurrentItem.findViewById(R.id.Resrate);
           RestourantSum=(TextView) CurrentItem.findViewById(R.id.Ressum);
            CurrentItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION)
                        listener.OnItemClick(RestourantName.getText().toString());
                }
            });
        }


        public void setData(Restourant restourant)
        {
            this.RestourantName.setText(restourant.getRestourantName());
            this.RestourantRate.setText(restourant.getRestourantRate().toString());
            this.RestourantSum.setText(restourant.getRestourantSummary());
        }

    }
    public interface OnItemClickListener
    {
        void OnItemClick(String R_Name);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener=listener;
    }
}
