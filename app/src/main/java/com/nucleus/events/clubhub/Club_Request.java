package com.nucleus.events.clubhub;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nucleus.events.clubhub.Model.Club_request_model;
import com.nucleus.events.clubhub.adapters.Club_requestAdapter;

import java.util.ArrayList;
import java.util.List;

public class Club_Request extends AppCompatActivity {

    RecyclerView club_req;
    Club_requestAdapter clubRequestAdapter;
    DatabaseReference databaseReference, databaseReference1;
    Club_request_model clubRequestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club__request);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Clubs");

        final List<Club_request_model> list = new ArrayList<>();

        club_req = findViewById(R.id.club_request_recycler_view);
        club_req.setHasFixedSize(true);
        club_req.setLayoutManager(new LinearLayoutManager(Club_Request.this));


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Snapshot) {

                for (DataSnapshot dataSnapshot : Snapshot.getChildren()) {
                    clubRequestModel = dataSnapshot.getValue(Club_request_model.class);
                    list.add(clubRequestModel);


                }

                clubRequestAdapter = new Club_requestAdapter(Club_Request.this, list);


//                final LayoutAnimationController controller =
//                        AnimationUtils.loadLayoutAnimation(Club_Request.this,R.anim.right_to_left);
//
//                club_req.setLayoutAnimation(controller);
                club_req.setAdapter(clubRequestAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
