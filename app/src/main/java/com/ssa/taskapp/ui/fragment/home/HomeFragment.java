package com.ssa.taskapp.ui.fragment.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ssa.taskapp.R;
import com.ssa.taskapp.databinding.FragmentHomeBinding;
import com.ssa.taskapp.ui.fragment.detail.DetailFragment;
import com.ssa.taskapp.ui.fragment.home.adapter.TaskAdapter;
import com.ssa.taskapp.data.model.TaskModel;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NavController controller;
    private final TaskAdapter adapter = new TaskAdapter();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initNavController();
        initListener();
        initResultListener();
    }

    private void initAdapter() {
        binding.itemTask.setAdapter(adapter);
    }

    private void initResultListener() {
        getParentFragmentManager().setFragmentResultListener(
                DetailFragment.RESULT_HOME_KEY,
                this,
                ((requestKey, result) -> {
                    String text = result.getString(DetailFragment.HOME_KEY);
                    String date = result.getString(DetailFragment.HOME_KEY1);

                    adapter.addList(new TaskModel(text,date));
                })
        );
    }



    private void initNavController() {
        NavHostFragment navHostController = (NavHostFragment)
                requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostController != null;
        controller = navHostController.getNavController();
    }

    private void initListener() {
        binding.addTaskBtn.setOnClickListener(view -> controller.navigate(R.id.detailFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}