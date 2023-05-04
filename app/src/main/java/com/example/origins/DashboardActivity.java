package com.example.origins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.origins.Fragments.HomeFragment;
import com.example.origins.Fragments.ProfileFragment;
import com.example.origins.Fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");

        FragmentManager fragmentManager = getSupportFragmentManager();

       // firebaseAuth = FirebaseAuth.getInstance();
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemReselectedListener(selectedListener);


    }
    FragmentManager fragmentManager = getSupportFragmentManager();
    private final BottomNavigationView.OnNavigationItemReselectedListener selectedListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    //

                    fragmentManager.beginTransaction()
                            .replace(R.id.content, HomeFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name") // name can be null
                            .commit();
                    return;
                case R.id.nav_profile:
                    //
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, ProfileFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name") // name can be null
                            .commit();
                    return;
                case R.id.nav_users:
                    //
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, UsersFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name") // name can be null
                            .commit();
            }
        }
    };


}