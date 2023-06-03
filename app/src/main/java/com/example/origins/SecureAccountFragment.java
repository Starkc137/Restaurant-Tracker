package com.example.origins;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.origins.Staff.StaffSignupActivity;

import java.util.HashMap;
import java.util.Map;


public class SecureAccountFragment extends Fragment {
    private EditText securityQuestion,securityAnswer;
    private Button secureBtn;
    private ProgressBar progressBar;
    private final String URL = "https://lamp.ms.wits.ac.za/~s2451244/assignment/secure_account.php";
    String qSecurity,aSecurity,email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_secure_account, container, false);

        securityQuestion = rootView.findViewById(R.id.securityQ);
        securityAnswer = rootView.findViewById(R.id.securityA);
        secureBtn = rootView.findViewById(R.id.secureAccount);
        progressBar = rootView.findViewById(R.id.progressBar);

        email = getActivity().getIntent().getStringExtra("email");

        secureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                qSecurity = securityQuestion.getText().toString();
                aSecurity = securityAnswer.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.equals("Success")) {
                            Toast.makeText(getActivity().getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            securityQuestion.setText("");
                            securityAnswer.setText("");
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Failure",Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("email", email);
                        data.put("security_question", qSecurity);
                        data.put("security_answer", aSecurity);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Handle the back button press
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // Navigate to the previous fragment
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.popBackStack();
                    return true;
                }
                return false;
            }
        });
    }


}