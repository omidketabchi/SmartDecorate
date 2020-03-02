package com.example.smartdecorate.Connection;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Connection {

    Context context;
    private static final int TIMEOUT = 15000;

    public Connection(Context context) {
        this.context = context;
    }

    public void setLightBubbleOnOrOff(String ip, int status) {

        String url = "http://" + ip + "/lightBubble?" +
                "status=" + status;

        sendDataToConnectedDevice(url);
    }

    public void sendParamToServer(String ip, int red, int green, int blue, int alpha,
                                  final int position, int speed, int brightness) {

        final String url = "http://" + ip + "/ledStrip?" +
                "red=" + red +
                "&green=" + green +
                "&blue=" + blue +
                "&effect=" + position +
                "&speed=" + speed +
                "&brightness=" + brightness;

        sendDataToConnectedDevice(url);
    }

    private void sendDataToConnectedDevice(String url) {

        //Toast.makeText(context, url, Toast.LENGTH_LONG).show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
