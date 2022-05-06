package com.ssa.taskapp.ui.home;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ssa.taskapp.R;
import com.ssa.taskapp.databinding.FragmentHomeBinding;
import com.ssa.taskapp.ui.detail.DetailFragment;
import com.ssa.taskapp.ui.home.task.TaskAdapter;
import com.ssa.taskapp.ui.home.task.TaskModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController controller;
private  List<TaskModel> list = new ArrayList<>();
    private TaskAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter = new TaskAdapter();
        adapter.setList(list);

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNavController();
        initListener();
        initResultListener();

    }

    private void initResultListener() {
    getActivity().getSupportFragmentManager().setFragmentResultListener(
            DetailFragment.RESULT_HOME_KEY,
            this,
             ((requestKey, result) -> {
                 String text = result.getString(DetailFragment.HOME_KEY);
                 String date = result.getString(DetailFragment.HOME_KEY1);
                 Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();
                 adapter.addList(new TaskModel(text, date));
            })
    );

    }

    private void initNavController() {
        NavHostFragment navHostController = (NavHostFragment)
                requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        controller = navHostController.getNavController();
    }

    private void initListener() {
        binding.addTaskBtn.setOnClickListener(view -> {
            controller.navigate(R.id.detailFragment);
        });
        binding.itemTask.setAdapter(adapter);
    }
}