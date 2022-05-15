package com.ssa.taskapp.ui.fragment.board;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssa.taskapp.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    private final int[] img = {
            R.drawable.img,
            R.drawable.img_1,
            R.drawable.img_2,
            R.drawable.img_3
    };
    private final String[] title = {
            "Добро пожаловать!",
            "Прилоэение",
            "Преумущество ",
            "Хорошего использование"
    };
    private final String[] desc = {
            "Это приложение поможет вам не забывать свои планы",
            "Наше приложение поможет вам записать свои задачи",
            "С помощью нашего приложение ваши задачи всегда буду у вас рядом и " +
                    "не придется взять собой дневник",
            "Хорошего использование"
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vp,
                parent,
                false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(img[position], title[position], desc[position]);
    }

    @Override
    public int getItemCount() {
        return img.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, description;
        private final ImageView image;
        public ViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.vp_title);
            description = view.findViewById(R.id.vp_description);
            image = view.findViewById(R.id.vp_image);
        }



        public void onBind(int img, String title, String desc) {
            this.title.setText(title);
            this.description.setText(desc);
            this.image.setImageResource(img);
        }
    }
}