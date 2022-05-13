package com.Coskun.eatie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

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

    private void SendClicker(Context context)
    {
        Sendreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ReviewDb db=new ReviewDb(context);
               RestourantDb Rdb=new RestourantDb(context);

                Intent intent=getIntent();
                String UserName=intent.getStringExtra("UserNam");
                String RestName=intent.getStringExtra("ResName");
                int rate=getRate();
                db.DBAdd(UserName,RestName,Rev.getText().toString(),rate);
                db.close();
                ArrayList<Restourant> ALlRest=new ArrayList<>();
                ALlRest=Rdb.AllRestourants();
                for(Restourant restourant:ALlRest)
                {
                    if(restourant.getRestourantName()==RestName)
                        Rdb.ReAverage(RestName,restourant.SetAvrgRate(context));

                }

                finishAndRemoveTask();
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
}
