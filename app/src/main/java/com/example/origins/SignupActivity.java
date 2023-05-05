package com.example.origins;


import static com.example.origins.LoginActivity.EMAIL_REGEX;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;


public class SignupActivity extends AppCompatActivity {

    private EditText nameSK;
    private EditText emailSK;
    private EditText passwordSK;
    private EditText confirmPasswordSK;
    private Button sSignupButton;
    private TextView login;
    private ProgressBar progressBar;
    private  String URL = "https://lamp.ms.wits.ac.za/~s2451244/signup.php";
    String username, email,password,confirmPassword;
;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        nameSK = findViewById(R.id.nameET);
        emailSK = findViewById(R.id.emailET);
        passwordSK = findViewById(R.id.passwordET);
        confirmPasswordSK = findViewById(R.id.confirmPassET);
        sSignupButton = findViewById(R.id.signUpBtn);
        login = findViewById(R.id.loginTV);
        progressBar = findViewById(R.id.progressBar);
        username = email = password =confirmPassword ="";



        sSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo Insert code to make HTTP request to server using Signup PHP file
                username = nameSK.getText().toString().trim();
                email = emailSK.getText().toString().trim();
                password = passwordSK.getText().toString().trim();
                confirmPassword = confirmPasswordSK.getText().toString().trim();

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
                signup();
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
            // Get user inputs
            username = nameSK.getText().toString();
            email = emailSK.getText().toString();
            password = passwordSK.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }




