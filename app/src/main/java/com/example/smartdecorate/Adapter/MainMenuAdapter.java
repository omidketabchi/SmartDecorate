package com.example.smartdecorate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartdecorate.Model.MainMenuItemModel;
import com.example.smartdecorate.R;

import java.util.List;

public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder> {

    Context context;
    List<MainMenuItemModel> models;
    OnMenuItemClick onMenuItemClick;

    public MainMenuAdapter(Context context, List<MainMenuItemModel> models) {

        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.main_menu_item, viewGroup, false);

        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuViewHolder holder, int position) {

        final MainMenuItemModel model = models.get(position);

        holder.txtLeftBox.setText(model.getLeftTitle());
        holder.txtRightBox.setText(model.getRightTitle());

        holder.imgLeftBox.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_shortcut_view_grid_plus));
        holder.imgRightBox.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_shortcut_format_list_text));

        if (!model.getLeftTitle().isEmpty()) {
            holder.leftBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuItemClick.onMenuItemClick(model.getLeftTitle());
                }
            });
        } else {
            holder.leftBox.setVisibility(View.GONE);
            holder.leftBox.setClickable(false);
        }

        if (!model.getRightTitle().isEmpty()) {
            holder.rightBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuItemClick.onMenuItemClick(model.getRightTitle());
                }
            });
        } else {
            holder.rightBox.setVisibility(View.GONE);
            holder.rightBox.setClickable(false);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MainMenuViewHolder extends RecyclerView.ViewHolder {

        CardView leftBox;
        CardView rightBox;

        ImageView imgLeftBox;
        ImageView imgRightBox;

        TextView txtLeftBox;
        TextView txtRightBox;

        public MainMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            leftBox = (CardView) itemView.findViewById(R.id.cv_mainMenuItem_leftBox);
            rightBox = (CardView) itemView.findViewById(R.id.cv_mainMenuItem_rightBox);

            imgLeftBox = (ImageView) itemView.findViewById(R.id.img_mainMenuItem_leftImage);
            imgRightBox = (ImageView) itemView.findViewById(R.id.img_mainMenuItem_rightImage);

            txtLeftBox = (TextView) itemView.findViewById(R.id.txt_mainMenuItem_leftTitle);
            txtRightBox = (TextView) itemView.findViewById(R.id.txt_mainMenuItem_rightTitle);
        }
    }

    public interface OnMenuItemClick {
        void onMenuItemClick(String title);
    }

    public void setOnMenuItemClick(OnMenuItemClick onMenuItemClick) {
        this.onMenuItemClick = onMenuItemClick;
    }
}
