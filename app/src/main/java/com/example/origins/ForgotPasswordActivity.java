package com.example.origins;

import static com.example.origins.LoginActivity.EMAIL_REGEX;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

//ToDo: Connect this to a php file that sends an email to the user with password token
public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mEmailInput, answerInput;
    private Button mResetButton, mConfirmButton;
    private TextView login, displayer1, displayer;
    private ProgressBar progressBar;


    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        // Initialize the database
        mAuth = FirebaseAuth.getInstance();


        // Get references to the views
        mEmailInput = findViewById(R.id.email_recovery);
        mResetButton = findViewById(R.id.reset_password_button);
        mConfirmButton = findViewById(R.id.confirm_button);
        progressBar = findViewById(R.id.progressBar);
        login = findViewById(R.id.back_to_login);
        displayer = findViewById(R.id.question_displayer);
        answerInput = findViewById(R.id.answer_input);
        displayer1 = findViewById(R.id.email_displayer);

        // Set a click listener for the reset button

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailInput.getText().toString();

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    mEmailInput.setError("Input valid email");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Password reset email send successfully",
                                            Toast.LENGTH_SHORT).show();
                                    mEmailInput.setText("");
                                } else {
                                    String errMsg = task.getException().getMessage();
                                    Toast.makeText(ForgotPasswordActivity.this, "Error: " + errMsg, Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);

                            }
                        });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


}

