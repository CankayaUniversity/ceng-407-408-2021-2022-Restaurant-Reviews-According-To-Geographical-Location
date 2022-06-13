package com.Coskun.eatie;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminPage extends AppCompatActivity {
    private static EditText delet, RN, Rs,RestNm;
    private static Spinner spinner,spitter;
    private ArrayAdapter<CharSequence> arrayAdapter,pendingLis;
    private static Button DelUser, DelRev, DelRest, AddRes,SetB;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private HashMap<String, Object> veri;
    private String Tag = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Const();
    }

    private void Const() {
        delet = findViewById(R.id.Deleted);
        RN = findViewById(R.id.RestourantName);
        Rs = findViewById(R.id.RestourantSummary);
        RestNm=findViewById(R.id.BossRest);
        DelUser = findViewById(R.id.DelUser);
        DelRev = findViewById(R.id.DelRev);
        DelRest = findViewById(R.id.DelRest);
        AddRes = findViewById(R.id.AddRest);
        SetB=findViewById(R.id.AcceptRequest);
        spinner = findViewById(R.id.Spnner);
        spitter=findViewById(R.id.Reqt);
        test(this);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Tagz, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        firestore = FirebaseFirestore.getInstance();
        veri = new HashMap<>();
        Btns();
    }

    private void Btns() {
        DeleteUser();
        DeleteReview();
        DeleteRestourant();
        AddRestourant(this);
        ChangeTag();
    }
    private void ChangeTag()
    {
        SetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spitter.getSelectedItem()!=null)
                {String UserName=spitter.getSelectedItem().toString();
                    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                    firestore.collection("User").document(UserName).update("Utag","Boss");
                    firestore.collection("User").document(UserName).update("RestName",RestNm.getText().toString());
                    documentReference = firestore.collection("User").document(UserName);
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                 AddRest(RestNm.getText().toString(),"","", document.get("ULat").toString(), document.get("ULng").toString());
                                }

                            } else {
                                Log.d("TAG", "get failed with ", task.getException());
                            }
                        }
                    });
                }
            }
        });
    }

    private void SendMessage(String c) {
        Toast.makeText(this, c, Toast.LENGTH_SHORT).show();
    }

    private void GetDeletedStr(String cnt) {
        String a = delet.getText().toString();
        if (cnt == "0"){ firestore.collection("User").document(a)
                .delete();}
        else if (cnt == "1"){}
        else if (cnt == "2") {firestore.collection("Restourant").document(a)
                .delete(); }
    }
private void test(Context context)
{
  firestore=FirebaseFirestore.getInstance();
    CollectionReference collectionReference=firestore.collection("User");
    Query query = collectionReference.whereEqualTo("Utag", "Pending");
    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
    {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task)
        {
            if (task.isSuccessful())
            {
                ArrayList<String> pendList=new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult())
                {
                    pendList.add(document.getData().get("UserMail").toString());
                }
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,pendList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
              spitter.setAdapter(arrayAdapter);
            }
        }
    });
}
    private void DeleteUser() {
        DelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDeletedStr("0");
            }
        });
    }

    private void DeleteReview() {
        DelRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDeletedStr("1");
            }
        });
    }

    private void DeleteRestourant() {
        DelRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDeletedStr("2");
            }
        });
    }

    private void AddRestourant(Context context) {
        AddRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                documentReference = firestore.collection("User").document(spinner.getSelectedItem().toString());
                AddRest( RN.getText().toString(),Rs.getText().toString(),spinner.getSelectedItem().toString(),"-","-");
            }
        });
    }

    private void AddRest(String Name,String Sum,String tag,String Ltd,String lNg) {
        veri.put("RestourantName", Name);
        veri.put("RestourantSum", Sum);
        veri.put("RestourantRate", "0");
        veri.put("RestourantTag", tag);
        veri.put("RestourantLtd", Ltd);
        veri.put("RestourantLNG", lNg);
        veri.put("Size","0");
          documentReference = firestore.collection("Restourant").document(Name+Ltd+lNg);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        SendMessage("Yay");
                    }
                    else {
                     firestore.collection("Restourant").document(Name+Ltd+lNg)
                    .set(veri);

                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

    }
}