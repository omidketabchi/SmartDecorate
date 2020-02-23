package com.example.smartdecorate.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.R;

public class FragmentAddDevice extends Fragment {

    View view;
    EditText edtDeviceName;
    EditText edtDeviceIp;
    Button btnSave;

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

        edtDeviceName = (EditText) view.findViewById(R.id.edt_fragmentAddDevice_deviceName);
        edtDeviceIp = (EditText) view.findViewById(R.id.edt_fragmentAddDevice_deviceIp);
        btnSave = (Button) view.findViewById(R.id.btn_fragmentAddDevice_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeviceDataBase dataBase = new DeviceDataBase(getContext());

                long id = dataBase.insertDeviceInfo(edtDeviceName.getText().toString(),
                        edtDeviceIp.getText().toString());

                Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                transaction.remove(FragmentAddDevice.this);
                transaction.commit();
            }
        });
    }
}
