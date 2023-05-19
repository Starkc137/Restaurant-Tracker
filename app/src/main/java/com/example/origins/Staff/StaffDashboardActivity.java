package com.example.origins.Staff;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.origins.R;
import com.example.origins.Staff.Fragments.OrdersFragment;
import com.example.origins.Staff.Fragments.StaffHomeFragment;
import com.example.origins.Staff.Fragments.StaffProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StaffDashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_dashboard);

        bottomNavigationView1 = findViewById(R.id.bottom_navigation2);
        bottomNavigationView1.setOnNavigationItemSelectedListener(selectedListener);

        // Load the HomeFragment as the default fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content2, new StaffHomeFragment());
        transaction.commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener selectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new StaffHomeFragment();
                    break;
                case R.id.nav_orders:
                    selectedFragment = new OrdersFragment();
                    break;
                case R.id.nav_account:
                    selectedFragment = new StaffProfileFragment();
                    break;

            }

            if (selectedFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content2, selectedFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }

            return true;
        }
    };
}
