package com.example.intender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose extends AppCompatActivity {

    private Button clogin , csignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        clogin = (Button) findViewById(R.id.chooselogin);
        csignup = (Button) findViewById(R.id.choosesignup);

        clogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Choose.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        csignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Choose.this,Name.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
