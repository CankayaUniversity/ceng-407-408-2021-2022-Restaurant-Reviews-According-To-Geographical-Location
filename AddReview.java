package com.Coskun.eatie;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class AddReview extends AppCompatActivity {
static private EditText Rev;
static private Button Sendreview;
    private static final int Request_Code=101;
static private RadioGroup Select;
static private RadioButton frst,lst;
    private String Restname,UserName;
    private FusedLocationProviderClient fusedLocationProviderClient;
private DocumentReference documentReference;
private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        Intent intent=getIntent();
        Restname=intent.getStringExtra("ResName");
        UserName=intent.getStringExtra("UserNam");
        Cont();
    }
    private void Cont()
    {
        Rev=findViewById(R.id.review);
        Sendreview=findViewById(R.id.SendReview);
        Select=findViewById(R.id.RateGroup);
        frst=findViewById(R.id.one);
        lst=findViewById(R.id.five);
        Clickerscall();
    }
    private void Clickerscall()
    {
        SendClicker(this);
    }
    private void SendMessage(String c)
    {
        Toast.makeText(this,c,Toast.LENGTH_SHORT).show();
    }

    private void SendClicker(Context context)
    {
        Sendreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                HashMap<String, Object> veri=new HashMap<>();

                veri.put("UserName",UserName);
                veri.put("RestourantName",Restname);
                veri.put("RestourantRate",getRate());
                veri.put("yorum",Rev.getText().toString());
                veri.put("YorumP",String.valueOf(0));
                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(AddReview.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_Code);
                    return;
                }
                LocationRequest locationRequest=LocationRequest.create();
                locationRequest.setInterval(60000000);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setFastestInterval(50000000);
                LocationCallback locationCallback=new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        if(locationResult==null)
                        {SendMessage("null value for location Result");
                            return;}

                         Location location=locationResult.getLastLocation();
                            if(location!=null)
                            {
                                CollectionReference collectionReference;
                                collectionReference=firestore.collection("Restourant");
                                Query query=collectionReference.whereEqualTo("RestourantName",Restname);
                                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful())
                                        {

                                            for (QueryDocumentSnapshot document : task.getResult())
                                            {
                                                if(testLocation(String.valueOf(document.getData().get("RestourantLtd").toString()),String.valueOf(document.getData().get("RestourantLNG").toString()),
                                                        39.8199705,32.563078))
                                                {

                                                    firestore.collection("Yorum").document(Restname)
                                                            .collection("Yorumlar").document(Rev.getText().toString()).set(veri);

                                                    SendMessage("YepBOi");
                                                }
                                                else{SendMessage("elsede");}
                                            }

                                        }

                                    }
                                   });
                                collectionReference=firestore.collection("Restourant");
                                query=collectionReference.whereEqualTo("RestourantName",Restname);
                                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful())
                                        {

                                            for (QueryDocumentSnapshot document : task.getResult())
                                            { if(document.getData().get("RestourantName").toString().equals(Restname))
                                               { Double x=Double.parseDouble(document.get("RestourantRate").toString());
                                                   if(document.get("Size").toString()=="0")
                                                   {
                                                       x=x+(double)getRate();
                                                   }
                                                   else
                                                   {
                                                       x=x*Integer.parseInt(document.get("Size").toString())+getRate();
                                                   }
                                                   x=(double) x/(Integer.parseInt(document.get("Size").toString())+1);

                                                   firestore.collection("Restourant").document(Restname+String.valueOf(document.getData().get("RestourantLtd"))+String.valueOf(document.getData().get("RestourantLNG"))).update("RestourantRate",String.valueOf(x));

                                                   firestore.collection("Restourant").document(Restname+String.valueOf(document.getData().get("RestourantLtd"))+String.valueOf(document.getData().get("RestourantLNG"))).update("Size",Integer.valueOf(String.valueOf(document.getData().get("Size").toString()+1)));
                                                   break;
                                               }
                                            }

                                        }

                                    }
                                });
                            }


                    }
                };
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
            //    Reload();
            }
        });
    }
    private void Setsize (String name,String lat,String lng,int x)
    {
        HashMap<String, Object> veri=new HashMap<>();
        veri.put("Size",x);
        firestore.collection("Yorum").document(name+lat+lng).set(veri);
    }
    private boolean testLocation(String RestLAt,String Restlng,Double UserLat,Double UserLng)
    {
        Double Rlat=Double.parseDouble(RestLAt),RLng=Double.parseDouble(Restlng);
         if((UserLat+0.003>=Rlat)&&(UserLat-0.003<=Rlat)&&(UserLng+0.003>=RLng)&&(UserLng-0.003<=RLng))

            return true;
        else
            return false;
    }

    private int getRate()
    {
        int radioid=Select.getCheckedRadioButtonId();
        return getWhich(radioid);
    }
    private int getWhich(int radioid)
    {

        switch (radioid)
        {
            case R.id.one:
                return 1;
            case R.id.two:
                return 2;
            case R.id.tree:
                return 3;
            case R.id.four:
                return 4;
            case R.id.five:
                return 5;
            default:
                return 0;
        }
    }

    private void Reload()
    {

        finish();
    }
}