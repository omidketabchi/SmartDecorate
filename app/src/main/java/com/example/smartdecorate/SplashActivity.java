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
import com.example.smartdecorate.Fragment.FragmentLogin;
import com.example.smartdecorate.Fragment.FragmentMainPage;
import com.example.smartdecorate.Model.CategoryModel;

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
    DeviceDataBase dataBase;

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

                getCategoryFromServer();

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);

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

        dataBase = new DeviceDataBase(SplashActivity.this, DeviceType.NOTHING);

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

    private void getCategoryFromServer() {
        String url = "https://api.myjson.com/bins/dkbg4";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {

                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                long id = dataBase.insertCategoryInfo(jsonObject.getString("id"),
                                        jsonObject.getString("title"));

                                Toast.makeText(SplashActivity.this, "" + id, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
