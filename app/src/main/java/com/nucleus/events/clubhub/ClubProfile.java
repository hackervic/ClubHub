package com.nucleus.events.clubhub;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClubProfile extends AppCompatActivity {

    DatabaseReference databaseReference, databaseReference1, databaseReference2, databaseReference3, databaseReference4, databaseReference5;
    CircleImageView clogo;
    ImageButton pencil;
    Toolbar toolbar;
    ImageView join_club_image;
    TextView join_club_text;
    HashMap<String, Object> member_map;
    FirebaseAuth firebaseAuth;
    MaterialCardView cardView , leave_club;
    String current_login_id, s_name, s_collage, s_branch, s_pic, s_contact, s_semester;
    String get_uid, get_name, get_catagory, get_collage, get_rating, get_member, get_logo, get_benifit, get_description, get_contact,
            get_leader_contact, get_leadername, get_member_1, get_member_2, get_member_3, get_coordinator, get_vision, l_type;
    TextView cname, ccat, ccol, crating, cbenifit, cdescription, ccontact, cleader,
            cmemberone, cmembertwo, cmemberthree, leadercontact, ccordinator, cvision, total_member, review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club__profile);

        get_uid = getIntent().getExtras().get("cuid").toString();
        firebaseAuth = FirebaseAuth.getInstance();
        current_login_id = firebaseAuth.getUid();
        cardView = findViewById(R.id.btn_join_club);
        join_club_image = findViewById(R.id.join_club_image);
        join_club_text = findViewById(R.id.join_club_tv);
//       toolbar = findViewById(R.id.club_profile_layout);
        pencil = findViewById(R.id.profile_pencil);
        cname = findViewById(R.id.c_name);
        leave_club = findViewById(R.id.btn_leave_club);
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

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clubs").child(get_uid);
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("students").child(get_uid);
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("students").child(current_login_id);
        databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Clubs").child(get_uid).child("ClubMembers").child(current_login_id);
        databaseReference4 = FirebaseDatabase.getInstance().getReference().child("students").child(get_uid).child("myclubs").child(get_uid).child("ClubMembers").child(current_login_id);
        //  toolbar = (Toolbar) findViewById(R.id.notification_toolbar);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JoinClub();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
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
                    crating.setText(get_rating);
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
                    Glide.with(ClubProfile.this).load(get_logo).into(clogo);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(ClubProfile.this, "loading", Toast.LENGTH_SHORT).show();
                     l_type = dataSnapshot.child("catagory").getValue().toString();
                    Toast.makeText(ClubProfile.this, l_type, Toast.LENGTH_SHORT).show();
                    get_leader_contact = dataSnapshot.child("leaderemail").getValue().toString();
                    leadercontact.setText(get_leader_contact);
                    if (l_type.equals("Clubleader")) {
                        pencil.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(ClubProfile.this, "success", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

     databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                   s_name = dataSnapshot.child("name").getValue().toString();
                    s_collage = dataSnapshot.child("collage").getValue().toString();
                    s_branch = dataSnapshot.child("branch").getValue().toString();
                    s_semester = dataSnapshot.child("semester").getValue().toString();
                    s_pic = dataSnapshot.child("imageurl").getValue().toString();
                    s_contact = dataSnapshot.child("contact").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
  }

    private void JoinClub()
    {
        member_map = new HashMap<>();
        member_map.put("name", s_name);
        member_map.put("scollage", s_collage);
        member_map.put("sbranch", s_branch);
        member_map.put("ssemester", s_semester);
        member_map.put("spic", s_pic);
        member_map.put("scontact", s_contact);
        databaseReference3.updateChildren(member_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ClubProfile.this, "TaskOne Completed", Toast.LENGTH_SHORT).show();
                    databaseReference2.updateChildren(member_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ClubProfile.this, "task 2 completed CLUB JOINED SUCCESSFULL", Toast.LENGTH_SHORT).show();
                                leave_club.setVisibility(View.VISIBLE);
                                leave_club.setEnabled(true);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        long s = dataSnapshot.getChildrenCount();

                                        String st=String.valueOf(s);
                                        dataSnapshot.getRef().child("cmembers").setValue(st);
                                        dataSnapshot.getRef().child("clunjoined").setValue("true");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            } else {
                                Toast.makeText(ClubProfile.this, "internal error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ClubProfile.this, "external error", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
