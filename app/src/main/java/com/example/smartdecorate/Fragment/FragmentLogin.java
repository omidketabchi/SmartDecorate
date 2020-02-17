package com.example.smartdecorate.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.LoginFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartdecorate.Model.LoginModel;
import com.example.smartdecorate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentLogin extends Fragment {

    View view;
    EditText edtPhone;
    Button btnRegister;
    ImageView imgLeftArrow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_login, container, false);

            setupViews();
        }

        return view;
    }

    private void setupViews() {

        ((AppCompatActivity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        edtPhone = (EditText) view.findViewById(R.id.edt_fragmentLogin_phone);
        btnRegister = (Button) view.findViewById(R.id.btn_fragmentLogin_register);
        imgLeftArrow = (ImageView) view.findViewById(R.id.img_fragmentLogin_leftArrow);

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 11) {
                    btnRegister.setClickable(true);
                    btnRegister.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_style));
                } else {
                    btnRegister.setClickable(false);
                    btnRegister.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.btn_disable_style));
                }
            }
        });

        imgLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(FragmentLogin.this);
                transaction.commit();

                ((AppCompatActivity) getContext()).finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {

        String url = "https://api.myjson.com/bins/xb3pw";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (parseLoginResponse(response)) {

                    Bundle bundle = new Bundle();
                    bundle.putString("phone", edtPhone.getText().toString());

                    FragmentManager fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    FragmentVerificationCode fragmentVerificationCode = new FragmentVerificationCode();
                    fragmentVerificationCode.setArguments(bundle);

                    transaction.add(R.id.frm_splash_frame, fragmentVerificationCode);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("phone", edtPhone.getText().toString());

                return params;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    private boolean parseLoginResponse(JSONObject response) {

        try {

            String errorCode = response.getString("errorCode");
            String errorDescription = response.getString("errorDescription");

            if (!errorCode.equals("000")) {

                Toast.makeText(getContext(), errorDescription + "[ " + errorCode + " ]", Toast.LENGTH_SHORT).show();

                return false;
            }

        } catch (JSONException e) {

            Toast.makeText(getContext(), "Parse Error/" + e.getMessage(), Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }

        return true;
    }
}
