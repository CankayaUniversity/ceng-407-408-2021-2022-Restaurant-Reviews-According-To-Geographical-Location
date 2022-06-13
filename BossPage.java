package com.Coskun.eatie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class BossPage extends AppCompatActivity {
    private static EditText UpdatedSum;
    private static Spinner spinner;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private static Button UpdRes;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private HashMap<String, Object> veri;
    private String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_page);
        Intent intent=getIntent();
        Name=intent.getStringExtra("BossName");
        Const(Name);
    }
    private void Const(String Name) {
        UpdatedSum = findViewById(R.id.UpdatedSum);
        UpdRes = findViewById(R.id.UpdateRes);
        spinner = findViewById(R.id.UpdSpin);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Tagz, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        firestore = FirebaseFirestore.getInstance();
        veri = new HashMap<>();
        UpdREst(Name);
    }
    private void UpdREst(String Name) {
        UpdRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentReference = firestore.collection("User").document(Name);
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {

                                AddRest(document.get("RestName").toString(),document.get("ULat").toString(),document.get("ULng").toString());

                            }
                            else {
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }
    private void AddRest(String RestName,String lat,String Lng) {
        veri.put("RestourantSum", UpdatedSum.getText().toString());
        veri.put("RestourantTag", spinner.getSelectedItem().toString());
        documentReference = firestore.collection("Restourant").document(RestName+lat+Lng);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        firestore.collection("Restourant").document(RestName+lat+Lng)
                                .update(veri);
                    }
                    else {
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

    }
}