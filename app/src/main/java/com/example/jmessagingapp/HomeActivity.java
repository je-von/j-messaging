package com.example.jmessagingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.jmessagingapp.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.profileFragment,
                R.id.homeFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.homeFragment); // change to whichever id should be default
        }
    }



}