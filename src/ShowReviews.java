package com.Coskun.eatie;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowReviews extends AppCompatActivity {

    private RecyclerView rV;
    private ListingForReviews lister;
    private FloatingActionButton newrev,showmenu;
    private DocumentReference  documentReference;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reviews);
        rV=(RecyclerView)findViewById(R.id.ReviewList);
        LinearLayoutManager Lm=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Intent intent=getIntent();
        String UserName=intent.getStringExtra("UserNam");
        String RestName=intent.getStringExtra("ResName");
        ShowAll(RestName,this);
        rV.setLayoutManager(Lm);
        rV.setAdapter(lister);
        newrev=findViewById(R.id.AddRev);
        newrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewActiv(UserName,RestName);
            }
        });
        showmenu=findViewById(R.id.ShowMenu);
        showmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show(RestName);
            }
        });
    }
    private void Show(String a)
    {
        Intent c=new Intent(this,ShowImage.class);
        c.putExtra("ResName",a);
        startActivity(c);

    }

    private void NewActiv(String a,String b)
    {
        String ResName=b,UserName=a;
        Intent c=new Intent(this,AddReview.class);
        c.putExtra("UserNam",UserName);
        c.putExtra("ResName",ResName); 
        startActivity(c);
    }
    private void ShowAll(String RestName, Context context){
        firestore=FirebaseFirestore.getInstance();
        ArrayList<Reviews> allReviews=new ArrayList<>();
        CollectionReference collectionReference=firestore.collection("Yorum").document(RestName).collection("Yorumlar");
        Query query = collectionReference;
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document0 : task.getResult())
                {

                        Reviews c=new Reviews(document0.getData().get("UserName").toString(),document0.getData().get("yorum").toString(),
                               document0.getData().get("RestourantName").toString(),Integer.parseInt(document0.getData().get("RestourantRate").toString()),
                                Integer.parseInt(document0.getData().get("YorumP").toString()));
                        allReviews.add(c);
                        if(Integer.parseInt(document0.getData().get("YorumP").toString())==20)
                        {
                            firestore.collection("Yorum").document(RestName).collection("Yorumlar").document(document0.getData().get("yorum").toString()).delete();
                        }
                }
                    lister=new ListingForReviews(allReviews,RestName,context);
                    rV.setHasFixedSize(true);
                    rV.setAdapter(lister);

                    lister.SetOnItemClickListener(new ListingForReviews.OnClickFunc1Listener() {
                        @Override
                        public void OnItemClick1() {
                            //Refr(RestName,context);
                        }
                    });
             }

            }
        });
    }

    private void Refr(String RestName, Context context){
        ShowAll(RestName,context);
    }
}