package com.Coskun.eatie;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class FetcData extends AsyncTask<Object,String,String> {
   String googleNearPlacesByData;
   GoogleMap googleMap;
   String url;
   private final Context Ccontext;
   public FetcData(final Context context)
   {
    Ccontext=context;
   }
     @Override
    protected void onPostExecute(String s) {
        try{
            JSONObject jsonObject=new JSONObject(s);
if(jsonObject!=null)
{
    JSONArray jsonArray=jsonObject.getJSONArray("results");
    if(jsonArray.length()==0)
    {
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        DocumentReference documentReference;
        HashMap<String, Object> veri=new HashMap<>();
        veri.put("RestourantName", "name");
        veri.put("RestourantSum", "Rs.getText().toString()");
        veri.put("RestourantRate", "0");
        veri.put("RestourantTag", "Tag");

        documentReference = firestore.collection("Restourant").document("name.toString()");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                    }
                    else {
                       // firestore.collection("Restourant").document("name.toString()")
                     //           .set(veri);

                    }
                }
            }
        });
    }
    for(int i=0;i<jsonArray.length();i++)
    {
        JSONObject jsonObject1=jsonArray.getJSONObject(i);

        JSONObject getlocation=jsonObject1.getJSONObject("geometry").getJSONObject("location");
        String Lattidue=getlocation.getString("lat");
        String Lantidue=getlocation.getString("lng");
        LatLng latLng=new LatLng(Double.parseDouble(Lattidue),Double.parseDouble(Lantidue));
        JSONObject getName=jsonArray.getJSONObject(i);

        String name=getName.getString("name");

        MarkerOptions markerOptions=new MarkerOptions();

        markerOptions.title(name);
        markerOptions.position(latLng);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }

}
        }
        catch (JSONException E){
            E.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Object... objects) {
       try{
        googleMap=(GoogleMap) objects[0];
        url=(String) objects[1];
        GetDatas getDatas=new GetDatas();
        googleNearPlacesByData=getDatas.retriveUrl(url);
       }catch (IOException e)
       {e.printStackTrace();}
       return googleNearPlacesByData;
    }
}
