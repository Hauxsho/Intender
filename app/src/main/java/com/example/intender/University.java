package com.example.intender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class University extends AppCompatActivity {
    Button cont5;
    EditText UniName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        UniName=(EditText)findViewById(R.id.supUni);
        cont5=(Button)findViewById(R.id.Scont5);

        cont5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNAME=UniName.getText().toString();

                //Receive
                Intent mintent = getIntent();
                String nameFR = mintent.getStringExtra("NAMET");
                String birthFR = mintent.getStringExtra("BIRTHDAYT");
                String genderfr = mintent.getStringExtra("GENDER");


                Intent intent = new Intent(University.this ,Signup.class);
                intent.putExtra("UNI",strNAME);
                intent.putExtra("NAMEFR",nameFR);
                intent.putExtra("BIRTHDAYFR",birthFR);
                intent.putExtra("GENDERFR",genderfr);
                startActivity(intent);
                finish();
            }
        });
    }
}
