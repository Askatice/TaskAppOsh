package com.ssa.taskapp.ui.fragment.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.ssa.taskapp.R;
import com.ssa.taskapp.data.model.TaskModel;
import com.ssa.taskapp.databinding.ItemTaskBinding;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final List<TaskModel> list = new ArrayList<>();


    public void addList(TaskModel model) {
        this.list.add(model);
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position).getTitle(), list.get(position).getCreated());
        if (position % 2 == 1) {
            holder.binding.containerTask.setBackground(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.shape_for_rv));
        } else {
            holder.binding.containerTask.setBackground(AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.shape_for_rv2));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTaskBinding binding;

        public ViewHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(String title, String created) {
            binding.taskTxtN.setText(title);
            binding.taskDate.setText(created);
        }
    }
}
