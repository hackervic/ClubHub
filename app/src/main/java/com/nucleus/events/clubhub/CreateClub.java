package com.nucleus.events.clubhub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public class CreateClub extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;

    ImageView club_logo;
    TextView leader_name, change_logo;
    EditText c_name, c_coordinator, c_leader, c_contact, c_vision, c_benifit, c_desc, c_catagory;
    final static int Gallery_pick = 1;
    Uri imageuri;
    Button register_my_club;
    DatabaseReference databaseReference, databaseReference1, databaseReference2;
    StorageReference storageReference;
    String club_uid, c_collage;
    FirebaseAuth firebaseAuth;
    String club_name, cuid, img_url, club_leader, club_contact, club_vision, club_benifit, club_description, club_coordinator, strname, club_catagory;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_club);

//        toolbar =  findViewById(R.id.creat_club_layout);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Create Club");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(CreateClub.this,HomeActivity.class));
//            }
//        });
//

        register_my_club = findViewById(R.id.club_register_btn);
        club_logo = findViewById(R.id.club_logo);
        leader_name = findViewById(R.id.tv_leader_name);
        change_logo = findViewById(R.id.tv_change_club_logo);
        c_name = findViewById(R.id.club_name);
        c_coordinator = findViewById(R.id.club_coordinator_name);
        c_leader = findViewById(R.id.Leader_name);
        c_contact = findViewById(R.id.club_contact);
        c_vision = findViewById(R.id.vision);
        c_benifit = findViewById(R.id.benifit);
        c_catagory = findViewById(R.id.club_catagory);
        c_desc = findViewById(R.id.club_description);
        firebaseAuth = FirebaseAuth.getInstance();
        cuid = firebaseAuth.getCurrentUser().getUid();
        // toolbar = (Toolbar) findViewById(R.id.creat_club_layout);
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("students").child(cuid);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clubs").child(cuid);
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("students").child(cuid).child("myclubs").child(cuid);


        change_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent galleryint = new Intent();
                galleryint.setAction(Intent.ACTION_GET_CONTENT);
                galleryint.setType("image/*");
                startActivityForResult(galleryint, Gallery_pick);
            }
        });


        register_my_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveClubInformation();
            }
        });


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                   // strname = dataSnapshot.child("clubleader").getValue().toString();
                    c_collage = dataSnapshot.child("collage").getValue().toString();
                   // club_uid = dataSnapshot.child("uid").getValue().toString();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        leader_name.setText(strname);

    }

    private void SaveClubInformation() {

        club_name = c_name.getText().toString();
        club_coordinator = c_coordinator.getText().toString();
        club_leader = c_leader.getText().toString();
        club_contact = c_contact.getText().toString();
        club_vision = c_vision.getText().toString();
        club_benifit = c_benifit.getText().toString();
        club_description = c_desc.getText().toString();
        club_catagory = c_catagory.getText().toString();


        if (TextUtils.isEmpty(club_name)) {
            Toast.makeText(this, "club name are required", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(club_coordinator)) {
            Toast.makeText(this, "club coordinator", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(club_leader)) {
            Toast.makeText(this, "Club leader", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(club_contact)) {
            Toast.makeText(this, "club contact", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(club_vision)) {
            Toast.makeText(this, "club vision", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(club_benifit)) {
            Toast.makeText(this, "club benifit", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(club_description)) {
            Toast.makeText(this, "club description", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(club_catagory)) {
            Toast.makeText(this, "club description", Toast.LENGTH_SHORT).show();
        } else {
            final HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("cname", club_name);
            hashMap.put("cleader", club_leader);
            hashMap.put("cbenifit", club_benifit);
            hashMap.put("cdesc", club_description);
            hashMap.put("cvision", club_vision);
            hashMap.put("ccoordinator", club_coordinator);
            hashMap.put("clubuid",cuid );
            hashMap.put("ccollage", c_collage);
            hashMap.put("ccontact", club_contact);
            hashMap.put("ccatagory", club_catagory);
            hashMap.put("approved", "none");
            hashMap.put("cmembers", "0");
            hashMap.put("cpartnerone", "none");
            hashMap.put("cpartnertwo", "none");
            hashMap.put("crating", "1.0");
            hashMap.put("cpartnerthree", "none");
            hashMap.put("clogo", img_url);
            databaseReference1.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(CreateClub.this, "task one finished", Toast.LENGTH_SHORT).show();
                        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateClub.this, "club created succesfully", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(CreateClub.this, "club deatails sent to admin for approval", Toast.LENGTH_SHORT).show();
                                   Intent i =  new Intent(CreateClub.this, HomeActivity.class);
                                 //  i.putExtra("currentid",club_uid);
                                    startActivity(i);

                                } else {
                                    Toast.makeText(CreateClub.this, "club failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    } else {
                        Toast.makeText(CreateClub.this, "club creation failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {

        if (requestCode == Gallery_pick && resultCode == RESULT_OK && data != null) {
            imageuri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setBorderLineColor(Color.BLACK)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resulturi = result.getUri();
                File file_path = new File(Objects.requireNonNull(resulturi.getPath()));
                Bitmap thumb_bitmap = null;
                try {

                    thumb_bitmap = new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(50)
                            .compressToBitmap(file_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thumb_byte = baos.toByteArray();
                club_logo.setImageURI(resulturi);

                storageReference = FirebaseStorage.getInstance().getReference().child("Club Logo").child(cuid);

                StorageReference filepath = storageReference.child(cuid + ".jpg");
                filepath.putFile(resulturi).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(CreateClub.this, "Image uploaded succesfull", Toast.LENGTH_SHORT).show();
                            final Task<Uri> result = task.getResult().getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    img_url = uri.toString();
                                    Toast.makeText(CreateClub.this, img_url, Toast.LENGTH_SHORT).show();

                                }
                            });
                        } else {
                            Toast.makeText(CreateClub.this, "Failed to store image", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }

        } else {
            Toast.makeText(this, "Crop image doesnt support", Toast.LENGTH_SHORT).show();
            // loadingBar.dismiss();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

}



