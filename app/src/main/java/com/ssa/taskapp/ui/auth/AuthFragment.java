package com.ssa.taskapp.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssa.taskapp.R;
import com.ssa.taskapp.databinding.FragmentAuthBinding;

public class AuthFragment extends Fragment {

    private FragmentAuthBinding binding;
    private NavController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater);
        initController();
        return binding.getRoot();

    }

    private void initController() {
        NavHostFragment navHostController = (NavHostFragment)
                requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostController != null;
        controller = navHostController.getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Eyes();
    }
    private void Eyes() {
        binding.withSlack.setEnabled(false);
        binding.withSlack.setOnClickListener(view -> {
            binding.withSlack.setVisibility(View.INVISIBLE);
            binding.withSlack.setEnabled(false);
            binding.slashEyes.setVisibility(View.VISIBLE);
            binding.slashEyes.setEnabled(true);
            binding.passEt.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
        });
        binding.slashEyes.setOnClickListener(view -> {
            binding.slashEyes.setVisibility(View.INVISIBLE);
            binding.slashEyes.setEnabled(false);
            binding.withSlack.setVisibility(View.VISIBLE);
            binding.withSlack.setEnabled(true);
            binding.passEt.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        });
    }
}