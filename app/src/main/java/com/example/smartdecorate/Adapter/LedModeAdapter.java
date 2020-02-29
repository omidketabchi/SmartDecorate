package com.example.smartdecorate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.R;

import java.util.List;

public class LedModeAdapter extends RecyclerView.Adapter<LedModeAdapter.LedStripViewHolder> {

    Context context;
    List<String> modeList;
    OnEffectClickListener onEffectClickListener;

    public LedModeAdapter(Context context, List<String> modeList) {
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

        final String mode = modeList.get(position);

        holder.txtMode.setText(mode);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEffectClickListener.OnEffectClick(mode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modeList.size();
    }

    public class LedStripViewHolder extends RecyclerView.ViewHolder {

        TextView txtMode;
        CardView parent;

        public LedStripViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMode = (TextView) itemView.findViewById(R.id.txt_modeItem_mode);
            parent = (CardView) itemView.findViewById(R.id.cv_modeItem_parent);
        }
    }

    public interface OnEffectClickListener {
        void OnEffectClick(String effect);
    }

    public void setOnEffectClickListener(OnEffectClickListener onEffectClickListener) {
        this.onEffectClickListener = onEffectClickListener;
    }
}
