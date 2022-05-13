package com.Coskun.eatie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static Button  SignIn,Anonymous,SignUp,NewRestourant;
    private static EditText UserId,UserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Const();
    }
    public void Const()
    {
        SignIn=findViewById(R.id.SignInnbtn);
        Anonymous=findViewById(R.id.Anonymousbtn);
        SignUp=findViewById(R.id.SignUpbtn);
        UserId=findViewById(R.id.Idt);
        UserPassword=findViewById(R.id.PasswordT);
        NewRestourant=findViewById(R.id.AddNewRestourant);
        CallClicker();
    }
    private void CallClicker()
    {
        SignInClicker(this);
        AnonymousClicker();
        SignUpClicker();
        CntrlId();
        NewRestourantClicker();
    }

    public void CntrlId()
    {
        UserId.setOnKeyListener(new View.OnKeyListener() {
            @Override

            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_UP && i==KeyEvent.KEYCODE_ENTER)
                {
                    if(UserId.length()==0)
                    {
                        SendMessage("UserId Can't be Empty");
                        return false;
                    }
                }
                return false;
            }
        });
    }
    public void NewRestourantClicker()
    {
        NewRestourant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewRestourant();
            }
        });
    }
    private void SendMessage(String c)
    {
        Toast.makeText(this,c,Toast.LENGTH_SHORT).show();
    }

public void SignInClicker(Context context)
{
SignIn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view)
    {
        User a=new User();
        String q;
        q=a.ControlUserLoginActivity(context,UserId.getText().toString(),UserPassword.getText().toString());
        if(q=="2")
        {AdminPag();}
        else if(q=="1")
        {LoggedPage();}
        else if(q=="0")
        {SendMessage("No Such Id");}
        else{SendMessage("Wrong Password");}
    }
});

}
public void AnonymousClicker()
{
Anonymous.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AnonymousPage();
    }
});

}
public void SignUpClicker()
{
SignUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       SingUp();
    }
});
}
public void LoggedPage()
{
    Intent c=new Intent(this, LoggedMp.class);
    c.putExtra("UserNam",UserId.getText().toString());
    startActivity(c);
}
public void AnonymousPage()
{
        Intent c=new Intent(this, MapsActivity.class);
        startActivity(c);
}
public void SingUp()
{
    Intent c=new Intent(this, SignUp.class);
    startActivity(c);
}
public void NewRestourant()
{
    Intent c=new Intent(this, LoggedMp.class);
    c.putExtra("UserNam",UserId.getText().toString());
    startActivity(c);
}
public void AdminPag()
{
    Intent c=new Intent(this, AdminPage.class);
    startActivity(c);
}


}
