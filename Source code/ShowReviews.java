package com.Coskun.eatie;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowReviews extends AppCompatActivity {

    private RecyclerView rV;
    private ListingForReviews lister;
    private FloatingActionButton newrev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reviews);
        rV=(RecyclerView)findViewById(R.id.ReviewList);
        LinearLayoutManager Lm=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Intent intent=getIntent();
        String UserName=intent.getStringExtra("UserNam");
        String RestName=intent.getStringExtra("ResName");
        lister=new ListingForReviews(Reviews.GetAllReviewsForRestourant(this,RestName),RestName,this);
        rV.setHasFixedSize(true);
        rV.setLayoutManager(Lm);
        rV.setAdapter(lister);
        newrev=findViewById(R.id.AddRev);
        newrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewActiv(UserName,RestName);
            }
        });
    }

    private void NewActiv(String a,String b)
    {
        String ResName=b,UserName=a;
        Intent c=new Intent(this,AddReview.class);
        c.putExtra("UserNam",UserName);
        c.putExtra("ResName",ResName);
        startActivity(c);
    }
}
