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

import com.example.intender.Cards.ArrayAdapt;
import com.example.intender.Cards.Cards;
import com.example.intender.Matches.Matches;
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
    private  String userGender, oppositeGender;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        currentuserId = mAuth.getCurrentUser().getUid();
        usersDb = FirebaseDatabase.getInstance().getReference().child("Users");
        checkGender();

        rowItems = new ArrayList<Cards>();

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
                usersDb.child(userid).child("connections").child("NOPE").child(currentuserId).setValue(true);
                isConnectionMatch(userid);
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards)dataObject;
                String userid = obj.getUserID();
                usersDb.child(userid).child("connections").child("YUP").child(currentuserId).setValue(true);
                isConnectionMatch(userid);
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
        DatabaseReference currentUserConnectionDB = usersDb.child(currentuserId).child("connections").child("YUP").child(userid);
        currentUserConnectionDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Toast.makeText(MainActivity.this, "new connection", Toast.LENGTH_SHORT).show();
                    usersDb.child(dataSnapshot.getKey()).child("connections").child("Matches").child(currentuserId).setValue(true);
                    usersDb.child(currentuserId).child("connections").child("Matches").child(dataSnapshot.getKey()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void checkGender()
    {
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference userDb = usersDb.child(user.getUid());
        usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("gender").getValue() != null) {
                        userGender = dataSnapshot.child("gender").getValue().toString();
                        switch (userGender) {
                            case "Male":
                                oppositeGender = "Female";
                                break;
                            case "Female":
                                oppositeGender = "Male";
                                break;
                        }
                        getGender();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }


    public void getGender()
    {

        usersDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.child("gender").getValue() != null) {
                    if (dataSnapshot.exists() && !dataSnapshot.child("connections").child("YUP").hasChild(currentuserId) && !dataSnapshot.child("connections").child("NOPE").hasChild(currentuserId) && dataSnapshot.child("gender").getValue().toString().equals(oppositeGender)) {
                        String profileImageUrl = "default";
                        if (!dataSnapshot.child("profileImageUrl").getValue().toString().equals("default")) {
                            profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        }
                        Cards Item = new Cards(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(), profileImageUrl);
                        rowItems.add(Item);
                        arrayAdapt.notifyDataSetChanged();
                    }
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
        startActivity(intent);
        Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
    }

    public void gotoMatches(View view) {
        Intent intent = new Intent(MainActivity.this, Matches.class);
        startActivity(intent);
    }
}
