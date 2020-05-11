package com.example.intender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Gender extends AppCompatActivity {

    Button cont4;
    RadioGroup mRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        cont4=(Button)findViewById(R.id.Scont2);

        cont4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioGroup= (RadioGroup) findViewById(R.id.rg);
                int selectId = mRadioGroup.getCheckedRadioButtonId();

                final RadioButton radioButton = (RadioButton) findViewById(selectId);

                if(radioButton.getText() == null){
                    return;
                }
                String strGEN = radioButton.getText().toString();
                //Receive
                Intent mintent = getIntent();
                String nameT = mintent.getStringExtra("NAMES");
                String birthT = mintent.getStringExtra("BIRTHDAY");


                Intent intent = new Intent(Gender.this ,University.class);
                intent.putExtra("GENDER",strGEN);
                intent.putExtra("NAMET",nameT);
                intent.putExtra("BIRTHDAYT",birthT);
                startActivity(intent);
                finish();
            }
        });
    }
}
