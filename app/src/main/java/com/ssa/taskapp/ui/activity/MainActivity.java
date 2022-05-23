package com.ssa.taskapp.ui.activity;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseUser;
import com.ssa.taskapp.App;
import com.ssa.taskapp.R;
import com.ssa.taskapp.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initNavController();
        init();
        openBoard();
        openAuth();
        BottomNav();
    }
    private void openAuth() {
        if (App.user == null) {
            controller.navigate(R.id.authFragment);
        }
    }

    private void BottomNav() {
        controller.addOnDestinationChangedListener((navController,
                                                    navDestination, bundle) -> {
            if (navDestination.getId() == R.id.boardFragment || navDestination.getId() ==
                    R.id.detailFragment || navDestination.getId() == R.id.authFragment) {
                binding.navView.setVisibility(View.GONE);
                Objects.requireNonNull(getSupportActionBar()).hide();
            } else {
                binding.navView.setVisibility(View.VISIBLE);
                Objects.requireNonNull(getSupportActionBar()).show();
            }
        });
    }

    private void init() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard).build();
        controller = findNavController(this,
                R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this,
                controller, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, controller);
    }

    private void openBoard() {
        if(!App.prefs.isShown()) {
            controller.navigate(R.id.boardFragment);
            App.prefs.isShowed();
        }
    }

    private void initNavController() {
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment_activity_main);
        assert host != null;
        NavController navController = host.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,
                R.id.navigation_notifications)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController,
                appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}