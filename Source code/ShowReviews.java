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
    private FloatingActionButton newrev;
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
        Query query = collectionReference.whereEqualTo("RestourantName", RestName);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult())
                {

                        Reviews c=new Reviews(document.getData().get("UserName").toString(),document.getData().get("yorum").toString(),
                               document.getData().get("RestourantName").toString(),Integer.parseInt(document.getData().get("Rate").toString()),
                                Integer.parseInt(document.getData().get("YorumP").toString()));
                        allReviews.add(c);
                }
                    lister=new ListingForReviews(allReviews,RestName,context);
                    rV.setHasFixedSize(true);
                    rV.setAdapter(lister);

                    lister.SetOnItemClickListener(new ListingForReviews.OnClickFunc1Listener() {
                        @Override
                        public void OnItemClick1() {
                            Refr();
                        }
                    });
             }

            }
        });
    }
    private void Refr(){

    }
}
