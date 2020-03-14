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

import com.example.smartdecorate.Adapter.LedStripAdapter;
import com.example.smartdecorate.Adapter.LightBulbAdapter;
import com.example.smartdecorate.Adapter.ParkingDoorAdapter;
import com.example.smartdecorate.Adapter.WaterValveAdapter;
import com.example.smartdecorate.Connection.Connection;
import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.ENUM.DeviceType;
import com.example.smartdecorate.Model.CategoryModel;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.Model.LedDeviceInfoModel;
import com.example.smartdecorate.Model.LightBulbModel;
import com.example.smartdecorate.Model.ParkingDoorModel;
import com.example.smartdecorate.Model.WaterValveModel;
import com.example.smartdecorate.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentCategory extends Fragment {

    View view;
    TextView txtTitle;
    ImageView imgBack;

    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    RecyclerView recyclerView4;

    TextView txtList1;
    TextView txtList2;
    TextView txtList3;
    TextView txtList4;

    List<CategoryModel>   categoryModels;
    List<LightBulbModel>  lightBulbModels;
    List<DeviceInfoModel> deviceInfoModels1;
    List<DeviceInfoModel> deviceInfoModels2;
    List<DeviceInfoModel> deviceInfoModels3;
    List<DeviceInfoModel> deviceInfoModels4;
    List<WaterValveModel> waterValveModels;
    List<ParkingDoorModel> parkingDoorModels;
    List<LedDeviceInfoModel> ledDeviceInfoModels;

    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_category, container, false);

            setupViews();

            fillLists();

            setItemList();
        }

        return view;
    }

    private void manageCategoryItemList(CategoryModel DeviceCategoryModel) {

    }

    private void setItemList() {

        DeviceDataBase dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

        Cursor cursor = dataBase.getCategoryInfo();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            CategoryModel categoryModel = new CategoryModel();

            categoryModel.setId(cursor.getInt(0));
            categoryModel.setTitle(cursor.getString(1));

            categoryModels.add(categoryModel);
        }

        txtList1.setText(categoryModels.get(0).getTitle());
        txtList2.setText(categoryModels.get(1).getTitle());
        txtList3.setText(categoryModels.get(4).getTitle());
        txtList4.setText(categoryModels.get(2).getTitle());
    }

    private void setupViews() {

        deviceInfoModels1 = new ArrayList<>();
        deviceInfoModels2 = new ArrayList<>();
        deviceInfoModels3 = new ArrayList<>();
        deviceInfoModels4 = new ArrayList<>();

        categoryModels = new ArrayList<>();
        lightBulbModels = new ArrayList<>();
        ledDeviceInfoModels = new ArrayList<>();
        waterValveModels = new ArrayList<>();
        parkingDoorModels = new ArrayList<>();

        fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();

        txtTitle = (TextView) view.findViewById(R.id.txt_main_title);
        imgBack = (ImageView) view.findViewById(R.id.img_main_back);
        txtList1 = (TextView) view.findViewById(R.id.txt_fragmentCategory_list1);
        txtList2 = (TextView) view.findViewById(R.id.txt_fragmentCategory_list2);
        txtList3 = (TextView) view.findViewById(R.id.txt_fragmentCategory_list3);
        txtList4 = (TextView) view.findViewById(R.id.txt_fragmentCategory_list4);

        recyclerView1 = (RecyclerView) view.findViewById(R.id.rv_fragmentCategory_list1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView2 = (RecyclerView) view.findViewById(R.id.rv_fragmentCategory_list2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView3 = (RecyclerView) view.findViewById(R.id.rv_fragmentCategory_list3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView4 = (RecyclerView) view.findViewById(R.id.rv_fragmentCategory_list4);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        txtTitle.setText("دسته بندی ها");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction removeTransaction = fragmentManager.beginTransaction();
                removeTransaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                removeTransaction.remove(FragmentCategory.this);
                removeTransaction.commit();
            }
        });
    }

    private void fillLists() {

        DeviceDataBase dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

        Cursor cursor = dataBase.getLightBulbItems();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            DeviceInfoModel deviceInfoModel = new DeviceInfoModel();
            LightBulbModel lightBulbModel = new LightBulbModel();

            deviceInfoModel.setId(String.valueOf(cursor.getInt(0)));
            deviceInfoModel.setDeviceName(cursor.getString(1));
            deviceInfoModel.setDeviceIp(cursor.getString(2));
            deviceInfoModel.setDeviceType(cursor.getString(3));

            lightBulbModel.setId(cursor.getInt(4));
            lightBulbModel.setStatus(cursor.getInt(5));

            deviceInfoModels1.add(deviceInfoModel);
            lightBulbModels.add(lightBulbModel);
        }

        LightBulbAdapter lightBulbAdapter = new LightBulbAdapter(getContext(), deviceInfoModels1, lightBulbModels);

        recyclerView1.setAdapter(lightBulbAdapter);

        lightBulbAdapter.setOnItemListClickListener(new LightBulbAdapter.OnItemListClickListener() {
            @Override
            public void onItemClick(DeviceInfoModel deviceInfoModel, int status) {
                Connection connection = new Connection(getContext());
                connection.setLightBubbleOnOrOff(deviceInfoModel.getDeviceIp(), status);
            }
        });


        cursor = null;

        cursor = dataBase.getLedStripItems();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            DeviceInfoModel deviceInfoModel = new DeviceInfoModel();
            LedDeviceInfoModel ledDeviceInfoModel = new LedDeviceInfoModel();

            deviceInfoModel.setId(String.valueOf(cursor.getInt(0)));
            deviceInfoModel.setDeviceName(cursor.getString(1));
            deviceInfoModel.setDeviceIp(cursor.getString(2));
            deviceInfoModel.setDeviceType(cursor.getString(3));

            ledDeviceInfoModel.setId(cursor.getInt(4));
            ledDeviceInfoModel.setColor(cursor.getInt(5));
            ledDeviceInfoModel.setEffect(cursor.getString(6));
            ledDeviceInfoModel.setSpeed(cursor.getInt(7));
            ledDeviceInfoModel.setBrightness(cursor.getInt(8));

            deviceInfoModels2.add(deviceInfoModel);
            ledDeviceInfoModels.add(ledDeviceInfoModel);
        }

        LedStripAdapter ledStripAdapter = new LedStripAdapter(getContext(), deviceInfoModels2, ledDeviceInfoModels);

        recyclerView2.setAdapter(ledStripAdapter);

        ledStripAdapter.setOnItemListClickListener(new LedStripAdapter.OnItemListClickListener() {
            @Override
            public void onItemClick(DeviceInfoModel deviceInfoModel) {

                FragmentTransaction removeTransaction = fragmentManager.beginTransaction();
                removeTransaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                removeTransaction.remove(FragmentCategory.this);
                removeTransaction.commit();

                Bundle bundle = new Bundle();
                bundle.putParcelable("model", deviceInfoModel);
                FragmentLedStripInfo fragmentLedStripInfo = new FragmentLedStripInfo();
                fragmentLedStripInfo.setArguments(bundle);
                FragmentTransaction addTransaction = fragmentManager.beginTransaction();
                removeTransaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                addTransaction.replace(R.id.frm_splash_frame, fragmentLedStripInfo);
                addTransaction.addToBackStack(null);
                addTransaction.commit();
            }
        });

        cursor = null;

        cursor = dataBase.getWaterValveItems();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            DeviceInfoModel deviceInfoModel = new DeviceInfoModel();
            WaterValveModel waterValveModel = new WaterValveModel();

            deviceInfoModel.setId(String.valueOf(cursor.getInt(0)));
            deviceInfoModel.setDeviceName(cursor.getString(1));
            deviceInfoModel.setDeviceIp(cursor.getString(2));
            deviceInfoModel.setDeviceType(cursor.getString(3));

            waterValveModel.setId(cursor.getInt(4));
            waterValveModel.setActiveNow(cursor.getInt(5));
            waterValveModel.setFrom(cursor.getString(6));
            waterValveModel.setUntil(cursor.getString(7));
            waterValveModel.setIntensity(cursor.getInt(8));

            deviceInfoModels3.add(deviceInfoModel);
            waterValveModels.add(waterValveModel);
        }

        WaterValveAdapter waterValveAdapter = new WaterValveAdapter(getContext(), deviceInfoModels3, waterValveModels);

        recyclerView3.setAdapter(waterValveAdapter);

        waterValveAdapter.setOnItemListClickListener(new WaterValveAdapter.OnItemListClickListener() {
            @Override
            public void onItemClick(DeviceInfoModel deviceInfoModel, int status) {

                FragmentTransaction removeTransaction = fragmentManager.beginTransaction();
                removeTransaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                removeTransaction.remove(FragmentCategory.this);
                removeTransaction.commit();

                Bundle bundle = new Bundle();
                bundle.putParcelable("model", deviceInfoModel);
                FragmentValveInfo fragmentValveInfo = new FragmentValveInfo();
                fragmentValveInfo.setArguments(bundle);
                FragmentTransaction addTransaction = fragmentManager.beginTransaction();
                removeTransaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                addTransaction.replace(R.id.frm_splash_frame, fragmentValveInfo);
                addTransaction.addToBackStack(null);
                addTransaction.commit();
            }
        });

        cursor = null;

        cursor = dataBase.getWaterValveItems();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            DeviceInfoModel deviceInfoModel = new DeviceInfoModel();
            WaterValveModel waterValveModel = new WaterValveModel();

            deviceInfoModel.setId(String.valueOf(cursor.getInt(0)));
            deviceInfoModel.setDeviceName(cursor.getString(1));
            deviceInfoModel.setDeviceIp(cursor.getString(2));
            deviceInfoModel.setDeviceType(cursor.getString(3));

            waterValveModel.setId(cursor.getInt(4));
            waterValveModel.setActiveNow(cursor.getInt(5));
            waterValveModel.setFrom(cursor.getString(6));
            waterValveModel.setUntil(cursor.getString(7));
            waterValveModel.setIntensity(cursor.getInt(8));

            deviceInfoModels3.add(deviceInfoModel);
            waterValveModels.add(waterValveModel);
        }

        ParkingDoorAdapter parkingDoorAdapter = new ParkingDoorAdapter(getContext(), deviceInfoModels4, parkingDoorModels);

        recyclerView4.setAdapter(parkingDoorAdapter);

        parkingDoorAdapter.setOnItemListClickListener(new ParkingDoorAdapter.OnItemListClickListener() {
            @Override
            public void onItemClick(DeviceInfoModel deviceInfoModel) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("model", deviceInfoModel);
                FragmentParkingDoor fragmentParkingDoor = new FragmentParkingDoor();
                fragmentParkingDoor.setArguments(bundle);

                fragmentParkingDoor.show(((AppCompatActivity)getContext()).getSupportFragmentManager(), null);
            }
        });
    }
}
