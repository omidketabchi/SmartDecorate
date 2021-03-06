package com.example.smartdecorate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.ENUM.DeviceType;
import com.example.smartdecorate.Fragment.FragmentAddDevice;
import com.example.smartdecorate.Fragment.FragmentCategory;
import com.example.smartdecorate.Fragment.FragmentDeviceList;
import com.example.smartdecorate.Fragment.FragmentLedStripInfo;
import com.example.smartdecorate.Fragment.FragmentLogin;
import com.example.smartdecorate.Fragment.FragmentMainPage;
import com.example.smartdecorate.Model.CategoryModel;
import com.example.smartdecorate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    ImageView imgLogo;
    FrameLayout frameLayout;
    FragmentManager fragmentManager;

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

                fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);

                if (!phone.isEmpty()) {
                    transaction.replace(R.id.frm_splash_frame, new FragmentMainPage());
                } else {
                    transaction.replace(R.id.frm_splash_frame, new FragmentLogin());
                }

                transaction.commit();
//                finish();

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

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        Toast.makeText(this, "" + count, Toast.LENGTH_SHORT).show();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }


//        Fragment fragment = fragmentManager.findFragmentById(R.id.frm_splash_frame);
//
//        if (fragment instanceof FragmentMainPage) {
//            Toast.makeText(this, "mainpage", Toast.LENGTH_SHORT).show();
//
////            FragmentTransaction removeTransaction = fragmentManager.beginTransaction().remove().commit();
//
//        } else if (fragment instanceof FragmentDeviceList) {
//
//            Toast.makeText(this, "devicelist", Toast.LENGTH_SHORT).show();
////            fragmentManager.beginTransaction().replace(R.id.frm_splash_frame, fragment).commit();
//
//        } else if (fragment instanceof FragmentAddDevice) {
//            Toast.makeText(this, "adddevice", Toast.LENGTH_SHORT).show();
//        } else if (fragment instanceof FragmentCategory) {
//            Toast.makeText(this, "category", Toast.LENGTH_SHORT).show();
//        } else if (fragment instanceof FragmentLedStripInfo) {
//            Toast.makeText(this, "ledInfo", Toast.LENGTH_SHORT).show();
//        }
    }
}
