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
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginButton;
    ProgressBar progressBar;

    private TextView mSignUp,forgotPassword;
    private  String URL = "http://lamp.ms.wits.ac.za/~s2451244/log.php";
    public static final String EMAIL_REGEX = "^(.+)@(.+)$";

    private String email, password;

    //ToDO add the password eye
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
                            progressBar.setVisibility(View.VISIBLE);
                            Handler handler = new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[2];
                                    field[0] = "email";
                                    field[1] = "password";
                                    //Creating array for data
                                    String[] data = new String[2];
                                    data[0] = email;
                                    data[1] = password;
                                    PutData putData = new PutData("http://lamp.ms.wits.ac.za/~s2451244/log.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        String result = putData.getResult();
                                        if (putData.onComplete()) {
                                            progressBar.setVisibility(View.GONE);
                                            if(result.equals("User email exists and password matches"))
                                            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    //End Write and Read data with URL
                                }
                            });
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





}
