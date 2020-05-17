package com.example.intender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class  Photos extends AppCompatActivity
{
    private String userId,profileImageUrl;
    private ImageView mImageOne;
    private Button uploadPhotos,contPho;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private Uri resultUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        mImageOne = (ImageView) findViewById(R.id.picOne);
        uploadPhotos=(Button)findViewById(R.id.uploadphoto);
        contPho=(Button)findViewById(R.id.conp);
        Intent mintent = getIntent();
        final String genderfPhotos = mintent.getStringExtra("GENDERFPhotos");


        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(genderfPhotos).child(userId);
        saveUserinfo();





        //image pe click kiya to intent

        mImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        contPho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Photos.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        //bhai upload mar with use of save information
        uploadPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserinfo();

            }
        });

    }



    private void saveUserinfo() {

        Map userInfo = new HashMap();

        //ye pehle image ke uri
        if (resultUri != null) {
            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImages").child(userId);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = filepath.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                finish();
            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUri =taskSnapshot.getStorage().getDownloadUrl();
                Map userInfo = new HashMap();
                userInfo.put("profileImageUrl", downloadUri.toString());
                mUserDatabase.updateChildren(userInfo);
                finish();
            }
        });
        }


        else {
        finish();
        }
    }






    //yaha code decode hoga

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            mImageOne.setImageURI(resultUri);
        }

    }

//ye download
    private void getUserInfo() {
        mUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    Glide.with(getApplication()).clear(mImageOne);
                    if (map.get("profileImageUrl") != null) {
                        profileImageUrl = map.get("profileImageUrl").toString();
                        switch (profileImageUrl) {
                            case "default":
                                Glide.with(getApplication()).load(R.mipmap.ic_launcher).into(mImageOne);
                                break;
                            default:
                                Glide.with(getApplication()).load(profileImageUrl).into(mImageOne);
                                break;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
          
            }
        });
    }
}