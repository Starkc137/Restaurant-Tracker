package com.example.origins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.origins.Customers.CustomerSignupActivity;
import com.example.origins.Staff.StaffSignupActivity;

public class NavigatorActivity extends AppCompatActivity {

    private Button Customer_sign_up, Staff_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        Customer_sign_up = findViewById(R.id.buttonCustomer);
        Staff_sign_up = findViewById(R.id.buttonStaff);

        Customer_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigatorActivity.this, CustomerSignupActivity.class));
            }
        });

        Staff_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigatorActivity.this, StaffSignupActivity.class));
            }
        });
    }
}