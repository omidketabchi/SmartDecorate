package com.example.smartdecorate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.Model.DeviceInfoModel;
import com.example.smartdecorate.R;

import java.util.List;

public class DeviceInfoAdapter extends RecyclerView.Adapter<DeviceInfoAdapter.DeviceInfoViewHolder> {

    Context context;
    List<DeviceInfoModel> models;

    public DeviceInfoAdapter(Context context, List<DeviceInfoModel> models) {

        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public DeviceInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.device_item, viewGroup, false);

        return new DeviceInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceInfoViewHolder holder, int position) {

        final DeviceInfoModel model = new DeviceInfoModel();

        holder.txtDeviceName.setText(model.getDeviceName());
        holder.txtDeviceIp.setText(model.getDeviceIp());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, model.getDeviceName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class DeviceInfoViewHolder extends RecyclerView.ViewHolder {

        ImageView imgEdit;
        ImageView imgDelete;
        TextView txtDeviceName;
        TextView txtDeviceIp;
        CardView parent;

        public DeviceInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            imgEdit = (ImageView) itemView.findViewById(R.id.img_deviceItem_edit);
            imgDelete = (ImageView) itemView.findViewById(R.id.img_deviceItem_delete);
            txtDeviceIp = (TextView) itemView.findViewById(R.id.txt_deviceItem_deviceIp);
            txtDeviceName = (TextView) itemView.findViewById(R.id.txt_deviceItem_deviceName);
            parent = (CardView) itemView.findViewById(R.id.cv_deviceItem_parent);
        }
    }
}
