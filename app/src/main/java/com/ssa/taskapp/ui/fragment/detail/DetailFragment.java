package com.ssa.taskapp.ui.fragment.detail;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssa.taskapp.R;
import com.ssa.taskapp.databinding.FragmentDetailBinding;

import java.util.Date;
import java.util.Locale;

public class DetailFragment extends Fragment {

    public static final String HOME_KEY = "home_key";
    public static final String HOME_KEY1 = "home_key1";
    public static final String RESULT_HOME_KEY = "result_home_key";

    private FragmentDetailBinding binding;

    private NavController controller;

    String date = new SimpleDateFormat("dd.MM.yyyy   HH:mm:ss", Locale.getDefault()).format(new Date());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        initNavController();
    }

    private void initNavController() {
        NavHostFragment navHostController = (NavHostFragment)
                requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostController != null;
        controller = navHostController.getNavController();
    }

    private void initListeners() {
    binding.btnSave.setOnClickListener(view -> {
        sendDateToHomeFragment();
        closeFragment();
    });
    }

    private void sendDateToHomeFragment() {
    String text = binding.taskTxt.getText().toString();
    Bundle bundle = new Bundle();
    bundle.putString(HOME_KEY1, date);
    bundle.putString(HOME_KEY, text);
    getParentFragmentManager().setFragmentResult(RESULT_HOME_KEY, bundle);
    }

    private void closeFragment() {
        controller.navigateUp();
    }
}