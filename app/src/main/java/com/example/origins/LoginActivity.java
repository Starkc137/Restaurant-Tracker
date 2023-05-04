package com.example.origins;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginButton;

    private TextView mSignUp,forgotPassword;
    public static final String EMAIL_REGEX = "^(.+)@(.+)$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email_loginInput);
        mPassword = findViewById(R.id.password_loginInput);
        mLoginButton = findViewById(R.id.login_button);
        mSignUp = findViewById(R.id.create_account);
        forgotPassword = findViewById(R.id.cforgot_password);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    mEmail.setError("Input valid email");
                    return;
                }

                if (password.isEmpty()) {
                    mPassword.setError("Please input valid password");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Input 6 digit password ");
                    return;
                }
                //ToDo Insert code to perform login (connect to server and fetch details
                ////String url = "http://lamp.ms.wits.ac.za/~s2451244/login.php"
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
