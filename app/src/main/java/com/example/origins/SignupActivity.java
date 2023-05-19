package com.example.origins;


import static com.example.origins.LoginActivity.EMAIL_REGEX;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

//ToDo add user selection in php files and implement it here
//ToDo new php
public class SignupActivity extends AppCompatActivity {

    private EditText nameSK;
    private EditText emailSK;
    private EditText passwordSK;
    private EditText confirmPasswordSK;
    private Button sSignupButton;
    private TextView login;
    private ProgressBar progressBar;
    private Spinner userTypeSpinner;
    private final String URL = "https://lamp.ms.wits.ac.za/~s2451244/signup.php";
    String username, email,password,confirmPassword, user_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameSK = findViewById(R.id.nameET);
        emailSK = findViewById(R.id.emailET);
        passwordSK = findViewById(R.id.passwordET);
        confirmPasswordSK = findViewById(R.id.confirmPassET);
        sSignupButton = findViewById(R.id.signUpBtn);
        login = findViewById(R.id.loginTV);
        progressBar = findViewById(R.id.progressBar);
        username = email = password =confirmPassword ="";
        userTypeSpinner = findViewById(R.id.user_type_spinner);


        //Code to set spinner color
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.user_types, R.layout.spinner_item_layout);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_layout);
        userTypeSpinner.setAdapter(adapter);



        sSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = nameSK.getText().toString().trim();
                email = emailSK.getText().toString().trim();
                password = passwordSK.getText().toString().trim();
                confirmPassword = confirmPasswordSK.getText().toString().trim();
                user_type = userTypeSpinner.getSelectedItem().toString();

                if (username.isEmpty() || username.equals(" ")) {
                    nameSK.setError("Please input valid name");
                    return;
                }

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    emailSK.setError("Please input valid email");
                    return;
                }

                if (password.isEmpty() ) {
                    passwordSK.setError("Please input password");
                    return;
                }
                if (password.length() < 6  ) {
                    passwordSK.setError("Input at least 6 characters");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    confirmPasswordSK.setError("Password not match");
                    return;
                }
                if(user_type.equals("None")){
                    Toast.makeText(SignupActivity.this, "Select valid user", Toast.LENGTH_SHORT).show();
                }
                else{
                    signup();
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });

    }
        private void signup() {
            progressBar.setVisibility(View.VISIBLE);
            // Get user inputs
            username = nameSK.getText().toString();
            email = emailSK.getText().toString();
            password = passwordSK.getText().toString();
            user_type = userTypeSpinner.getSelectedItem().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.equals("Success")) {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    } else if (response.equals("User with this email already exists")) {
                        Toast.makeText(SignupActivity.this, "User with this email already exists", Toast.LENGTH_SHORT).show();                    }
                    else if (response.equals("Failure")) {
                        Toast.makeText(SignupActivity.this, "Error: Query Failed", Toast.LENGTH_SHORT).show();                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", username);
                    data.put("email", email);
                    data.put("password", password);
                    data.put("user_type", user_type);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }




