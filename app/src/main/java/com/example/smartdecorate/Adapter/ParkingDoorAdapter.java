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
import com.example.smartdecorate.Model.ParkingDoorModel;
import com.example.smartdecorate.R;

import java.util.List;

public class ParkingDoorAdapter extends RecyclerView.Adapter<ParkingDoorAdapter.LightBulbViewHolder> {

    Context context;
    List<DeviceInfoModel> deviceInfoModels;
    List<ParkingDoorModel> parkingDoorModels;
    OnItemListClickListener onItemListClickListener;

    public ParkingDoorAdapter(Context context, List<DeviceInfoModel> deviceInfoModels,
                              List<ParkingDoorModel> parkingDoorModels) {

        this.context = context;
        this.deviceInfoModels = deviceInfoModels;
        this.parkingDoorModels = parkingDoorModels;
    }

    @NonNull
    @Override
    public LightBulbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.parking_door_item, parent, false);

        return new LightBulbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LightBulbViewHolder holder, int position) {

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
        CardView parent;

        public LightBulbViewHolder(@NonNull View itemView) {
            super(itemView);

            txtDeviceName = (TextView) itemView.findViewById(R.id.txt_parkingDoorItem_deviceName);

            parent = (CardView) itemView.findViewById(R.id.cv_parkingDoorItem_parent);
        }
    }

    public interface OnItemListClickListener {
        void onItemClick(DeviceInfoModel deviceInfoModel);
    }

    public void setOnItemListClickListener(OnItemListClickListener onItemListClickListener) {
        this.onItemListClickListener = onItemListClickListener;
    }
}
