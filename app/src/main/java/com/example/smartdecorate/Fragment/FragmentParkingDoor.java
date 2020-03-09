package com.example.smartdecorate.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.ENUM.DeviceType;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.R;

public class FragmentParkingDoor extends DialogFragment {

    View view;
    CardView firstDoor;
    CardView bothOfThem;
    Button btnSave;
    AppCompatRadioButton rdbFirstDoor;
    AppCompatRadioButton rdbBothOfThem;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_parking_door, null);

        builder.setView(view);

        setupViews();

        return builder.create();
    }

    private void setupViews() {

        final DeviceInfoModel model = getArguments().getParcelable("model");

        firstDoor = (CardView) view.findViewById(R.id.cv_fragmentParkingDoor_firstDoor);
        bothOfThem = (CardView) view.findViewById(R.id.cv_fragmentParkingDoor_bothDoor);

        rdbFirstDoor = (AppCompatRadioButton) view.findViewById(R.id.rdb_fragmentParkingDoor_firstDoor);
        rdbBothOfThem = (AppCompatRadioButton) view.findViewById(R.id.rdb_fragmentParkingDoor_bothDoor);
        btnSave = (Button) view.findViewById(R.id.btn_fragmentParkingDoor_save);

        rdbFirstDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rdbFirstDoor.isChecked()) {
                    rdbBothOfThem.setChecked(false);
                }
            }
        });

        rdbBothOfThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rdbBothOfThem.isChecked()) {
                    rdbFirstDoor.setChecked(false);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeviceDataBase dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

                dataBase.insertParkingInfo(Integer.parseInt(model.getId()),
                        (rdbFirstDoor.isChecked()) ? 1 : 0,
                        (rdbBothOfThem.isChecked()) ? 1 : 0);

                dismiss();
            }
        });
    }
}
