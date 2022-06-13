package com.Coskun.eatie;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    public static Button  Enter;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private HashMap<String,Object> veri;
    public static EditText UserId,UserMail,UserPassword,PasswordCntrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Const();
        CallClicker(this);

    }
    public void Const()
    {
        UserId=findViewById(R.id.UserId);
        UserMail=findViewById(R.id.UserEmail);
        UserPassword=findViewById(R.id.UserPassword);
        PasswordCntrl=findViewById(R.id.PasswordCntrl);
        Enter=findViewById(R.id.EnterBtn);
        veri=new HashMap<>();
        firebaseAuth= FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
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
   private void AddToMail(){
       final boolean[] t = {false};
       veri.put("UserName",UserId.getText().toString());
       veri.put("UserMail",UserMail.getText().toString());
       veri.put("UserPassword",UserPassword.getText().toString());
       veri.put("Utag","Logged");
       veri.put("RestName"," null");
       veri.put("ULat","null");
       veri.put("ULng"," null");
       firebaseAuth.createUserWithEmailAndPassword(UserMail.getText().toString(),UserPassword.getText().toString());

       documentReference = firestore.collection("User").document(UserMail.getText().toString());
       documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {
                       SendMessage("There is a user with given ID");
                   }
                   else {
                       firestore.collection("User").document(UserMail.getText().toString())
                               .set(veri);

                   }
               } else {
                   Log.d("TAG", "get failed with ", task.getException());
               }
           }
       });

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

                                //Add();
                                AddToMail();
                                finish();
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