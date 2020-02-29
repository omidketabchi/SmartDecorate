package com.example.smartdecorate.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.Adapter.LedStripAdapter;
import com.example.smartdecorate.Adapter.LightBulbAdapter;
import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.ENUM.DeviceType;
import com.example.smartdecorate.Model.CategoryModel;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.Model.LedDeviceInfoModel;
import com.example.smartdecorate.Model.LightBulbModel;
import com.example.smartdecorate.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentCategory extends Fragment {

    View view;
    TextView txtTitle;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    List<CategoryModel> categoryModels;
    TextView txtList1;
    TextView txtList2;
    List<DeviceInfoModel> deviceInfoModels1;
    List<DeviceInfoModel> deviceInfoModels2;
    List<LightBulbModel> lightBulbModels;
    List<LedDeviceInfoModel> ledDeviceInfoModels;

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
    }

    private void setupViews() {

        deviceInfoModels1 = new ArrayList<>();
        deviceInfoModels2 = new ArrayList<>();
        categoryModels = new ArrayList<>();

        txtTitle = (TextView) view.findViewById(R.id.txt_main_title);
        txtList1 = (TextView) view.findViewById(R.id.txt_fragmentCategory_list1);
        txtList2 = (TextView) view.findViewById(R.id.txt_fragmentCategory_list2);

        recyclerView1 = (RecyclerView) view.findViewById(R.id.rv_fragmentCategory_list1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView2 = (RecyclerView) view.findViewById(R.id.rv_fragmentCategory_list2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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
            ledDeviceInfoModel.setMoreEffect(cursor.getString(7));


            deviceInfoModels2.add(deviceInfoModel);
            ledDeviceInfoModels.add(ledDeviceInfoModel);
        }

        LedStripAdapter ledStripAdapter = new LedStripAdapter(getContext(), deviceInfoModels2, ledDeviceInfoModels);

        recyclerView2.setAdapter(ledStripAdapter);
    }
}
