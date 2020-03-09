package com.example.smartdecorate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.Model.LightBulbModel;
import com.example.smartdecorate.Model.WaterValveModel;
import com.example.smartdecorate.R;

import java.util.List;

public class WaterValveAdapter extends RecyclerView.Adapter<WaterValveAdapter.WaterValveViewHolder> {

    Context context;
    List<DeviceInfoModel> deviceInfoModels;
    List<WaterValveModel> waterValveModels;
    OnItemListClickListener onItemListClickListener;

    public WaterValveAdapter(Context context, List<DeviceInfoModel> deviceInfoModels,
                             List<WaterValveModel> waterValveModels) {

        this.context = context;
        this.deviceInfoModels = deviceInfoModels;
        this.waterValveModels = waterValveModels;
    }

    @NonNull
    @Override
    public WaterValveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.water_valve_item, parent, false);

        return new WaterValveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final WaterValveViewHolder holder, int position) {

        final DeviceInfoModel model = deviceInfoModels.get(position);
        final WaterValveModel waterValveModel = waterValveModels.get(position);


        holder.switchCompat.setChecked((waterValveModel.getActiveNow() == 1) ? true : false);
        holder.txtDeviceName.setText(model.getDeviceName());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //holder.switchCompat.setChecked(!holder.switchCompat.isChecked());
                onItemListClickListener.onItemClick(model, (holder.switchCompat.isChecked()) ? 1 : 0);
            }
        });

        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //onItemListClickListener.onItemClick(model, (holder.switchCompat.isChecked()) ? 1 : 0);
            }
        });

        holder.switchCompat.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return deviceInfoModels.size();
    }

    public class WaterValveViewHolder extends RecyclerView.ViewHolder {

        TextView txtDeviceName;
        SwitchCompat switchCompat;
        CardView parent;

        public WaterValveViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDeviceName = (TextView) itemView.findViewById(R.id.txt_waterValveItem_deviceName);
            switchCompat = (SwitchCompat) itemView.findViewById(R.id.swh_waterValveItem_status);

            parent = (CardView) itemView.findViewById(R.id.cv_waterValveItem_parent);
        }
    }

    public interface OnItemListClickListener {
        void onItemClick(DeviceInfoModel deviceInfoModel, int status);
    }

    public void setOnItemListClickListener(OnItemListClickListener onItemListClickListener) {
        this.onItemListClickListener = onItemListClickListener;
    }
}
