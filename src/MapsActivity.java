package com.Coskun.eatie;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.Coskun.eatie.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ArrayList<HashMap<String,Object>> all;
    private static final int Request_Code=101;
    private double lattidue,longtidu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        StringBuilder stringBuilder=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json");
        stringBuilder.append("?location="+lattidue+","+longtidu);
        stringBuilder.append("&radius=1000");
        stringBuilder.append("&type=restaurant");
        stringBuilder.append("&key="+getResources().getString(R.string.google_maps_key));

        String url=stringBuilder.toString();
        Object dataFetch[]=new Object[2];
        dataFetch[0]=mMap;
        dataFetch[1]=url;
        FetcData fetcData=new FetcData(getApplicationContext());
        fetcData.execute(dataFetch);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    private void SendMessage(String c){        Toast.makeText(this,c,Toast.LENGTH_SHORT).show(); }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (Request_Code) {
            case Request_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CurrentLocation();
                }
        }
    }
    private void CurrentLocation()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_Code);
                return;
        }
        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if(locationResult==null)
                {SendMessage("null value for location Result");
                    return;}

             Location location=locationResult.getLastLocation();
                    if(location!=null)
                    {   SendMessage("CurrentLocation is "+location.getLongitude()+","+location.getLatitude());
                    }


            }
             };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        Task<Location> task =fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                lattidue=location.getLatitude();
                longtidu=location.getLongitude();
                LatLng latLng=new LatLng(lattidue,longtidu);
                mMap.addMarker(new MarkerOptions().position(latLng).title("CurrentLocation"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                SendMessage(String.valueOf(lattidue)+String.valueOf(lattidue));
                    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                    Query query=firestore.collection("Restourant");
                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            for(QueryDocumentSnapshot document : task.getResult())
                           {


                               if(testLocation(document.get("RestourantLtd").toString(),document.get("RestourantLNG").toString()
                               ,location.getLatitude(),location.getLongitude()))
                               {
                                   LatLng latLng=new LatLng(Double.parseDouble(document.get("RestourantLtd").toString()),Double.parseDouble(document.get("RestourantLNG").toString()));
                                   mMap.addMarker(new MarkerOptions().position(latLng).title(document.get("RestourantName").toString()));}
                                }
                           }
                    });
                }

            }
        });
    }
    private boolean testLocation(String RestLAt,String Restlng,Double UserLat,Double UserLng)
    {
        Double Rlat=Double.parseDouble(RestLAt),RLng=Double.parseDouble(Restlng);
        SendMessage("("+(UserLat+0.005)+">"+Rlat+")"+"("+(UserLat-0.005)+"<"+Rlat+")"+"("+(UserLng+0.005)+">"+RLng+")"+"("+(UserLng-0.005)+"<"+RLng+")");
        if((UserLat+0.005>=Rlat)&&(UserLat-0.005<=Rlat)&&(UserLng+0.005>=RLng)&&(UserLng-0.005<=RLng))

            return true;
        else
            return false;
    }
}