package com.example.aniomi.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class signUP_fragment extends Fragment {

    private EditText _name,_email,_pass,_dept,_year,_address,_blood;
    TextView login;
    DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");

    private FirebaseAuth mAuth;
    List<Students> lists=new ArrayList<>();
    List<String> mp=new ArrayList<>();
    public signUP_fragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signup=view.findViewById(R.id.btn_signup);
        _name=(EditText) view.findViewById(R.id.input_name);
        _email=(EditText) view.findViewById(R.id.input_email);
        _pass=(EditText) view.findViewById(R.id.input_password);
        _dept=(EditText) view.findViewById(R.id.input_department);
        _year=(EditText) view.findViewById(R.id.input_year);
        _address=(EditText) view.findViewById(R.id.input_address);
        _blood=(EditText) view.findViewById(R.id.input_blood);
        login = view.findViewById(R.id.link_login);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn_Fragment fragment=new logIn_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.commit();
            }
        });



        return  view;
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            Toast.makeText(getContext(), "Sign Up failed", Toast.LENGTH_LONG).show();
            return;
        }

        //_signupButton.setEnabled(false);EventPageMain.this, "", "Loading", true)

        final ProgressDialog progressDialog = ProgressDialog.show(getContext(),"","Creating Account...",true);

        final String name = _name.getText().toString().trim();
        final String address = _address.getText().toString().trim();
        final String email = _email.getText().toString().trim();
        final String password = _pass.getText().toString().trim();
        final String dept = _dept.getText().toString().trim();
        final String year = _year.getText().toString().trim();
        final String blood = _blood.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    progressDialog.cancel();
                    Toast.makeText(getContext(),"DONE",Toast.LENGTH_LONG).show();
                    String userID = mAuth.getCurrentUser().getUid();
                    DatabaseReference current  = mDatabase.child(userID);

                    // Need to change
                    Students temp = new Students(name,password,email,dept,year,"0.0,0.0",blood,userID);
                    current.setValue(temp);
                    /*Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);*/
                }
                else
                {
                    progressDialog.cancel();
                    Toast.makeText(getContext(),"FAILED",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public boolean validate() {
        boolean valid = true;

        String name = _name.getText().toString().trim();
        String address = _address.getText().toString().trim();
        String email = _email.getText().toString().trim();
        String password = _pass.getText().toString().trim();
        String dept = _dept.getText().toString().trim();
        String year = _year.getText().toString().trim();
        String blood = _blood.getText().toString().trim();

        if (name.isEmpty() || name.length() < 3) {
            _name.setError("at least 3 characters");
            valid = false;
        } else {
            _name.setError(null);
        }

        if (address.isEmpty()) {
            _address.setError("Enter Valid Address");
            valid = false;
        } else {
            _address.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _email.setError("enter a valid email address");
            valid = false;
        } else {
            _email.setError(null);
        }

        if (dept.isEmpty()) {
            _dept.setError("Enter Department");
            valid = false;
        } else {
            _dept.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            _pass.setError("between 6 and 10 alphanumeric characters");
            valid = false;
        } else {
            _pass.setError(null);
        }

        if (year.isEmpty()) {
            _year.setError("Enter Year");
            valid = false;
        } else {
            _year.setError(null);
        }

        if (blood.isEmpty()) {
            _blood.setError("Enter Blood Group");
            valid = false;
        } else {
            _blood.setError(null);
        }


        return valid;
    }
}
