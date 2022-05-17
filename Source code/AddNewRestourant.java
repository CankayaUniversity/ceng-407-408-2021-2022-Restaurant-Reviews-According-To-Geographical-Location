
package com.Coskun.eatie;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class AddNewRestourant extends AppCompatActivity {
    public static EditText Name,Rate,Sum;
    public static Button Snd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_restourant);
        cont();

    }
    private void cont()
    {
        Name=findViewById(R.id.Name);
        Rate=findViewById(R.id.Rate);
        Sum=findViewById(R.id.Summary);
        Snd=findViewById(R.id.SendBtn);
        BtnClick();
    }
    public void BtnClick()
    {
        Snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
