package com.ssa.taskapp.ui.fragment.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.ssa.taskapp.App;
import com.ssa.taskapp.R;
import com.ssa.taskapp.data.model.TaskModel;
import com.ssa.taskapp.databinding.ItemTaskBinding;
import com.ssa.taskapp.ui.fragment.home.Click;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskModel> list = new ArrayList<>();
    Click click;
    private int pos;

    public TaskAdapter(Click click) {
        this.click = click;
    }

    public void addList(List<TaskModel> list){
        this.list.clear();
        this.list.addAll(list);
        notifyItemChanged(list.size());
    }


    public void sortA(){
        list = App.dateBase.taskDao().sortWithAlphabetAllTasks();
        notifyDataSetChanged();
    }
    public void sortD(){
        list = App.dateBase.taskDao().sortWithDateAllTasks();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.
                inflate(LayoutInflater.
                                from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position).getTitle(), list.get(position).getCreated());
        if (position % 2 == 1) {
            holder.binding.containerTask.setBackground(AppCompatResources.getDrawable(holder.
                    itemView.getContext(), R.drawable.shape_for_rv));
        } else {
            holder.binding.containerTask.setBackground(AppCompatResources.
                    getDrawable(holder.itemView.getContext(), R.drawable.shape_for_rv2));
        }
        holder.binding.containerTask.setOnClickListener(v -> click.
                click(list.get(holder.getAdapterPosition())));
        holder.binding.containerTask.setLongClickable(true);
        holder.binding.containerTask.setOnLongClickListener(v -> {
            click.delete(list.get(holder.getAdapterPosition()));
            return true;
        });
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
