package com.Coskun.eatie;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class LoggedMp extends AppCompatActivity {
    private RecyclerView rV;
    private Listing lister;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private NavigationView nv;
    private static final int Request_Code=101;
    private DrawerLayout dv;
    private String Tagzz,UserNm;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
         Intent intent=getIntent();
        UserNm=intent.getStringExtra("UserNam");
        changeProf();
        Tanimlar(UserNm);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.Hmbrgr:
                      SetTagzz("Hamburger");
                        reshow("Hamburger",UserNm,getContext());
                       return true;
                    case R.id.Pizza:
                        SetTagzz("Pizza");
                        reshow("Pizza",UserNm,getContext());
                        return true;
                    case R.id.Tst:
                        reshow("Tost/Sandwich",UserNm,getContext());
                        return true;
                    case R.id.Kahvaltı:
                        reshow("Kahvaltı",UserNm,getContext());
                        return true;
                    case R.id.OpenMap:
                      //  openMap();
                        return true;
                    case R.id.Profile:
                        SendMessage("yolo");
                        settings(UserNm);
                        return true;
                    case R.id.RestoranSh:
                        test(UserNm);
                default:return false;

                }
            }
        });

    }


    private void SendMessage(String c)
    {
        Toast.makeText(this,c,Toast.LENGTH_SHORT).show();
    }
    public void a(String a, String b)
    {

        Intent c=new Intent(this,ShowReviews.class);
        c.putExtra("UserNam", b);
        c.putExtra("ResName", a);
        startActivity(c);
    }
    private void changeProf()
    {
        storageReference= FirebaseStorage.getInstance().getReference("images/us.png");

           try
           {
               File localfile = File.createTempFile("tempfile",".png");
               storageReference.getFile(localfile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                       Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                       ImageView c=nv.findViewById(R.id.UseP);
                       c.setImageBitmap(bitmap);
                   }
               });
           }catch (Exception e)
           {}


    }
    private void SetTagzz(String q) {Tagzz=q;}
    private String getTagzz()   {return Tagzz; }
    private Context getContext()
    {
        return this;
    }
    public void Tanimlar(String q)
    {
        setContentView(R.layout.activity_logged_mp);
        rV=(RecyclerView)findViewById(R.id.rcview);
       all(q,UserNm,this);
        nv=(NavigationView)findViewById(R.id.navView);
        nv.setItemIconTintList(null);
        dv=(DrawerLayout)findViewById(R.id.ShowRest);
        Toolbar tb=(Toolbar)findViewById(R.id.toolb);
        setSupportActionBar(tb);
        ActionBarDrawerToggle abt=new ActionBarDrawerToggle(this,dv,tb,R.string.open,R.string.close);
        dv.addDrawerListener(abt);
        View headView=nv.getHeaderView(0);
        TextView tw=(TextView) headView.findViewById(R.id.HeaderId);
        EditText searc=(EditText) headView.findViewById(R.id.Barcodetextview);
        tw.setText(q);
        TextView.OnEditorActionListener exampleListener = new TextView.OnEditorActionListener(){
            public boolean onEditorAction(TextView searc, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String tes=searc.getText().toString();
                    research(tes,q);//match this behavior to your 'Send' (or Confirm) button
                }
                return true;
            }
        };
        searc.setOnEditorActionListener(exampleListener);
        abt.syncState();
    }

    public void onBackPressed()
    {
        if(dv.isDrawerOpen(GravityCompat.START))
            dv.closeDrawer(GravityCompat.START);
        else
        {
            this.finish();
        }
    }
    private void reshow(String tag, String UserNM, Context context)
    {
        HashMap<String, Object> veri=new HashMap<>();
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        CollectionReference collectionReference=firestore.collection("Restourant");
        ArrayList<Restourant> allrestourants=new ArrayList<>();
        Query query = collectionReference.whereEqualTo("RestourantTag", tag);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Restourant lel=new Restourant(document.getData().get("RestourantName").toString(),(int)Double.parseDouble(document.getData().get("RestourantRate").toString()) ,
                                document.getData().get("RestourantSum").toString(),   document.getData().get("RestourantTag").toString());
                        allrestourants.add(lel);
                    }
                for(Restourant c:allrestourants)
                {SendMessage(c.getRestourantName().toString());
                lister =new Listing(allrestourants,UserNM,context);
                    rV.setAdapter(lister);
                    lister.setOnItemClickListener(new Listing.OnItemClickListener() {
                        @Override
                        public void OnItemClick(String R_Nam) {
                            a(R_Nam,UserNM);
                        }
                    });
                }
                }

            }
        });
    }
  private ArrayList<Restourant> all(String q,String Un,Context context)
    {
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        ArrayList<Restourant> allrestourants=new ArrayList<>();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_Code);
            return null;
        }
        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setInterval(6000000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000000);
        LocationCallback locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(locationResult==null)
                {SendMessage("null value for location Result");
                    return;}
                Location location=locationResult.getLastLocation();
                if(location!=null)
                {
                    firestore.collection("Restourant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                if (task.isSuccessful()) {
                                    if(testLocation(document.getData().get("RestourantLtd").toString(),document.getData().get("RestourantLNG").toString(),location.getLatitude(),location.getLongitude()))
                                    {
                                        Restourant c=new Restourant(document.getData().get("RestourantName").toString(),Double.parseDouble(document.getData().get("RestourantRate").toString())
                                                ,document.getData().get("RestourantSum").toString(),document.getData().get("RestourantTag").toString());
                                        allrestourants.add(c);
                                    }
                                }
                            }
                            LinearLayoutManager Lm=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                            lister=new Listing(allrestourants,q,context);
                            rV.setHasFixedSize(true);
                            rV.setLayoutManager(Lm);
                            rV.setAdapter(lister);
                            lister.setOnItemClickListener(new Listing.OnItemClickListener() {
                                @Override
                                public void OnItemClick(String R_Nam) {
                                    a(R_Nam,Un);
                                }
                            });
                        }

                    });
                }
            }

        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        return allrestourants;

    }
    private boolean testLocation(String RestLAt,String Restlng,Double UserLat,Double UserLng)
    {
        Double Rlat=Double.parseDouble(RestLAt),RLng=Double.parseDouble(Restlng);
        SendMessage("("+(39.8199705+0.005)+">"+Rlat+")"+"("+(39.8199705-0.005)+"<"+Rlat+")"+"("+(32.563078+0.005)+">"+RLng+")"+"("+(32.563078-0.005)+"<"+RLng+")");
        if((39.8199705+0.005>=Rlat)&&(39.8199705-0.005<=Rlat)&&(32.563078+0.005>=RLng)&&(32.563078-0.005<=RLng))

            return true;
        else
            return false;
    }
    private void test(String Un)
         {
             FirebaseFirestore firestore =FirebaseFirestore.getInstance();
             if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                 && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
         {
             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_Code);
             return;
         }
             LocationRequest locationRequest=LocationRequest.create();
             locationRequest.setInterval(6000000);
             locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
             locationRequest.setFastestInterval(5000000);
             LocationCallback locationCallback=new LocationCallback() {
                 @Override
                 public void onLocationResult(@NonNull LocationResult locationResult) {
                     if(locationResult==null)
                     {SendMessage("null value for location Result");
                         return;}
                        Location location=locationResult.getLastLocation();
                     if(location!=null)
                         {
                             firestore.collection("User").document(Un).update("ULat",String.valueOf(location.getLatitude()));
                             firestore.collection("User").document(Un).update("ULng",String.valueOf(location.getLongitude()));
                             firestore.collection("User").document(Un).update("Utag","Pending");

                         }
                     }

             };

             fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
          }
    private void research(String ResName,String Username)
    {
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference;
        ArrayList<Restourant> allrestourants=new ArrayList<>();
        Restourant t=null;
        firestore.collection("Restourant").whereArrayContains("RestourantName",ResName).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Restourant lel=new Restourant(document.getData().get("RestourantName").toString(),(int)Double.parseDouble(document.getData().get("RestourantRate").toString()) ,
                                document.getData().get("RestourantSum").toString(),   document.getData().get("RestourantTag").toString());
                        allrestourants.add(lel);
                    }
                    for(Restourant c:allrestourants)
                    {SendMessage(c.getRestourantName().toString());
                        lister =new Listing(allrestourants,Username,getContext());
                        rV.setAdapter(lister);
                        lister.setOnItemClickListener(new Listing.OnItemClickListener() {
                            @Override
                            public void OnItemClick(String R_Nam) {
                                a(R_Nam,Username);
                            }
                        });
                    }
                }
                else {

                }
            }
        });

    }
    private void settings(String Usermail)

    {            Intent c=new Intent(this, SettingsPage.class);
            c.putExtra("UserNam",Usermail);
            startActivity(c);

        }
}