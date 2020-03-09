package com.example.smartdecorate.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartdecorate.Class.DateTime;
import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.ENUM.DeviceType;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.Model.WaterValveModel;
import com.example.smartdecorate.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentValveInfo extends Fragment {

    View view;
    ImageView imgBack;
    TextView txtValveName;
    TextView txtTitle;
    TextView txtDateTime;
    CardView onOff;
    SwitchCompat switchOnOff;
    Button btnFrom;
    Button btnUntil;
    Button btnSave;
    AppCompatSeekBar seekBar;
    TextView txtIntensityWaterFlow;
    CardView dateTimeSetting;
    DeviceDataBase dataBase;
    AppCompatSpinner spinner;
    List<String> periodList;

    String date = "";
    String time = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_valve, container, false);

            setupView();
        }

        return view;
    }

    private void setupView() {

        periodList = new ArrayList<>();

        dataBase = new DeviceDataBase(getContext(), DeviceType.NOTHING);

        imgBack = (ImageView) view.findViewById(R.id.img_main_back);
        txtTitle = (TextView) view.findViewById(R.id.txt_main_title);
        txtValveName = (TextView) view.findViewById(R.id.txt_fragmentValve_valveName);
        txtDateTime = (TextView) view.findViewById(R.id.txt_fragmentValve_finalTime);
        onOff = (CardView) view.findViewById(R.id.cv_fragmentValve_valve);
        switchOnOff = (SwitchCompat) view.findViewById(R.id.swh_fragmentValve_status);
        btnFrom = (Button) view.findViewById(R.id.btn_fragmentValve_from);
        btnUntil = (Button) view.findViewById(R.id.btn_fragmentValve_until);
        seekBar = (AppCompatSeekBar) view.findViewById(R.id.skb_fragmentLedValve_intensityWaterFlow);
        txtIntensityWaterFlow = (TextView) view.findViewById(R.id.txt_fragmentValve_value);
        dateTimeSetting = (CardView) view.findViewById(R.id.cv_fragmentValve_setTime);
        btnSave = (Button) view.findViewById(R.id.btn_fragmentValve_save);
        spinner = (AppCompatSpinner) view.findViewById(R.id.spn_fragmentValve_period);

        periodList.add("یک روز در هفته");
        periodList.add("دو روز در هفته");
        periodList.add("سه روز در هفته");
        periodList.add("جهار روز در هفته");
        periodList.add("پنج روز در هفته");
        periodList.add("شش روز در هفته");
        periodList.add("همه روزه");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.spinner_item, periodList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setHapticFeedbackEnabled(true);

        final DeviceInfoModel model = getArguments().getParcelable("model");

        Cursor cursor = dataBase.getWaterValveInfo(Integer.parseInt(model.getId()));

        cursor.moveToFirst();

        WaterValveModel waterValveModel = new WaterValveModel();

        waterValveModel.setId(cursor.getInt(0));
        waterValveModel.setActiveNow(cursor.getInt(1));
        waterValveModel.setFrom(cursor.getString(2));
        waterValveModel.setUntil(cursor.getString(3));
        waterValveModel.setIntensity(cursor.getInt(4));

        txtTitle.setText(model.getDeviceType());
        txtValveName.setText(model.getDeviceName());

        switchOnOff.setChecked((waterValveModel.getActiveNow() == 1) ? true : false);
        btnFrom.setText(waterValveModel.getFrom());
        btnUntil.setText(waterValveModel.getUntil());
        seekBar.setProgress(waterValveModel.getIntensity());
        txtIntensityWaterFlow.setText(waterValveModel.getIntensity() + "");

        if (switchOnOff.isChecked()) {
            dateTimeSetting.setVisibility(View.GONE);
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(FragmentValveInfo.this);
                transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                transaction.commit();
            }
        });

        switchOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchOnOff.setChecked(!switchOnOff.isChecked());
                Toast.makeText(getContext(), switchOnOff.isChecked() + "", Toast.LENGTH_SHORT).show();

                if (switchOnOff.isChecked()) {
                    disableDateTimeSetting();
                } else {
                    enableDateTimeSetting();
                }
            }
        });

        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switchOnOff.setChecked(!switchOnOff.isChecked());

                Toast.makeText(getContext(), switchOnOff.isChecked() + "", Toast.LENGTH_SHORT).show();

                if (switchOnOff.isChecked()) {
                    disableDateTimeSetting();
                } else {
                    enableDateTimeSetting();
                }
            }
        });

        btnFrom.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                showDateTimeDialog(btnFrom, false);
            }
        });

        btnUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(btnUntil, true);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtIntensityWaterFlow.setText(progress + "");

                Toast.makeText(getContext(), "" + progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //txtIntensityWaterFlow.setText(progress + "");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            long id;

            @Override
            public void onClick(View v) {

                if (!dataBase.isExistThisId(DeviceType.WATER_VALVE, Integer.parseInt(model.getId()))) {
                    id = dataBase.insertWaterValveInfo(
                            Integer.parseInt(model.getId()),
                            (switchOnOff.isChecked()) ? 1 : 0,
                            btnFrom.getText().toString(),
                            btnUntil.getText().toString(),
                            spinner.getSelectedItem().toString(),
                            seekBar.getProgress());
                } else {
                    id = dataBase.updateWaterValveInfo(
                            Integer.parseInt(model.getId()),
                            (switchOnOff.isChecked()) ? 1 : 0,
                            btnFrom.getText().toString(),
                            btnUntil.getText().toString(),
                            spinner.getSelectedItem().toString(),
                            seekBar.getProgress()
                    );
                }

                Toast.makeText(getContext(), "" + id, Toast.LENGTH_SHORT).show();

                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(FragmentValveInfo.this);
                transaction.setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation);
                transaction.commit();
            }
        });
    }

    private void showDateTimeDialog(final Button btnDateTime, final boolean showDiff) {

        date = "";
        time = "";

        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date = String.format("%04d", year) + "/" +
                                String.format("%02d", month + 1) + "/" +
                                String.format("%02d", dayOfMonth);

                        if (date.length() > 0) {
                            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    time = String.format("%02d", hourOfDay) + ":"
                                            + String.format("%02d", minute);

                                    btnDateTime.setText(date + "\n" + time);

                                    if (showDiff) {

                                        DateTime dateTime = new DateTime().differenceBetweenTwoDateTime(btnFrom.getText().toString(), btnUntil.getText().toString());

                                        String strDay = (dateTime.getDay() > 0) ? dateTime.getDay() + " روز و " : "";
                                        String strHour = (dateTime.getHour() > 0) ? dateTime.getHour() + " ساعت و " : "";
                                        String strMinute = (dateTime.getMinute() > 0) ? dateTime.getMinute() + " دقیقه " : "";

                                        txtDateTime.setText(strDay + strHour + strMinute);
                                    }
                                }
                            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);

                            timePickerDialog.show();
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void disableDateTimeSetting() {

        dateTimeSetting.setVisibility(View.GONE);
    }

    private void enableDateTimeSetting() {

        dateTimeSetting.setVisibility(View.VISIBLE);
    }
}
