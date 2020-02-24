package com.example.smartdecorate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.R;

import java.util.List;

public class LedStripAdapter extends RecyclerView.Adapter<LedStripAdapter.LedStripViewHolder> {

    Context context;
    List<String> modeList;

    public LedStripAdapter(Context context, List<String> modeList) {
        this.context = context;
        this.modeList = modeList;
    }

    @NonNull
    @Override
    public LedStripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.mode_item, parent, false);

        return new LedStripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LedStripViewHolder holder, int position) {

        String mode = modeList.get(position);

        holder.txtMode.setText(mode);
    }

    @Override
    public int getItemCount() {
        return modeList.size();
    }

    public class LedStripViewHolder extends RecyclerView.ViewHolder {

        TextView txtMode;

        public LedStripViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMode = (TextView)itemView.findViewById(R.id.txt_modeItem_mode);
        }
    }
}
