package com.Coskun.eatie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class LoggedMp extends AppCompatActivity {
    private RecyclerView rV;
    private Listing lister;
    private NavigationView nv;
    private DrawerLayout dv;
    private String Tagzz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         Intent intent=getIntent();
        String UserNm=intent.getStringExtra("UserNam");

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
                        //reshow(UserNm);
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
                        Sendrq(UserNm);//to be implemented By 
                default:return false;

                }
            }
        });
        lister.setOnItemClickListener(new Listing.OnItemClickListener() {
            @Override
            public void OnItemClick(String R_Nam) {
                a(R_Nam,UserNm);
            }
        });
    }
    private void SendMessage(String c)
    {
        Toast.makeText(this,c,Toast.LENGTH_SHORT).show();
    }
    
    private void Sendrq(String Un){
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("User").document(Un).update("Utag","Pending");
    }
    
    public void a(String a, String b)
    {

        Intent c=new Intent(this,ShowReviews.class);
        c.putExtra("UserNam", b);
        c.putExtra("ResName", a);
        startActivity(c);
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
        LinearLayoutManager Lm=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        lister=new Listing(all(),q,this);
        rV.setHasFixedSize(true);
        rV.setLayoutManager(Lm);
        rV.setAdapter(lister);
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
                        Restourant lel=new Restourant(document.getData().get("RestourantName").toString(),(double)Integer.parseInt(document.getData().get("RestourantRate").toString()) ,
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
  private ArrayList<Restourant> all()
    {
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference;
        ArrayList<Restourant> allrestourants=new ArrayList<>();
        Restourant t=null;
        firestore.collection("Restourant").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult())
                {
                    if (task.isSuccessful()) {

                        Restourant c=new Restourant(document.getData().get("RestourantName").toString(),Integer.parseInt(document.getData().get("RestourantRate").toString())
                                ,document.getData().get("RestourantSum").toString(),document.getData().get("RestourantTag").toString());
                        allrestourants.add(c);
                    }
                }
            }

        });
        return allrestourants;
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
                        Restourant lel=new Restourant(document.getData().get("RestourantName").toString(),(double)Integer.parseInt(document.getData().get("RestourantRate").toString()) ,
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


       /* lister=new Listing(Restourant.getsearchedName(this,q),name,this);
        rV.setAdapter(lister);
        lister.setOnItemClickListener(new Listing.OnItemClickListener() {
            @Override
            public void OnItemClick(String R_Nam) {
                a(R_Nam,q);
            }
        });*/
    }
    private void openMap()
    {
            Intent c=new Intent(this, MapsActivity.class);
            startActivity(c);
    }
    private void settings(String Usermail)

    {


            Intent c=new Intent(this, SettingsPage.class);
            c.putExtra("UserNam",Usermail);
            startActivity(c);

        }
}
