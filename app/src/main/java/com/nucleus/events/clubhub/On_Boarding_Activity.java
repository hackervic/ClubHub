package com.nucleus.events.clubhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class On_Boarding_Activity extends AppCompatActivity {

    private ViewPager ScreenPager;
    private IntroViewPagerAdapter introViewPagerAdapte;
    private TabLayout tableLayout;
    private int position;
    private Button nextbtn, btngetst ,skip;
    private Animation btnanimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on__boarding_);

        if (restoredPrefData()) {
            Intent mintent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mintent);
            finish();

        }


        tableLayout = findViewById(R.id.dots);
        nextbtn = findViewById(R.id.nextbutton);
        btngetst = findViewById(R.id.btn_getstarted);
        skip = findViewById(R.id.skipbtn);
        btnanimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.btn_animation);

//        getSupportActionBar().hide();
        ScreenPager = findViewById(R.id.viewpager1);

        final List<Screen_item> screen_items = new ArrayList<>();
        screen_items.add(new Screen_item("24*7 Consultancy", "Proving Consultancy at your fingertips get idea of how your dream home would look like , with a no of engineers and interior designers you get a variety of choices", R.mipmap.chat));
        screen_items.add(new Screen_item("Easy Repairs", "Facing some problems at your home or does it needs some renovation we provide you with highly qualified experienced Engineers, Trained Technicians, Electricians, Plumbers, Carpenters and a lot more.", R.mipmap.club));
        screen_items.add(new Screen_item("User Friendly", "Buildovo User Friendly interface enables you to access all what you need whether its about buying construction material or hireing a engineer or a contractor.", R.mipmap.paymentm));
        screen_items.add(new Screen_item("One Stop Solution", "Buildovo Provides you with all what you need to build your dream home- From basic plan to construction material, from fittings to interior at your fingertips.", R.mipmap.chat));

        introViewPagerAdapte = new IntroViewPagerAdapter(this, screen_items);
        ScreenPager.setAdapter(introViewPagerAdapte);

        tableLayout.setupWithViewPager(ScreenPager);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = ScreenPager.getCurrentItem();
                if (position < screen_items.size() - 1) {
                    position++;
                    ScreenPager.setCurrentItem(position);
                }
                if (position == screen_items.size()) {
                    //TODO :show the getstarted button and hide the indicator
                    LoadLastScreen();
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoadLastScreen();
                            }
        });

        tableLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == screen_items.size() - 1) {
                    LoadLastScreen();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btngetst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                SavePrefData();
                finish();
            }
        });


    }

    private boolean restoredPrefData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mypefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = sharedPreferences.getBoolean("isIntroOpend", false);
        return isIntroActivityOpenedBefore;
    }

    private void SavePrefData() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("mypefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isIntroOpend", true);
        editor.commit();

    }


    private void LoadLastScreen() {
        btngetst.setVisibility(View.VISIBLE);
        nextbtn.setVisibility(View.INVISIBLE);
        tableLayout.setVisibility(View.INVISIBLE);
        skip.setVisibility(View.INVISIBLE);
        btngetst.setAnimation(btnanimation);

    }


}
