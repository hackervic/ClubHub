package com.nucleus.events.clubhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.nucleus.events.clubhub.fragment.Clubs_fragment;

public class Admin_Dashboard extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar toolbar;
    FirebaseAuth firebaseAuth;
    String str;
    MaterialCardView admin_request, adminclub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        toolbar = (Toolbar) findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        firebaseAuth = FirebaseAuth.getInstance();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(Admin_Dashboard.this, Student_login.class));
            }
        });

        admin_request = findViewById(R.id.admin_request);
        admin_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Dashboard.this, Club_Request.class));
            }
        });
        adminclub = findViewById(R.id.admin_club);
        adminclub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin_Dashboard.this, Clubs_fragment.class));
            }
        });


    }
}
