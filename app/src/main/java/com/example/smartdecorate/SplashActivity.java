package com.example.smartdecorate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.smartdecorate.Fragment.FragmentLogin;
import com.example.smartdecorate.Fragment.FragmentMainPage;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    ImageView imgLogo;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        showAnimation();


        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final String phone = sharedPreferences.getString("phone", "");

        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                timer.purge();
                timer.cancel();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (!phone.isEmpty()) {
                    transaction.add(R.id.frm_splash_frame, new FragmentMainPage());
                } else {
                    transaction.add(R.id.frm_splash_frame, new FragmentLogin());
                }

                transaction.commit();
            }
        }, 3000, 1);
    }

    private void setupViews() {
        imgLogo = (ImageView) findViewById(R.id.img_splash_logo);
        frameLayout = (FrameLayout) findViewById(R.id.frm_splash_frame);
    }

    private void showAnimation() {

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);

        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        imgLogo.setAnimation(alphaAnimation);
    }
}
