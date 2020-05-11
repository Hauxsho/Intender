package com.example.intender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class  Photos extends AppCompatActivity
{

    Button cont6;
    TextView t1, t2, t3 , t4 ,t5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        //Receive
        Intent mintent = getIntent();
        String phoneFI = mintent.getStringExtra("PHONEFR");
        String nameFI= mintent.getStringExtra("NAMEFR");
        String birthFI = mintent.getStringExtra("BIRTHDAYFR");
        String genderFI = mintent.getStringExtra("GENDERFR");
        String uniFI = mintent.getStringExtra("UNI");

        t1=(TextView)findViewById(R.id.t1);
        t1.setText(phoneFI);
        t2=(TextView)findViewById(R.id.t2);
        t2.setText(nameFI);
        t3=(TextView)findViewById(R.id.t3);
        t3.setText(birthFI);
        t4=(TextView)findViewById(R.id.t4);
        t4.setText(genderFI);
        t5=(TextView)findViewById(R.id.t5);
        t5.setText(uniFI);
    }
}
