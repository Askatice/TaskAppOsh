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
import android.widget.Toast;

import com.ssa.taskapp.R;
import com.ssa.taskapp.databinding.FragmentDetailBinding;
import com.ssa.taskapp.ui.fragment.home.HomeFragment;

import java.util.Date;
import java.util.Locale;

public class DetailFragment extends Fragment {


    public static final String HOME_KEY = "home_key";
    public static final String HOME_KEY1 = "home_key1";
    public static final String HOME_KEY3 = "home_key3";
    public static final String RESULT_HOME_KEY = "result_home_key";
    public static final String RESULT_HOME_KEY_EDIT = "result_home_key_edit";

    private FragmentDetailBinding binding;

    private NavController controller;

    String date = new SimpleDateFormat("dd.MM.yyyy   HH:mm:ss",
            Locale.getDefault()).format(new Date());


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
        checkIsEdit();
    }

    private void checkIsEdit() {
        getParentFragmentManager().setFragmentResultListener(
                HomeFragment.ADAPTER_KEY2,
                this,
                ((requestKey, result) -> {
                    String text = result.getString(HomeFragment.ADAPTER_KEY).trim();
                    String date = result.getString(HomeFragment.ADAPTER_KEY1);
                    int id = result.getInt(HomeFragment.ADAPTER_KEY3);
                    binding.taskTxt.setText(text);
                    binding.btnSave.setOnClickListener(v -> {
                        String text2 = binding.taskTxt.getText().toString().trim();
                        if (!binding.taskTxt.getText().toString().trim().isEmpty()) {
                            Bundle bundle = new Bundle();
                            bundle.putString(HOME_KEY1, date);
                            bundle.putString(HOME_KEY, text2);
                            bundle.putInt(HOME_KEY3, id);
                            getParentFragmentManager().setFragmentResult(
                                    RESULT_HOME_KEY_EDIT, bundle);
                            controller.navigateUp();
                        }
                    });
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

    private void initListeners() {
    binding.btnSave.setOnClickListener(view -> {
        sendDateToHomeFragment();
        closeFragment();
    });
    }

    private void sendDateToHomeFragment() {
    String text = binding.taskTxt.getText().toString().trim();
    if (!binding.taskTxt.getText().toString().trim().isEmpty()) {
        Bundle bundle = new Bundle();
        bundle.putString(HOME_KEY1, date);
        bundle.putString(HOME_KEY, text);
        getParentFragmentManager().setFragmentResult(RESULT_HOME_KEY, bundle);
    }else Toast.makeText(requireContext(),
            "Напишите что нибудь",
            Toast.LENGTH_SHORT).show();
    }

    private void closeFragment() {
        controller.navigateUp();
    }
}