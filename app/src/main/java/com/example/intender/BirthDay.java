package com.example.intender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;


import java.util.Calendar;

public class BirthDay extends AppCompatActivity
{
    Button cont3;
    DatePicker  birthdate;
    private String getAge(int year, int month, int day)
    {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_day);


        cont3=(Button)findViewById(R.id.Scont3);
        birthdate=(DatePicker) findViewById(R.id.datep);
        int y=birthdate.getYear();
        int m=birthdate.getMonth();
        int d=birthdate.getDayOfMonth();
        final String fage=getAge(y,m,d);



        cont3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Receive
                Intent mintent = getIntent();
                String nameS = mintent.getStringExtra("NAME");



                //Send
                Intent intent = new Intent(BirthDay.this,Gender.class);
                intent.putExtra("NAMES",nameS);
                intent.putExtra("BIRTHDAY",fage);
                startActivity(intent);
                finish();
            }
        });

    }

}
