package com.example.intender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Name extends AppCompatActivity {

    EditText userame;
    Button cont2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        userame=(EditText)findViewById(R.id.supName);
        cont2=(Button)findViewById(R.id.Scont2);

        cont2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strNAME=userame.getText().toString();

                //Send
                Intent intent = new Intent(Name.this ,BirthDay.class);
                intent.putExtra("NAME",strNAME);
                startActivity(intent);
                finish();
            }
        });
    }
}
