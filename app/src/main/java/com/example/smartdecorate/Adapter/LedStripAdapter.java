package com.example.smartdecorate.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.Model.CategoryModel;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.Model.LedDeviceInfoModel;
import com.example.smartdecorate.R;

import java.util.List;

public class LedStripAdapter extends RecyclerView.Adapter<LedStripAdapter.LedStripViewHolder> {

    Context context;
    List<DeviceInfoModel> deviceInfoModels;
    List<LedDeviceInfoModel> ledDeviceInfoModels;
    OnItemListClickListener onItemListClickListener;

    public LedStripAdapter(Context context, List<DeviceInfoModel> deviceInfoModels,
                           List<LedDeviceInfoModel> ledDeviceInfoModels) {

        this.context = context;
        this.deviceInfoModels = deviceInfoModels;
        this.ledDeviceInfoModels = ledDeviceInfoModels;
    }

    @NonNull
    @Override
    public LedStripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.led_strip_item, parent, false);

        return new LedStripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LedStripViewHolder holder, int position) {

        final DeviceInfoModel model = deviceInfoModels.get(position);

        int color = Color.parseColor("#" + Integer.toHexString(ledDeviceInfoModels.get(position).getColor()));

        Drawable background = holder.txtColor.getBackground();
        GradientDrawable gradientDrawable = (GradientDrawable) background;
        gradientDrawable.setColor(color);

        holder.txtDeviceName.setText(model.getDeviceName());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemListClickListener.onItemClick(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceInfoModels.size();
    }

    public class LedStripViewHolder extends RecyclerView.ViewHolder {

        TextView txtDeviceName;
        TextView txtColor;
        CardView parent;

        public LedStripViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDeviceName = (TextView) itemView.findViewById(R.id.txt_ledStripItem_deviceName);
            txtColor = (TextView) itemView.findViewById(R.id.txt_ledStripItem_color);

            parent = (CardView) itemView.findViewById(R.id.cv_ledStripItem_parent);
        }
    }

    public interface OnItemListClickListener {
        void onItemClick(DeviceInfoModel deviceInfoModel);
    }

    public void setOnItemListClickListener(OnItemListClickListener onItemListClickListener) {
        this.onItemListClickListener = onItemListClickListener;
    }
}
