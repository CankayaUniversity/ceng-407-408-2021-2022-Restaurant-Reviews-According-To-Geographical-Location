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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListingForReviews extends RecyclerView.Adapter<ListingForReviews.ListerforReviewsHolder> {
    private ArrayList<Reviews> ReviewList;
    private Context context;
    private String RestourantName;
    public OnClickFunc1Listener listener;
    public OnFloatListener listener1;

    public ListingForReviews(ArrayList<Reviews> Rest, String a, Context context) {
        this.ReviewList = Rest;
        this.context = context;
        this.RestourantName = a;
    }
    @NonNull
    @Override
    public ListerforReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.review_item,parent,false);
        return new ListerforReviewsHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ListerforReviewsHolder holder, int position) {
        Reviews reviews=ReviewList.get(position);
        holder.setData(reviews);
    }

    public int getItemCount() {
        return ReviewList.size();
    }


    class ListerforReviewsHolder extends RecyclerView.ViewHolder
    {

        TextView ReviewerName,ReviewRate,ReviewSum;
        FloatingActionButton red,green;
        public ListerforReviewsHolder(View CurrentItem1)
        {
            super(CurrentItem1);
            ReviewerName=(TextView) CurrentItem1.findViewById(R.id.ReviewerName);
            ReviewRate=(TextView) CurrentItem1.findViewById(R.id.Reviewrate);
            ReviewSum=(TextView) CurrentItem1.findViewById(R.id.ResRev);
            red=(FloatingActionButton) CurrentItem1.findViewById(R.id.Redbtn);
            green=(FloatingActionButton) CurrentItem1.findViewById(R.id.greenBtn);
            CurrentItem1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position == RecyclerView.NO_POSITION)
                        listener.OnItemClick1(RestourantName);
                }
            });
            red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener1!=null)
                    {
                        listener1.OnItemClick2();
                    }
                }
            });
        }

        public void setData(Reviews reviews)
        {
            this.ReviewerName.setText(reviews.getReviewMaker());
            this.ReviewRate.setText(String.valueOf(reviews.getRate()));
            this.ReviewSum.setText(reviews.getReview().toString());
        }

    }
    public interface OnClickFunc1Listener
    {
        void OnItemClick1(String R_Name);
    }

    public void SetOnItemClickListener(OnClickFunc1Listener listener)
    {
        this.listener=listener;
    }

    public interface OnFloatListener
    {
        void OnItemClick2();
    }

    public void SetOnFloatClickListener(OnFloatListener listener)
    {
        this.listener1=listener;
    }

}
