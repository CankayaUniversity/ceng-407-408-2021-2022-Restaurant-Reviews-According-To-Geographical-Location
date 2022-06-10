package com.Coskun.eatie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddReview extends AppCompatActivity {
static private EditText Rev;
static private Button Sendreview;
static private RadioGroup Select;
static private RadioButton frst,lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
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
                Intent intent=getIntent();
                HashMap<String, Object> veri=new HashMap<>();
                String RestourantName=intent.getStringExtra("ResName"),UserName=intent.getStringExtra("UserNam");

                veri.put("UserName",UserName);
                veri.put("RestourantName",RestourantName);
                veri.put("Rate",getRate());
                veri.put("yorum",Rev.getText().toString());
                veri.put("YorumP",String.valueOf(0));
                //db.collection('users').doc(this.username).collection('booksList').doc(myBookId).set({
                firestore.collection("Yorum").document(RestourantName).collection("Yorumlar").document(Rev.getText().toString()).set(veri);

                Reload();
            }
        });
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
