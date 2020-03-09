package com.example.smartdecorate.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.smartdecorate.ENUM.DeviceType;
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
    FragmentManager fragmentManager;

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

        fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

        imgBack = (ImageView) view.findViewById(R.id.img_main_back);
        txtTitle = (TextView) view.findViewById(R.id.txt_main_title);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragmentDeviceList_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txtTitle.setText("لیست دستگا ها");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                transaction.remove(FragmentDeviceList.this);
                transaction.commit();
            }
        });
    }

    private void fillList() {

        DeviceDataBase dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

        Cursor cursor = dataBase.getDeviceInfo();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            DeviceInfoModel model = new DeviceInfoModel();

            model.setId(String.valueOf(cursor.getInt(0)));
            model.setDeviceName(cursor.getString(1));
            model.setDeviceIp(cursor.getString(2));
            model.setDeviceType(cursor.getString(3));

            models.add(model);
        }

        DeviceInfoAdapter adapter = new DeviceInfoAdapter(getContext(), models);
        recyclerView.setAdapter(adapter);

        adapter.setOnDeviceItemClick(new DeviceInfoAdapter.OnDeviceItemClick() {
            @Override
            public void deviceItemClick(DeviceInfoModel model) {

                if (model.getDeviceType().equals(getString(R.string.str_device_name_led_stripe))) {

                    Toast.makeText(getContext(), model.getDeviceType(), Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("model", model);
                    FragmentLedStripInfo fragmentLedStripInfo = new FragmentLedStripInfo();
                    fragmentLedStripInfo.setArguments(bundle);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frm_splash_frame, fragmentLedStripInfo);
                    transaction.addToBackStack(null);
                    transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                    transaction.commit();

                } else if (model.getDeviceType().equals(getString(R.string.str_device_name_water_valve))) {

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("model", model);
                    FragmentValveInfo fragmentValveInfo = new FragmentValveInfo();
                    fragmentValveInfo.setArguments(bundle);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frm_splash_frame, fragmentValveInfo);
                    transaction.addToBackStack(null);
                    transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), model.getDeviceType(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}