package com.example.smartdecorate.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartdecorate.Adapter.MainMenuAdapter;
import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.ENUM.DeviceType;
import com.example.smartdecorate.Model.MainMenuItemModel;
import com.example.smartdecorate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentMainPage extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<MainMenuItemModel> models;
    DeviceDataBase dataBase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_main_page, container, false);

            setupViews();

            getCategoryFromServer();

            getMenuItemList();
        }

        return view;
    }

    private void setupViews() {

        models = new ArrayList<>();

        dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

        recyclerView = view.findViewById(R.id.rv_fragmentMainPage_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getMenuItemList() {

        String url = "https://api.myjson.com/bins/11u5s4";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {

                            MainMenuItemModel model = new MainMenuItemModel();
                            try {

                                JSONObject jsonObject = response.getJSONObject(i);

                                model.setRightTitle(jsonObject.getString("rightTitle"));
                                model.setLeftTitle(jsonObject.getString("leftTitle"));
                                model.setLeftUrl(jsonObject.getString("leftUrl"));
                                model.setRighttUrl(jsonObject.getString("rightUrl"));

                                models.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        MainMenuAdapter mainMenuAdapter = new MainMenuAdapter(getContext(), models);
                        recyclerView.setAdapter(mainMenuAdapter);

                        mainMenuAdapter.setOnMenuItemClick(new MainMenuAdapter.OnMenuItemClick() {
                            @Override
                            public void onMenuItemClick(String title) {
                                doAction(title);
                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void doAction(String title) {

        if (title.equals(getString(R.string.str_add_new_device))) {

            FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
            transaction.add(R.id.frm_splash_frame, new FragmentAddDevice());
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (title.equals(getString(R.string.str_devices_list))) {

            FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
            transaction.add(R.id.frm_splash_frame, new FragmentDeviceList());
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (title.equals(getString(R.string.str_category))) {

            FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
            transaction.add(R.id.frm_splash_frame, new FragmentCategory());
            transaction.addToBackStack(null);
            transaction.commit();
        }
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

                                Toast.makeText(getContext(), "" + id, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}
