package com.example.smartdecorate.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartdecorate.R;

public class FragmentVerificationCode extends Fragment {

    View view;
    ImageView imgBack;
    TextView txtDescription;
    EditText edtVerificationCode;
    Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_verification_code, container, false);

            setupViews();
        }

        return view;
    }

    private void setupViews() {

        final String phone = getArguments().getString("phone");

        txtDescription = (TextView) view.findViewById(R.id.txt_fragmentVerificationCode_description);
        edtVerificationCode = (EditText) view.findViewById(R.id.edt_fragmentVerificationCode_code);
        btnSubmit = (Button) view.findViewById(R.id.btn_fragmentVerificationCode_register);
        imgBack = (ImageView) view.findViewById(R.id.img_fragmentVerificationCode_leftArrow);

        txtDescription.setText("کد فعالسازی به شماره " + phone + " ارسال میشود.");

        edtVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() == 5) {
                    btnSubmit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_style));
                    btnSubmit.setClickable(true);
                } else {
                    btnSubmit.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_disable_style));
                    btnSubmit.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(FragmentVerificationCode.this);
                transaction.commit();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtVerificationCode.getText().toString().equals("12345")) {

                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("phone", phone);
                    editor.apply();

                    FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.frm_splash_frame, new FragmentMainPage());
                    transaction.commit();
                }
            }
        });
    }
}
