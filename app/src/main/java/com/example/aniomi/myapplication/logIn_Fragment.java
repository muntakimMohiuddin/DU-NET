package com.example.aniomi.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logIn_Fragment extends Fragment {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private EditText _emailText,_passwordText;
    private TextView _signupLink;
    private Button _loginButton;
    private FirebaseAuth mAuth;

    public logIn_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_in_, container, false);

        _emailText = v.findViewById(R.id.input_email);
        _passwordText = v.findViewById(R.id.input_password);
        _loginButton = v.findViewById(R.id.btn_login);
        _signupLink = v.findViewById(R.id.link_signup);

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        }*/

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                signUP_fragment fragment=new signUP_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.commit();
            }
        });

        return v;
    }

    public void login() {
        Log.d(TAG, "Login");

        /*if (!validate()) {
            Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();
            return;
        }*/

       // _loginButton.setEnabled(false);

        String email = _emailText.getText().toString().trim();
        String password = _passwordText.getText().toString().trim();

        // TODO: Implement your own authentication logic here.
        mAuth = FirebaseAuth.getInstance();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            final ProgressDialog progressDialog = ProgressDialog.show(getContext(),"","Logging",true);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

        public boolean validate() {
            boolean valid = true;

            String email = _emailText.getText().toString().trim();
            String password = _passwordText.getText().toString().trim();

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _emailText.setError("enter a valid email address");
                valid = false;
            } else {
                _emailText.setError(null);
            }

            if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
                _passwordText.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

            return valid;
        }
    }
