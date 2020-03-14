package com.example.smartdecorate.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


        firstDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbBothOfThem.isChecked()) {
                    rdbBothOfThem.setChecked(false);
                }

                rdbFirstDoor.setChecked(true);
            }
        });

        rdbFirstDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rdbBothOfThem.isChecked()) {
                    rdbBothOfThem.setChecked(false);
                }

                rdbFirstDoor.setChecked(true);
            }
        });

        bothOfThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbFirstDoor.isChecked()) {
                    rdbFirstDoor.setChecked(false);
                }

                rdbBothOfThem.setChecked(true);
            }
        });

        rdbBothOfThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rdbFirstDoor.isChecked()) {
                    rdbFirstDoor.setChecked(false);
                }

                rdbBothOfThem.setChecked(true);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeviceDataBase dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

                long id = dataBase.updateParkingInfo(Integer.parseInt(model.getId()),
                        (rdbFirstDoor.isChecked()) ? 1 : 0,
                        (rdbBothOfThem.isChecked()) ? 1 : 0);

                dismiss();

                Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
