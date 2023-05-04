package com.example.origins;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;




public class SignupActivity extends AppCompatActivity {

    private EditText NameSK, EmailSK, PasswordSK, confirmPasswordSK;
    private Button sSignupButton;
    private TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        NameSK = findViewById(R.id.nameET);
        EmailSK = findViewById(R.id.emailET);
        PasswordSK = findViewById(R.id.passwordET);
        confirmPasswordSK = findViewById(R.id.confirmPassET);
        sSignupButton = findViewById(R.id.signUpBtn);
        login = findViewById(R.id.loginTV);


        sSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = NameSK.getText().toString().trim();
                String email = EmailSK.getText().toString().trim();
                String password = PasswordSK.getText().toString().trim();

                //ToDo Insert code to make HTTP request to server using Signup PHP file
                //String url = "http://lamp.ms.wits.ac.za/~s2451244/signup.php"

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
    }


}
