package com.Coskun.eatie;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.KeyEvent;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    public static Button  Enter;
    public static EditText UserId,UserMail,UserPassword,PasswordCntrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Const();
        CallClicker(this);
        Restourant c=new Restourant();
        Reviews w=new Reviews();
        ArrayList<Restourant> q=new ArrayList<>();
        q=c.getData(this);
        ArrayList<Reviews> rev=new ArrayList<>();
        rev=w.GetAllReviews(this);
    }
    public void Const()
    {
        UserId=findViewById(R.id.UserId);
        UserMail=findViewById(R.id.UserEmail);
        UserPassword=findViewById(R.id.UserPassword);
        PasswordCntrl=findViewById(R.id.PasswordCntrl);
        Enter=findViewById(R.id.EnterBtn);
    }
    private void SendMessage(String c)
    {
        Toast.makeText(this,c,Toast.LENGTH_SHORT).show();
    }
    private boolean IsEmpty()
    {
        boolean q=false;
        if(UserId.getText().length()==0)
        {
            SendMessage("UserID Cannot be empty");
            q=true;
        }
        if(UserMail.getText().length()==0)
        {
            SendMessage("UserMail Cannot be empty");
            q=true;
        }
        if(UserPassword.getText().length()==0)
        {
            SendMessage("UserPassword Cannot be empty");
            q=true;
        }
        if(PasswordCntrl.getText().length()==0)
        {
            SendMessage("Adding Password Cannot be empty");
            q=true;
        }
        if(!PasswordCntrl.getText().toString().equals(UserPassword.getText().toString()))
        {
            SendMessage("PassWords should be same");
            q=true;
        }
        return q;
    }
    private void Add()
    {
        UDBControl db=new UDBControl(this);
        db.DBAdd(UserId.getText().toString(),UserPassword.getText().toString(),UserMail.getText().toString());
    }
    private void CallClicker(Context context)
    {
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String a;
                if(!IsEmpty())
                {

                   a=UserMail.getText().toString();
                    if(a.contains("@gmail.com")||a.contains("@hotmail.com"))
                    {
                        try
                        {
                            User b=new User();
                           if(!b.ControlUserName(context,UserId.getText().toString()))
                            {
                                Add();
                                finish();
                            }
                           else
                           {SendMessage("There is a user with given ID"); }
                        }
                        catch (Exception e)
                        {
                         e.printStackTrace();
                        }
                    }
                    else
                    {
                        SendMessage("No such E_Mail");
                    }

                }
            }
        });
    }
}
