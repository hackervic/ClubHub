package com.nucleus.events.clubhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Request_club_profile extends AppCompatActivity {

    DatabaseReference databaseReference, databaseReference1;
    CircleImageView clogo;
    ImageButton pencil;
    Toolbar toolbar;
    CardView approve, decline;
    String get_uid, get_name, get_catagory, get_collage, get_rating, get_member, get_logo, get_benifit, get_description, get_contact,
            get_leader_contact, get_leadername, get_member_1, get_member_2, get_member_3, get_coordinator, get_vision, l_type;
    TextView cname, ccat, ccol, crating, cbenifit, cdescription, ccontact, cleader,
            cmemberone, cmembertwo, cmemberthree, leadercontact, ccordinator, cvision, total_member, review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_club_profile);


        get_uid = getIntent().getExtras().get("send_uid").toString();
        //   toolbar = findViewById(R.id.clubr_profile_layout);
        pencil = findViewById(R.id.profile_pencil);
        cname = findViewById(R.id.c_name);
        ccat = findViewById(R.id.club_profile_c_catagory);
        ccol = findViewById(R.id.club_profile_c_collage);
        crating = findViewById(R.id.c_rating);
        clogo = findViewById(R.id.profile);
        cbenifit = findViewById(R.id.club_profile_c_benifit);
        cdescription = findViewById(R.id.club_profile_c_description);
        ccontact = findViewById(R.id.club_contact);
        cleader = findViewById(R.id.leader_name);
        leadercontact = findViewById(R.id.leader_contact);
        cmemberone = findViewById(R.id.club_member_one);
        cmembertwo = findViewById(R.id.club_member_two);
        cmemberthree = findViewById(R.id.club_memeber_three);
        ccordinator = findViewById(R.id.coordinator_name);
        cvision = findViewById(R.id.club_profile_c_bio);
        total_member = findViewById(R.id.total_members);
        review = findViewById(R.id.review);
        approve = findViewById(R.id.approve_btn);
        decline = findViewById(R.id.decline_btn);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clubs").child(get_uid);
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("students").child(get_uid);


        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //  String str = dataSnapshot.child("approved").getValue().toString();
                        //  Toast.makeText(Request_club_profile.this, str, Toast.LENGTH_SHORT).show();

                        dataSnapshot.getRef().child("approved").setValue("true");

                        Toast.makeText(Request_club_profile.this, "Club approved S8uccessfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Request_club_profile.this, Admin_Dashboard.class);
                        startActivity(i);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //  String str = dataSnapshot.child("approved").getValue().toString();
                        //  Toast.makeText(Request_club_profile.this, str, Toast.LENGTH_SHORT).show();

                        dataSnapshot.getRef().child("approved").setValue("false");

                        Toast.makeText(Request_club_profile.this, "Club Dis approved Suuccessfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Request_club_profile.this, Admin_Dashboard.class);
                        startActivity(i);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    get_name = dataSnapshot.child("cname").getValue().toString();
                    get_catagory = dataSnapshot.child("ccatagory").getValue().toString();
                    get_benifit = dataSnapshot.child("cbenifit").getValue().toString();
                    get_collage = dataSnapshot.child("ccollage").getValue().toString();
                    get_contact = dataSnapshot.child("ccontact").getValue().toString();
                    get_coordinator = dataSnapshot.child("ccoordinator").getValue().toString();
                    get_description = dataSnapshot.child("cdesc").getValue().toString();
                    get_leadername = dataSnapshot.child("cleader").getValue().toString();
                    get_logo = dataSnapshot.child("clogo").getValue().toString();
                    get_member = dataSnapshot.child("cmembers").getValue().toString();
                    get_rating = dataSnapshot.child("crating").getValue().toString();
                    get_member_1 = dataSnapshot.child("cpartnerone").getValue().toString();
                    get_member_2 = dataSnapshot.child("cpartnertwo").getValue().toString();
                    get_member_3 = dataSnapshot.child("cpartnerthree").getValue().toString();
                    get_vision = dataSnapshot.child("cvision").getValue().toString();


                    cname.setText(get_name);
                    //   crating.setText(get_rating);
                    cbenifit.setText(get_benifit);
                    ccontact.setText(get_contact);
                    ccordinator.setText(get_coordinator);
                    ccat.setText(get_catagory);
                    cdescription.setText(get_description);
                    cleader.setText(get_leadername);
                    cmemberone.setText(get_member_1);
                    cmembertwo.setText(get_member_2);
                    cmemberthree.setText(get_member_3);
                    total_member.setText(get_member);
                    cvision.setText(get_vision);
                    Glide.with(Request_club_profile.this).load(get_logo).into(clogo);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Toast.makeText(Request_club_profile.this, "loading", Toast.LENGTH_SHORT).show();
                    l_type = dataSnapshot.child("catagory").getValue().toString();
                    get_leader_contact = dataSnapshot.child("leaderemail").getValue().toString();
                    leadercontact.setText(get_leader_contact);
                    if (l_type.equals("Clubleader")) {
                        pencil.setVisibility(View.VISIBLE);
                    }
                    // Toast.makeText(Request_club_profile.this, "success", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
