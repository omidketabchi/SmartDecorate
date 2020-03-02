package com.example.smartdecorate.Fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartdecorate.Adapter.LedModeAdapter;
import com.example.smartdecorate.Connection.Connection;
import com.example.smartdecorate.DataBase.DeviceDataBase;
import com.example.smartdecorate.ENUM.DeviceType;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.Model.LedDeviceInfoModel;
import com.example.smartdecorate.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class FragmentLedStripInfo extends Fragment {

    View view;
    ImageView imgBack;
    TextView imgColor;
    TextView txtEffect;
    CardView moreEffectParent;
    CardView selectColorParent;
    RecyclerView recyclerView;
    AppCompatSeekBar speed;
    AppCompatSeekBar brightness;
    Toolbar toolbar;
    TextView txtDeviceName;
    DeviceInfoModel model;
    List<String> modeList;
    LedDeviceInfoModel ledDeviceInfoModel;
    DeviceDataBase dataBase;
    Connection connection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_led_strip_info, container, false);

            setupViews();

            fillList();

            LedModeAdapter ledModeAdapter = new LedModeAdapter(getContext(), modeList);

            recyclerView.setAdapter(ledModeAdapter);

            ledModeAdapter.setOnEffectClickListener(new LedModeAdapter.OnEffectClickListener() {
                @Override
                public void OnEffectClick(String effect, int position) {

                    txtEffect.setText(effect);

                    long id = dataBase.updateLedDeviceInfo(ledDeviceInfoModel.getId(),
                            ledDeviceInfoModel.getColor(), txtEffect.getText().toString(),
                            speed.getProgress(),
                            brightness.getProgress());

                    Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();

                    connection.sendParamToServer(model.getDeviceIp(),
                            Color.red(ledDeviceInfoModel.getColor()),
                            Color.green(ledDeviceInfoModel.getColor()),
                            Color.blue(ledDeviceInfoModel.getColor()),
                            Color.alpha(ledDeviceInfoModel.getColor()),
                            position,
                            speed.getProgress(),
                            brightness.getProgress());
                }
            });
        }

        return view;
    }

    private void setupViews() {

        connection = new Connection(getContext());
        dataBase = new DeviceDataBase(getContext(), DeviceType.LED_STRIP);

        model = getArguments().getParcelable("model");
        modeList = new ArrayList<>();

        getLedDeviceInfo(model.getId());

        toolbar = (androidx.appcompat.widget.Toolbar) view.findViewById(R.id.tlb_main_toolbar);
        imgBack = (ImageView) view.findViewById(R.id.img_main_back);
        imgColor = (TextView) view.findViewById(R.id.img_fragmentLedStrip_selectColor);
        speed = (AppCompatSeekBar) view.findViewById(R.id.skb_fragmentLedStrip_speed);
        brightness = (AppCompatSeekBar) view.findViewById(R.id.skb_fragmentLedStrip_brightness);
        selectColorParent = (CardView) view.findViewById(R.id.cv_fragmentLedStrip_selectColor);
        moreEffectParent = (CardView) view.findViewById(R.id.cv_fragmentLedStrip_moreEfect);
        txtDeviceName = (TextView) view.findViewById(R.id.txt_main_title);
        txtEffect = (TextView) view.findViewById(R.id.txt_fragmentLedStrip_effectValue);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragmentLedStrip_selectEfect);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txtDeviceName.setText(model.getDeviceName());
        txtEffect.setText(ledDeviceInfoModel.getEffect());
        speed.setProgress(ledDeviceInfoModel.getSpeed());
        brightness.setProgress(ledDeviceInfoModel.getBrightness());

        setCircleColor(ledDeviceInfoModel.getColor());

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(FragmentLedStripInfo.this);
                transaction.commit();
            }
        });

        imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("انتخاب رنگ")
                        .initialColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                        .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                        .density(10)
                        .showBorder(true)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                                setCircleColor(selectedColor);

                                connection.sendParamToServer(model.getDeviceIp(), Color.red(selectedColor),
                                        Color.green(selectedColor),
                                        Color.blue(selectedColor),
                                        Color.alpha(selectedColor),
                                        getEffectPosition(txtEffect.getText().toString()),
                                        speed.getProgress(),
                                        brightness.getProgress());
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {

                                setCircleColor(selectedColor);

                                long id = dataBase.updateLedDeviceInfo(ledDeviceInfoModel.getId(),
                                        selectedColor, txtEffect.getText().toString(),
                                        speed.getProgress(),
                                        brightness.getProgress());

                                Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();

            }
        });


        speed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                connection.sendParamToServer(model.getDeviceIp(),
                        Color.red(ledDeviceInfoModel.getColor()),
                        Color.green(ledDeviceInfoModel.getColor()),
                        Color.blue(ledDeviceInfoModel.getColor()),
                        Color.alpha(ledDeviceInfoModel.getColor()),
                        getEffectPosition(txtEffect.getText().toString()),
                        speed.getProgress(),
                        brightness.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                long id = dataBase.updateLedDeviceInfo(ledDeviceInfoModel.getId(),
                        ledDeviceInfoModel.getColor(), txtEffect.getText().toString(),
                        speed.getProgress(),
                        brightness.getProgress());

                Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();
            }
        });

        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                connection.sendParamToServer(model.getDeviceIp(),
                        Color.red(ledDeviceInfoModel.getColor()),
                        Color.green(ledDeviceInfoModel.getColor()),
                        Color.blue(ledDeviceInfoModel.getColor()),
                        Color.alpha(ledDeviceInfoModel.getColor()),
                        getEffectPosition(txtEffect.getText().toString()),
                        speed.getProgress(),
                        brightness.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                long id = dataBase.updateLedDeviceInfo(ledDeviceInfoModel.getId(),
                        ledDeviceInfoModel.getColor(), txtEffect.getText().toString(),
                        speed.getProgress(),
                        brightness.getProgress());

                Toast.makeText(getContext(), id + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fillList() {

        modeList.add("FX_MODE_STATIC");
        modeList.add("FX_MODE_BLINK");
        modeList.add("FX_MODE_BREATH");
        modeList.add("FX_MODE_COLOR_WIPE");
        modeList.add("FX_MODE_COLOR_WIPE_INV");
        modeList.add("FX_MODE_COLOR_WIPE_REV");
        modeList.add("FX_MODE_COLOR_WIPE_REV_INV");
        modeList.add("FX_MODE_COLOR_WIPE_RANDOM");
        modeList.add("FX_MODE_RANDOM_COLOR");
        modeList.add("FX_MODE_SINGLE_DYNAMIC");
        modeList.add("FX_MODE_MULTI_DYNAMIC");
        modeList.add("FX_MODE_RAINBOW");
        modeList.add("FX_MODE_RAINBOW_CYCLE");
        modeList.add("FX_MODE_SCAN");
        modeList.add("FX_MODE_DUAL_SCAN");
        modeList.add("FX_MODE_FADE");
        modeList.add("FX_MODE_THEATER_CHASE");
        modeList.add("FX_MODE_THEATER_CHASE_RAINBOW");
        modeList.add("FX_MODE_RUNNING_LIGHTS");
        modeList.add("FX_MODE_TWINKLE");
        modeList.add("FX_MODE_TWINKLE_RANDOM");
        modeList.add("FX_MODE_TWINKLE_FADE");
        modeList.add("FX_MODE_TWINKLE_FADE_RANDOM");
        modeList.add("FX_MODE_SPARKLE");
        modeList.add("FX_MODE_FLASH_SPARKLE");
        modeList.add("FX_MODE_HYPER_SPARKLE");
        modeList.add("FX_MODE_STROBE");
        modeList.add("FX_MODE_STROBE_RAINBOW");
        modeList.add("FX_MODE_MULTI_STROBE");
        modeList.add("FX_MODE_BLINK_RAINBOW");
        modeList.add("FX_MODE_CHASE_WHITE");
        modeList.add("FX_MODE_CHASE_COLOR");
        modeList.add("FX_MODE_CHASE_RANDOM");
        modeList.add("FX_MODE_CHASE_RAINBOW");
        modeList.add("FX_MODE_CHASE_FLASH");
        modeList.add("FX_MODE_CHASE_FLASH_RANDOM");
        modeList.add("FX_MODE_CHASE_RAINBOW_WHITE");
        modeList.add("FX_MODE_CHASE_BLACKOUT");
        modeList.add("FX_MODE_CHASE_BLACKOUT_RAINBOW");
        modeList.add("FX_MODE_COLOR_SWEEP_RANDOM");
        modeList.add("FX_MODE_RUNNING_COLOR");
        modeList.add("FX_MODE_RUNNING_RED_BLUE");
        modeList.add("FX_MODE_RUNNING_RANDOM");
        modeList.add("FX_MODE_LARSON_SCANNER");
        modeList.add("FX_MODE_COMET");
        modeList.add("FX_MODE_FIREWORKS");
        modeList.add("FX_MODE_FIREWORKS_RANDOM");
        modeList.add("FX_MODE_MERRY_CHRISTMAS");
        modeList.add("FX_MODE_FIRE_FLICKER");
        modeList.add("FX_MODE_FIRE_FLICKER_SOFT");
        modeList.add("FX_MODE_FIRE_FLICKER_INTENSE");
        modeList.add("FX_MODE_CIRCUS_COMBUSTUS");
        modeList.add("FX_MODE_HALLOWEEN");
        modeList.add("FX_MODE_BICOLOR_CHASE");
        modeList.add("FX_MODE_TRICOLOR_CHASE");
        modeList.add("FX_MODE_ICU");
        modeList.add("FX_MODE_CUSTOM");
        modeList.add("FX_MODE_CUSTOM_0");
        modeList.add("FX_MODE_CUSTOM_1");
        modeList.add("FX_MODE_CUSTOM_2");
        modeList.add("FX_MODE_CUSTOM_3");
    }

    private void getLedDeviceInfo(String deviceId) {

        ledDeviceInfoModel = new LedDeviceInfoModel();

        Cursor cursor = dataBase.getOneLedDeviceInfo(deviceId);

        cursor.moveToFirst();

        ledDeviceInfoModel.setId(cursor.getInt(0));
        ledDeviceInfoModel.setColor(cursor.getInt(1));
        ledDeviceInfoModel.setEffect(cursor.getString(2));
        ledDeviceInfoModel.setSpeed(cursor.getInt(3));
        ledDeviceInfoModel.setBrightness(cursor.getInt(4));
    }

    private void setCircleColor(int selectedColor) {

        int color = Color.parseColor("#" + Integer.toHexString(selectedColor));

        Drawable background = imgColor.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) background;
        gradientDrawable.setColor(color);

        recyclerView.setBackgroundColor(color);
        moreEffectParent.setBackgroundColor(color);
        selectColorParent.setBackgroundColor(color);
        toolbar.setBackgroundColor(color);

        ledDeviceInfoModel.setColor(selectedColor);
    }

    private int getEffectPosition(String effect) {

        for (int i = 0; i < modeList.size(); i++) {

            if (modeList.get(i).equals(effect)) {
                return i;
            }
        }

        // first effect is default.
        return 0;
    }
}
