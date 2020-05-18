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
import com.google.firebase.database.ValueEventListener;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Cards cards_data[];
    private ArrayAdapt arrayAdapt;
    ListView listView;
    List<Cards> rowItems;
    private int i;
    private String currentuserId;
    private DatabaseReference usersDb;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        currentuserId = mAuth.getCurrentUser().getUid();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        checkGender();

        rowItems = new ArrayList<>();

        arrayAdapt = new ArrayAdapt(this, layout.item, rowItems);

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
                Cards obj = (Cards)dataObject;
                String userid = obj.getUserID();
                usersDb.child(oppositeGender).child(userid).child("connections").child("NOPE").child(currentuserId).setValue(true);
                isConnectionMatch(userid);
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards)dataObject;
                String userid = obj.getUserID();
                usersDb.child(oppositeGender).child(userid).child("connections").child("YUP").child(currentuserId).setValue(true);
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

    private void isConnectionMatch(String userid) {
        DatabaseReference currentUserConnectionDB = usersDb.child(userGender).child(currentuserId).child("connections").child("YUP").child(userid);
        currentUserConnectionDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Toast.makeText(MainActivity.this, "new connection", Toast.LENGTH_SHORT).show();
                    usersDb.child(oppositeGender).child(dataSnapshot.getKey()).child("connections").child("Matches").child(currentuserId).setValue(true);
                    usersDb.child(userGender).child(currentuserId).child("connections").child("Matches").child(dataSnapshot.getKey()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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



        DatabaseReference femaleDB = FirebaseDatabase.getInstance().getReference().child("Users").child("Female");
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
        DatabaseReference oppositeGenDB = FirebaseDatabase.getInstance().getReference().child("Users").child(oppositeGender);
        oppositeGenDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists() && !dataSnapshot.child("connections").child("YUP").hasChild(currentuserId) && !dataSnapshot.child("connections").child("NOPE").hasChild(currentuserId))
                {
                    Cards Item = new Cards(dataSnapshot.getKey(),dataSnapshot.child("name").getValue().toString());
                    rowItems.add(Item);
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

    public void settings(View view) {
        Intent intent = new Intent(MainActivity.this ,Settings.class);
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        intent.putExtra("UserGen",userGender);
        startActivity(intent);
        Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
    }
}
