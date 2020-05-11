package com.example.intender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.intender.R.layout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> al;
    private ArrayAdapt arrayAdapt;
    ListView listView;
    List<Cards> rowItems;
    private int i;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();

        checkGender();

        rowItems = new ArrayList<>();

        arrayAdapt = new ArrayAdapt(this, layout.item, R.id.helloText, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapt);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener()
        {
            @Override
            public void removeFirstObjectInAdapter()
            {
                Log.d("LIST", "removed object!");
                    rowItems.remove(0);
                    arrayAdapt.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                    Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                    Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }
            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                    Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private  String userGender, oppositeGender;

    public void checkGender()
    {
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference maleDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Male");
        maleDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user.getUid()))
                {
                    userGender = "Male";
                    oppositeGender = "Female";
                    getGender();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



        DatabaseReference femaleDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Male");
      femaleDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user.getUid()))
                {
                    userGender = "Female";
                    oppositeGender = "Male";
                    getGender();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s){
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s){
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getGender()
    {
        DatabaseReference oppositeGenDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Male");
        oppositeGenDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists())
                {
                   al.add(dataSnapshot.child("name").getValue().toString());
                   arrayAdapt.notifyDataSetChanged();
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void logout(View view)
    {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this ,Choose.class);
        startActivity(intent);
        Toast.makeText(this, "Signed Out", Toast.LENGTH_LONG).show();
        finish();
    }
}
