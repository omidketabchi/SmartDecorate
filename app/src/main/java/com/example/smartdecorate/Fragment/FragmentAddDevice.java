package com.example.smartdecorate.Fragment;

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
import com.example.smartdecorate.R;

import java.util.ArrayList;

public class FragmentAddDevice extends Fragment {

    View view;
    EditText edtDeviceName;
    EditText edtDeviceIp;
    Button btnSave;
    AppCompatSpinner spinner;
    ArrayList<String> deviceList;

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

        deviceList.add(getString(R.string.str_device_name_lamp));
        deviceList.add(getString(R.string.str_device_name_led_stripe));
        deviceList.add(getString(R.string.str_device_name_parking_door));
        deviceList.add(getString(R.string.str_device_name_tv));
        deviceList.add(getString(R.string.str_device_name_refrigerator));
        deviceList.add(getString(R.string.str_device_name_fan));

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.spinner_item, deviceList);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        spinner.setAdapter(adapter);
        spinner.setHapticFeedbackEnabled(true);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeviceDataBase dataBase = new DeviceDataBase(getContext());

                String deviceName = edtDeviceName.getText().toString();
                String deviceIp = edtDeviceIp.getText().toString();
                String deviceType = spinner.getSelectedItem().toString();

                if (deviceName.isEmpty() || deviceIp.isEmpty()) {
                    Toast.makeText(getContext(), "فیلدهای خالی را پر کنید", Toast.LENGTH_SHORT).show();
                } else {
                    long id = dataBase.insertDeviceInfo(deviceName, deviceIp, deviceType);

                    Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();

                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                    transaction.remove(FragmentAddDevice.this);
                    transaction.commit();
                }
            }
        });
    }
}
