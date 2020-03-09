package com.example.smartdecorate.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.ENUM.DeviceType;
import com.example.smartdecorate.R;

import java.util.ArrayList;

public class FragmentAddDevice extends Fragment {

    View view;
    EditText edtDeviceName;
    EditText edtDeviceIp;
    Button btnSave;
    AppCompatSpinner spinner;
    ArrayList<String> deviceList;
    private final int DEFAULT_COLOR;
    private final String DEFAULT_EFFECT;
    private final boolean DEFAULT_MORE_EFFECT;
    private final int DEFAULT_BULB_STATUS;
    private final int DEFAULT_SPEED;
    private final int DEFAULT_BRIGHTNESS;
    private final int DEFAULT_ACTIVE_NOW;
    private final String DEFAULT_DATE_TIME;
    private final int DEFAULT_INTENSITY;
    private final String DEFAULT_PERIOD;

    public FragmentAddDevice() {

//        int color = Color.parseColor("#" + Integer.toHexString(selectedColor));
        DEFAULT_COLOR = 13224393;//Color.parseColor(String.valueOf(getResources().getColor(R.color.colorBlack)));
        DEFAULT_EFFECT = "FX_MODE_STATIC";
        DEFAULT_MORE_EFFECT = false;
        DEFAULT_BULB_STATUS = 0;
        DEFAULT_SPEED = 0;
        DEFAULT_BRIGHTNESS = 0;

        DEFAULT_ACTIVE_NOW = 0;
//        DEFAULT_DATE_TIME = getString(R.string.str_default_date_time);
        DEFAULT_DATE_TIME = "تاریخ/زمان";
        DEFAULT_INTENSITY = 50;
        DEFAULT_PERIOD = "یک روز در هفته";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_add_device, container, false);

            setupViews();
        }

        return view;
    }

    private void setupViews() {

        deviceList = new ArrayList<>();

        edtDeviceName = (EditText) view.findViewById(R.id.edt_fragmentAddDevice_deviceName);
        edtDeviceIp = (EditText) view.findViewById(R.id.edt_fragmentAddDevice_deviceIp);
        btnSave = (Button) view.findViewById(R.id.btn_fragmentAddDevice_save);
        spinner = (AppCompatSpinner) view.findViewById(R.id.spn_fragmentAddDevice_deviceType);

        setSpinnerItems();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long id;
                DeviceDataBase dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

                String deviceName = edtDeviceName.getText().toString();
                String deviceIp = edtDeviceIp.getText().toString();
                String deviceType = spinner.getSelectedItem().toString();

                if (deviceName.isEmpty() || deviceIp.isEmpty()) {
                    Toast.makeText(getContext(), "فیلدهای خالی را پر کنید", Toast.LENGTH_SHORT).show();
                } else {
                    id = dataBase.insertDeviceInfo(deviceName, deviceIp, deviceType);

                    Toast.makeText(getContext(), "master:" + id, Toast.LENGTH_SHORT).show();

                    if (deviceType.equals(getString(R.string.str_device_name_lamp))) {
                        dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);
                        id = dataBase.insertLightBulbInfo(id, DEFAULT_BULB_STATUS);

                        Toast.makeText(getContext(), "detail:" + id, Toast.LENGTH_SHORT).show();
                    } else if (deviceType.equals(getString(R.string.str_device_name_led_stripe))) {
                        dataBase = new DeviceDataBase(getContext(), DeviceType.LED_STRIP);
                        id = dataBase.insertLedDeviceInfo(id, DEFAULT_COLOR, DEFAULT_EFFECT,
                                DEFAULT_SPEED, DEFAULT_BRIGHTNESS);

                        Toast.makeText(getContext(), "detail:" + id, Toast.LENGTH_SHORT).show();
                    } else if (deviceType.equals(getString(R.string.str_device_name_water_valve))) {
                        dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);
                        id = dataBase.insertWaterValveInfo((int) id, DEFAULT_ACTIVE_NOW, DEFAULT_DATE_TIME,
                                DEFAULT_DATE_TIME, DEFAULT_PERIOD, DEFAULT_INTENSITY);

                        Toast.makeText(getContext(), "detail:" + id, Toast.LENGTH_SHORT).show();
                    }

                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                    transaction.remove(FragmentAddDevice.this);
                    transaction.commit();
                }
            }
        });
    }

    private void setSpinnerItems() {

        DeviceDataBase dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

        Cursor cursor = dataBase.getCategoryInfo();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

            deviceList.add(cursor.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.spinner_item, deviceList);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(adapter);
        spinner.setHapticFeedbackEnabled(true);

    }
}
