package com.ssa.taskapp.ui.home.task;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ssa.taskapp.databinding.ItemTaskBinding;
import com.ssa.taskapp.ui.detail.DetailFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskModel> list = new ArrayList<>();

    private ItemTaskBinding binding;

    public void addList(TaskModel model){
        this.list.add(model);
        notifyDataSetChanged();
    }

    public void setList(List<TaskModel> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.taskTxtN.setText(list.get(position).getTitle());
        holder.binding.taskDate.setText(list.get(position).getCreated());

//        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
//        transaction.replace(binding.containerTask, DetailFragment.class, bundle);
//        transaction.addToBackStack("AddNoteFragmentEdit");
//        transaction.commit();

        if(position %2 == 1)
        {
            holder.binding.containerTask.setBackgroundColor(Color.WHITE);
        }
        else
        {
            holder.binding.containerTask.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemTaskBinding binding;
        public ViewHolder(@NonNull ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
