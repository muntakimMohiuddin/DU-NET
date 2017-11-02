package com.example.aniomi.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText mNameField,mEmailField,mPassField;
    private Button mSignUp;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mNameField = (EditText) findViewById(R.id.editText);
        mEmailField = (EditText) findViewById(R.id.editText2);
        mPassField = (EditText) findViewById(R.id.editText3);
        mSignUp= (Button) findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

/*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                  //  Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    mSignUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startSignUp();
                        }
                    });
                }
                // ...
            }
        };*/

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });

    }

    private void startSignUp() {
        final String name = mNameField.getText().toString().trim();
        final String email = mEmailField.getText().toString().trim();
        final String pass = mPassField.getText().toString().trim();


        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
        {
           /* mprog.setMessage("Signing UP...");
            mprog.show();*/
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"DONE",Toast.LENGTH_LONG).show();
                        String userID = mAuth.getCurrentUser().getUid();
                        DatabaseReference current  = mDatabase.child(userID);
                        Students temp = new Students(name,pass,email,"CSE","2nd","Dhaka","O+",userID);
                       /* current.child("Name").setValue(name);
                        current.child("Department").setValue("CSE");
                        current.child("Year").setValue("2nd");
                        current.child("Blood Group").setValue("O+");*/
                        current.setValue(temp);
                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
