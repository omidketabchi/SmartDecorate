package com.example.smartdecorate.Fragment;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.Adapter.LedStripAdapter;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.R;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentLedStripInfo extends Fragment {

    View view;
    ImageView imgBack;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    AppCompatCheckBox checkBox;
    TextView txtDeviceName;
    DeviceInfoModel model;
    List<String> modeList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_led_strip_info, container, false);

            setupViews();

            fillList();

            recyclerView.setAdapter(new LedStripAdapter(getContext(), modeList));
        }

        return view;
    }

    private void setupViews() {

        model = getArguments().getParcelable("model");
        modeList = new ArrayList<>();

        imgBack = (ImageView) view.findViewById(R.id.img_fragmentLedStrip_back);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_fragmentLedStrip_selectColor);
        checkBox = (AppCompatCheckBox) view.findViewById(R.id.chb_fragmentLedStrip_selectFX);
        txtDeviceName = (TextView) view.findViewById(R.id.txt_fragmentLedStrip_stripName);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_fragmentLedStrip_selectEfect);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        txtDeviceName.setText(model.getDeviceName());

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(FragmentLedStripInfo.this);
                transaction.commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
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
                                //Toast.makeText(getContext(), "onColorSelected: 0x" + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                String hex = "#" + Integer.toHexString(selectedColor);
                                int a = Color.parseColor(hex);
//                                fab.setBackgroundColor(Color.parseColor(hex));
//                                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBlack)));
                                //fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.colorAccent)));
                                fab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

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
}
