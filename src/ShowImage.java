package com.Coskun.eatie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        Intent intent=getIntent();
        String b=intent.getStringExtra("ResName");
        ImageView c=findViewById(R.id.men);
        switch(b)
        {
            case "Arabica":
            c.setImageResource(R.drawable.arabica);
            case "Coffy Kahve":
                c.setImageResource(R.drawable.coffy);
            case"Düveroğlu ocakbaşı":
                c.setImageResource(R.drawable.duveroglu);
            case "Masabaşı Et":
                c.setImageResource(R.drawable.masabasi);
            case "Mc Donalds":
                c.setImageResource(R.drawable.mcdonalds);
            case "Otantik Kumpir":
                c.setImageResource(R.drawable.otantik);
            case "Pablo cafe":
                c.setImageResource(R.drawable.pablo);
            case "Papilon cafe":
                c.setImageResource(R.drawable.papilon);
            case "Turuncu cafe":
                c.setImageResource(R.drawable.turuncu);
            case "Varuna Kitap Bar":
                c.setImageResource(R.drawable.varuna);
        }
    }
}