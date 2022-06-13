package com.Coskun.eatie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class SettingsPage extends AppCompatActivity {

    public static Button Update,UpdateImage;
    private FirebaseFirestore firestore;
    private DocumentReference documentReference;
    private HashMap<String,Object> veri;
    private String OldUserName,OldUserMail,OldUserPAssword,UserNm;
    private ImageView pp;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri ImgUri;
    public static EditText NewUserId,NewUserPassword,NewPasswordCntrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings_page);
        Intent intent=getIntent();
        UserNm=intent.getStringExtra("UserNam");
        String Userpss=intent.getStringExtra("UserPassw");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        Const();
        CallClicker(UserNm,Userpss);
        Imgupdpressed();
    }
    public void Const()
    {
        NewUserId=findViewById(R.id.NewID);
        NewUserPassword=findViewById(R.id.NewUserPassword);
        NewPasswordCntrl=findViewById(R.id.NewPasswordCntrl);
        Update=findViewById(R.id.Güncelle);
        UpdateImage=findViewById(R.id.UploadImage);
        firestore=FirebaseFirestore.getInstance();
        pp=findViewById(R.id.profilepic);
    }
    private void Imgupdpressed()
    {
        pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
  */
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
    }
    protected void onActivityResult(int requestCode,int ResultCode,Intent data)
    {
        super.onActivityResult(requestCode,ResultCode,data);
      /*  if(ResultCode==RESULT_OK&&data!=null)
        {           Uri selected=data.getData();
            ImageView imageView=findViewById(R.id.profilepic);
            imageView.setImageURI(selected);
        }
        */
        if(requestCode==1&&ResultCode==RESULT_OK&&data!=null&&data.getData()!=null)
        {
            ImgUri=data.getData();
            pp.setImageURI(ImgUri);
            UploadPict();
        }
    }
    private void UploadPict()
    {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Uploading Image");
        pd.show();
        StorageReference riversref=storageReference.child("images/"+UserNm);
        riversref.putFile(ImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {SendMessage("lol");
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progresperc=(100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Yüzde "+(int) progresperc+"bitti ");
            }
        });
    }

    private void SendMessage(String c)
    {
        Toast.makeText(this,c,Toast.LENGTH_SHORT).show();
    }
    private int IsEmpty()
    {
        int i=0;
        if(NewUserId.getText().length()!=0)
        {
            i=i+1;
        }
        if(!(NewUserPassword.getText().length()==0 && NewPasswordCntrl.getText().length()== 0) && NewPasswordCntrl.getText().toString().equals(NewUserPassword.getText().toString()))
        {
            i=i+2;
        }
     return i;
    }


    private void PassUpd(String Mail,String NewPass)
    {
        veri=new HashMap<>();
        veri.put("UserPassword",NewPass);
        documentReference = firestore.collection("User").document(Mail);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        firestore.collection("User").document(Mail)
                                .update(veri);
                    }
                    else {

                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
    private void idUpd(String Mail,String NewID)
    {
        veri=new HashMap<>();
        veri.put("UserName",NewID);
        documentReference = firestore.collection("User").document(Mail);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        firestore.collection("User").document(Mail)
                                .update(veri);
                    }
                    else {

                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
    private void BothUpd(String Mail,String newId,String newPass){
        veri=new HashMap<>();
        veri.put("UserName",newId);
        veri.put("UserPassword",newPass);
        veri.put("UserMail",Mail);
        documentReference = firestore.collection("User").document(Mail);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        firestore.collection("User").document(Mail)
                                .update(veri);
                    }
                    else {

                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    private void CallClicker(String Name,String Pss)
    {
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (IsEmpty())
                {

                    case 0:
                        SendMessage("Both can't be empty");
                        break;
                    case 1:
                        idUpd(Name,NewUserId.getText().toString());
                        break;
                    case 2:
                        PassUpd(Name,NewPasswordCntrl.getText().toString());
                        break;
                    case 3:
                        BothUpd(Name,NewUserId.getText().toString(),NewPasswordCntrl.getText().toString());
                        break;
                }
            }
        });
    }
}