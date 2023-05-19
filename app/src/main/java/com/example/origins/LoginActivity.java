package com.example.origins;

import android.content.Intent;

import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.origins.Customers.CustomerDashboardActivity;
import com.example.origins.Staff.StaffDashboardActivity;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginButton;
    ProgressBar progressBar;

    private final String URL = "https://lamp.ms.wits.ac.za/~s2451244/login.php";
    private TextView mSignUp,forgotPassword;
    public static final String EMAIL_REGEX = "^(.+)@(.+)$";

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email_loginInput);
        mPassword = findViewById(R.id.password_loginInput);
        mLoginButton = findViewById(R.id.login_button);
        mSignUp = findViewById(R.id.create_account);
        forgotPassword = findViewById(R.id.cforgot_password);
        progressBar = findViewById(R.id.progressBar);


        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    mEmail.setError("Please input valid email");
                    return;
                }

                if (password.isEmpty() ) {
                    mPassword.setError("Please input password");
                    return;
                }
                if (password.length() < 6  ) {
                    mPassword.setError("Input at least 6 characters");
                    return;
                }
                /*progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "email";
                        field[1] = "password";

                        String[] data = new String[2];
                        data[0] = email;
                        data[1] = password;

                        PutData putData = new PutData("http://lamp.ms.wits.ac.za/~s2451244/login2.php", "POST", field, data);
                        if (putData.startPut()) {
                            String result = putData.getResult();
                            if (putData.onComplete()) {
                                progressBar.setVisibility(View.GONE);
                                if (result.equals("customer")) {
                                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, CustomerDashboardActivity.class));
                                    finish();
                                }
                                if (result.equals("staff")) {
                                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, StaffDashboardActivity.class));
                                    finish();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });*/
                login();
            }

        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        // Get user inputs
        email = mEmail.getText().toString().trim();
        password = mPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response.equals("customer")) {
                    startActivity(new Intent(getApplicationContext(),CustomerDashboardActivity.class));
                } else if (response.equals("staff")) {
                    startActivity(new Intent(getApplicationContext(),StaffDashboardActivity.class));
                }

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
                data.put("email", email);
                data.put("password", password);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}