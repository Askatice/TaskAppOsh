package com.ssa.taskapp.ui.fragment.board;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ssa.taskapp.databinding.FragmentBoardBinding;


public class BoardFragment extends Fragment {

    private FragmentBoardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     binding = FragmentBoardBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        setHasOptionsMenu(true);
        ActionBar();
    }

    private void ActionBar() {
           ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        }

    private void initAdapter() {
        BoardAdapter adapter = new BoardAdapter();
        binding.vpBoard.setAdapter(adapter);
    }
}