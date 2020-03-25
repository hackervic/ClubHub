package com.nucleus.events.clubhub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_Activity extends Activity implements Animation.AnimationListener {

    ImageView logo1 , logo2;
    Animation anim , animation1;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        logo1 = findViewById(R.id.img1);
        logo2 = findViewById(R.id.img2);
        anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        anim.setAnimationListener(this);

        animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        animation1.setAnimationListener(this);


        logo1.setVisibility(View.VISIBLE);
        logo1.startAnimation(anim);
        logo2.startAnimation(animation1);


    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if(animation == anim)
        {

            handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(Splash_Activity.this,On_Boarding_Activity.class);
                    startActivity(intent);
                    finish();
                }
            },3000);

        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
