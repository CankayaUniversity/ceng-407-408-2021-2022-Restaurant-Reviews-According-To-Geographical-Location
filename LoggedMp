package com.Coskun.eatie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class LoggedMp extends AppCompatActivity {
    private RecyclerView rV;
    private Listing lister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_mp);
        rV=(RecyclerView)findViewById(R.id.rcview);
        LinearLayoutManager Lm=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Intent intent=getIntent();
        String q=intent.getStringExtra("UserNam");
        lister=new Listing(Restourant.getData(this),q,this);
        rV.setHasFixedSize(true);
        rV.setLayoutManager(Lm);    
        rV.setAdapter(lister);
        lister.setOnItemClickListener(new Listing.OnItemClickListener() {
            @Override
            public void OnItemClick(String R_Nam) {
                a(R_Nam,q);
            }
        });
    }
    public void a(String a, String b)
    {

        String ResName=a,UserName=b;
        Intent c=new Intent(this,ShowReviews.class);
        c.putExtra("UserNam",UserName);
        c.putExtra("ResName",ResName);
        startActivity(c);
    }

}
