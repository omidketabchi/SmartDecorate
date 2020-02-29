package com.example.smartdecorate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.Model.CategoryModel;
import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.Model.LedDeviceInfoModel;
import com.example.smartdecorate.Model.LightBulbModel;
import com.example.smartdecorate.R;

import java.util.List;

public class LightBulbAdapter extends RecyclerView.Adapter<LightBulbAdapter.LightBulbViewHolder> {

    Context context;
    List<DeviceInfoModel> deviceInfoModels;
    List<LightBulbModel> lightBulbModels;
    OnItemListClickListener onItemListClickListener;

    public LightBulbAdapter(Context context, List<DeviceInfoModel> deviceInfoModels,
                            List<LightBulbModel> lightBulbModels) {

        this.context = context;
        this.deviceInfoModels = deviceInfoModels;
        this.lightBulbModels = lightBulbModels;
    }

    @NonNull
    @Override
    public LightBulbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.light_bulb_item, parent, false);

        return new LightBulbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LightBulbViewHolder holder, int position) {

        final DeviceInfoModel model = deviceInfoModels.get(position);

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

    public class LightBulbViewHolder extends RecyclerView.ViewHolder {

        TextView txtDeviceName;
        SwitchCompat switchCompat;
        CardView parent;

        public LightBulbViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDeviceName = (TextView) itemView.findViewById(R.id.txt_lightBulbItem_deviceName);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.swh_lightBulbItem_status);

            parent = (CardView) itemView.findViewById(R.id.cv_lightBulbItem_parent);
        }
    }

    public interface OnItemListClickListener {
        void onItemClick(DeviceInfoModel deviceInfoModel);
    }

    public void setOnItemListClickListener(OnItemListClickListener onItemListClickListener) {
        this.onItemListClickListener = onItemListClickListener;
    }
}
