package com.example.origins.Customers;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.origins.Customers.Fragments.CustomerHomeFragment;
import com.example.origins.Customers.Fragments.CustomerProfileFragment;
import com.example.origins.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerDashboardActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new CustomerHomeFragment();
                    break;
                case R.id.nav_account:
                    selectedFragment = new CustomerProfileFragment();
                    break;
            }

            if (selectedFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, selectedFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            return true;
        }
    };
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_dashboard);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        // Load the HomeFragment as the default fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new CustomerHomeFragment());
        transaction.commit();
    }
}
