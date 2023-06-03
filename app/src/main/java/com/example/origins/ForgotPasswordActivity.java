package com.example.origins;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.origins.Customers.CustomerDashboardActivity;
import com.example.origins.Staff.StaffDashboardActivity;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText mEmailInput, answerInput,mNewPassword;
    private Button mResetButton, mConfirmButton,mNewPasswordBtn;
    private TextView login, displayer1, displayer;

    private final String URL1 = "https://lamp.ms.wits.ac.za/~s2451244/assignment/fetch_question.php";
    private final String URL2 = "https://lamp.ms.wits.ac.za/~s2451244/assignment/fetch_answer.php";
    private final String URL3 = "https://lamp.ms.wits.ac.za/~s2451244/assignment/new_password.php";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        // Get references to the views
        mEmailInput = findViewById(R.id.email_recovery);
        mResetButton = findViewById(R.id.reset_password_button);
        mConfirmButton = findViewById(R.id.confirm_button);
        login = findViewById(R.id.back_to_login);
        displayer =findViewById(R.id.question_displayer);
        answerInput = findViewById(R.id.answer_input);
        displayer1 = findViewById(R.id.email_displayer);
        mNewPassword = findViewById(R.id.new_password);
        mNewPasswordBtn = findViewById(R.id.set_new_password);

        // Set a click listener for the reset button

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailInput.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    getSecurityQuestion(email);

                }
            }
        });


        mNewPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailInput.getText().toString();
                String password = mNewPassword.getText().toString();

                if (password.isEmpty()) {
                    mNewPassword.setError("Please input password");
                    return;
                }
                if (password.length() < 6) {
                    mNewPassword.setError("Input at least 6 characters");
                    return;
                }
                setNewPassword(email, password);
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailInput.getText().toString();
                getSecurityAnswer(email);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity .this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getSecurityQuestion(String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String securityQuestion = response;
                if (TextUtils.isEmpty(securityQuestion)) {
                    Toast.makeText(ForgotPasswordActivity.this, "No account found with that email", Toast.LENGTH_SHORT).show();
                }
                else {
                    displayer.setText(securityQuestion);
                    mEmailInput.setVisibility(View.GONE);
                    answerInput.setVisibility(View.VISIBLE);
                    mResetButton.setVisibility(View.GONE);
                    mConfirmButton.setVisibility(View.VISIBLE);
                    displayer1.setText("Security question for "+email+ ":");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getSecurityAnswer(String email) {
        String answer = answerInput.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String securityAnswer = response;
                if (TextUtils.isEmpty(answer) ) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your security answer", Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.equals(answer,securityAnswer)) {
                    Toast.makeText(ForgotPasswordActivity.this, "ERROR: incorrect security answer", Toast.LENGTH_SHORT).show();
                } else {
                    answerInput.setVisibility(View.GONE);
                    mConfirmButton.setVisibility(View.GONE);
                    mNewPassword.setVisibility(View.VISIBLE);
                    mNewPasswordBtn.setVisibility(View.VISIBLE);
                    displayer1.setText("Correct Security Answer !!");
                    displayer.setText("Now set your new password");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("email", email);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void setNewPassword(String email, String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL3, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();

                    mNewPassword.setVisibility(View.GONE);
                    mNewPasswordBtn.setVisibility(View.GONE);
                    displayer1.setVisibility(View.GONE);
                    displayer.setText("YOU CAN GO BACK TO LOGIN NOW");
                    displayer.setTextSize(30);
                    login.setTextSize(20);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        }) {
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

