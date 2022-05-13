package com.Coskun.eatie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminPage extends AppCompatActivity {
private static EditText delet,RN,Rs;
private static Button DelUser,DelRev,DelRest,AddRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        Const();
    }
    private void SendMessage(String c)
    {
        Toast.makeText(this,c,Toast.LENGTH_SHORT).show();
    }
    private void GetDeletedStr(String cnt)
    {
        UDBControl udbControl=new UDBControl(this);
        ReviewDb reviewDb=new ReviewDb(this);
        RestourantDb restourantDb=new RestourantDb(this);
        String a=delet.getText().toString();
        if(cnt=="0")udbControl.DBremove(a);
        else if(cnt=="1")reviewDb.DBremoveAllFromOneUser(a);
        else if(cnt=="2")restourantDb.DBremove(a);
    }
    private void Const()
    {
        delet=findViewById(R.id.Deleted);
        RN=findViewById(R.id.RestourantName);
        Rs=findViewById(R.id.RestourantSummary);
        DelUser=findViewById(R.id.DelUser);
        DelRev=findViewById(R.id.DelRev);
        DelRest=findViewById(R.id.DelRest);
        AddRes=findViewById(R.id.AddRest);
        Btns();
    }
    private void Btns()
    {DeleteUser();
    DeleteReview();
    DeleteRestourant();
    AddRestourant(this);
    }
    private void DeleteUser()
    {
        DelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDeletedStr("0");
            }
        });
    }

    private void DeleteReview()
    {
        DelRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDeletedStr("1");
            }
        });
    }
    private void DeleteRestourant()
    {
        DelRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDeletedStr("2");
            }
        });
    }
    private void AddRestourant(Context context)
    {
        AddRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestourantDb restourantDb=new RestourantDb(context);
                restourantDb.DBAdd(RN.getText().toString(),Rs.getText().toString(),0.0);
                ReviewDb reviewDb=new ReviewDb(context);
                SendMessage("Restourant Added");
            }
        });
    }
}
