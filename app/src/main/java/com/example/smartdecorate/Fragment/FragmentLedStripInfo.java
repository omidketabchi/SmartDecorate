package com.example.smartdecorate.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.R;

public class FragmentLedStripInfo extends Fragment {

    View view;
    DeviceInfoModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_led_strip_info, container, false);

            setupViews();
        }

        return view;
    }

    private void setupViews() {
        model = getArguments().getParcelable("model");
    }
}
