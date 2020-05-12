 package com.example.intender;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

 public class Signup extends AppCompatActivity {

    private Button cont1;
    private EditText Email, Pass;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent mintent = getIntent();
        final String nameFI= mintent.getStringExtra("NAMEFR");
        final String birthFI = mintent.getStringExtra("BIRTHDAYFR");
        final String genderFI = mintent.getStringExtra("GENDERFR");
        final String uniFI = mintent.getStringExtra("UNI");



        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null)
                {
                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }

            }
        };

        cont1 = (Button) findViewById(R.id.Scont1);
        Email = (EditText) findViewById(R.id.supEmail);
        Pass = (EditText) findViewById(R.id.supPass);
        cont1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                final String email = Email.getText().toString();
                final String password = Pass.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Signup.this, "sign up error", Toast.LENGTH_LONG).show();
                        }
                        else {
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Users").child(genderFI).child(userId);
                            Map userInfo = new HashMap<>();
                            userInfo.put("name",nameFI);
                            userInfo.put("birth", birthFI);
                            userInfo.put("uni", uniFI);
                            userInfo.put("profileImageUrl", "default");
                            currentUserDb.updateChildren(userInfo);

                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}


