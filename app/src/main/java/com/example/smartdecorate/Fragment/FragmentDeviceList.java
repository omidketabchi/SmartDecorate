package com.example.smartdecorate.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.Adapter.DeviceInfoAdapter;
import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentDeviceList extends Fragment {

    View view;
    ImageView imgBack;
    TextView txtTitle;
    RecyclerView recyclerView;
    List<DeviceInfoModel> models;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_device_list, container, false);

            setupViews();

            fillList();
        }

        return view;
    }

    private void setupViews() {

        models = new ArrayList<>();

        imgBack = (ImageView) view.findViewById(R.id.img_fragmentDeviceList_back);
        txtTitle = (TextView) view.findViewById(R.id.txt_fragmentDeviceList_title);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragmentDeviceList_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txtTitle.setText("لیست دستگا ها");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(FragmentDeviceList.this);
                transaction.commit();
            }
        });
    }

    private void fillList() {

        DeviceDataBase dataBase = new DeviceDataBase(getContext());

        Cursor cursor = dataBase.getDeviceInfo();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            DeviceInfoModel model = new DeviceInfoModel();

            model.setId(String.valueOf(cursor.getInt(0)));
            model.setDeviceName(cursor.getString(1));
            model.setDeviceIp(cursor.getString(2));

            models.add(model);
        }

        recyclerView.setAdapter(new DeviceInfoAdapter(getContext(), models));
    }
}