package com.nucleus.events.clubhub;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.nucleus.events.clubhub.adapters.Fragment_PageAdapter;
import com.nucleus.events.clubhub.fragment.Clubs_fragment;
import com.nucleus.events.clubhub.fragment.Events_fragment;
import com.nucleus.events.clubhub.fragment.Main_Home_Fragment;

public class HomeActivity extends AppCompatActivity implements Main_Home_Fragment.OnFragmentInteractionListener, Clubs_fragment.OnFragmentInteractionListener, Events_fragment.OnFragmentInteractionListener {

    SpaceNavigationView spaceNavigationView;
    ImageView notification, create_club;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    //SliderView sliderView;
    RecyclerView gridrecyclerview;
    TabLayout tabLayout;

    //ab run kro...
    //ok
    // aj validate krna he

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        String cuid = firebaseAuth.getCurrentUser().getUid();
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_home_black_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.setting));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.myclub));
        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.faq));
        //spaceNavigationView.addSpaceItem(new SpaceItem("Add", R.drawable.ic_menu_manage));
        spaceNavigationView.setActiveCentreButtonBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        // ba slide ni ho ra he and fragemts ki activity me jo content he bo ni dekh ra he
        //ok.. great!!!
        // ok thnks now we can disconnect
        // if any problem occurs i will ask you
        //
       // sure good night...

//working fine

        //tab layout
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Clubs"));
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager;//maybe try once, it might work..
        // ok wait
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        final Fragment_PageAdapter pageAdapter = new Fragment_PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });






     //   sliderView = findViewById(R.id.imageSlider);
      //  sliderAdapterHome home = new sliderAdapterHome(this);
//    //    sliderView.setSliderAdapter(home);
     //   sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("students").child(cuid);
        gridrecyclerview = findViewById(R.id.grid_recyclerview);

//home activity
//        1.home activity :- this activity will show clubs ,societies , events etc.
//        3.center button :- create event activity on click.
//        2.my club:- show the club u created or joined .
//        4.setting: - user can edit details of profile , feeback , queries(help centre) , logout.
//        5.faq


        notification = (ImageView) findViewById(R.id.action_notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(HomeActivity.this, Student_login.class));


            }
        });
        create_club = (ImageView) findViewById(R.id.action_create_club);
        create_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CreateClub.class);
                startActivity(intent);


            }
        });


        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(HomeActivity.this, "onCentreButtonClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toast.makeText(HomeActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();

                switch (itemIndex) {

                    case 0:
                        startActivity(new Intent(HomeActivity.this,Main_Home_Fragment.class));
                    case 1:

                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this, MyClub.class));
                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity.this, Admin_Dashboard.class));
                        break;

                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(HomeActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
                switch (itemIndex) {

                    case 0:

                        startActivity(new Intent(HomeActivity.this,getClass()));
                    case 1:

                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(HomeActivity.this, MyClub.class));
                        break;
                    case 3:
                        startActivity(new Intent(HomeActivity.this, FAQ.class));
                        break;

                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        create_club.setVisibility(View.INVISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String cat = dataSnapshot.child("catagory").getValue().toString();
                    Toast.makeText(HomeActivity.this,cat, Toast.LENGTH_SHORT).show();
                    if (cat.equals("Clubleader") || cat.equals("Society")) {
                        create_club.setVisibility(View.VISIBLE);
                    } else if (cat.equals("Student")) {
                        create_club.setVisibility(View.INVISIBLE);
                        create_club.setEnabled(false);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
