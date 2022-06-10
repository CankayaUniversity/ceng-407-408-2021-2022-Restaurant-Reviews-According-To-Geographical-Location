package com.Coskun.eatie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static Button  SignIn,Anonymous,SignUp,NewRestourant;
    private static EditText UserId,UserPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Const();
    }
    public void Const()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
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
        SignUpClicker();
        CntrlId();
        NewRestourantClicker();
        newmap();
    }

    public void newmap()
    {
        Anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Maps();
            }
        });
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
        if(isEmpty())
        {
            if(UserId.getText().toString().equals("Coskun"))
            {
                if(UserPassword.getText().toString().equals("123456789"))
                    AdminPag();
            }
            else
            {documentReference=firestore.collection("User").document(UserId.getText().toString());
                documentReference.get().addOnSuccessListener(MainActivity.this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists())
                        {
                            if(documentSnapshot.getData().get("UserMail").toString().equals(UserId.getText().toString()))
                            {
                                if(documentSnapshot.getData().get("UserPassword").toString().equals(UserPassword.getText().toString()))

                                    if(documentSnapshot.getData().get("Utag").toString().equals("Logged")||documentSnapshot.getData().get("Utag").toString().equals("Pending"))
                                    {
                                        LoggedPage();
                                    }
                                    else if(documentSnapshot.getData().get("Utag").toString().equals("Boss"))
                                    {      patronn();
                                    }

                            }
                        }

                    }
                });
            }

        }

    }
});

}
private boolean isEmpty()
{
    if(!TextUtils.isEmpty(UserId.getText().toString()) && !TextUtils.isEmpty(UserPassword.getText().toString()))
        return true;
    else
        return false;
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
private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }
public void LoggedPage()
{
    Intent c=new Intent(this, LoggedMp.class);
    c.putExtra("UserNam",UserId.getText().toString());
    c.putExtra("UserPassw",UserPassword.getText().toString());
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
private void Maps()

{  Intent c=new Intent(this, MapsActivity.class);
    startActivity(c);
}
private void patronn(){
    Intent c=new Intent(this, BossPage.class);
    c.putExtra("BossName",UserId.getText().toString());
    startActivity(c);
}
}
