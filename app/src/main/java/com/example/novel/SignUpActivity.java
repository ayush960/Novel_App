package com.example.novel;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText enrol;
    private EditText password;
    FirebaseAuth mAuth;
     Button signup;
     TextView signinstead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        firstname=findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        enrol = findViewById(R.id.enrol);
        password = findViewById(R.id.password);
        signinstead = findViewById(R.id.signininstead);
        signup= findViewById(R.id.signup);

        signup.setOnClickListener(view -> {
            String getFName = firstname.getText().toString();
            String getLName = lastname.getText().toString();
            String getEmail = email.getText().toString();
            String getEnroll = enrol.getText().toString();
            String getPassword = password.getText().toString();

            HashMap<String,Object>hashMap= new HashMap<>();
            hashMap.put("firstname",getFName);
            hashMap.put("lastname",getLName);
            hashMap.put("email",getEmail);
            hashMap.put("enroll",getEnroll);
            hashMap.put("password",getPassword);
            mAuth = FirebaseAuth.getInstance();

            FirebaseFirestore.getInstance().collection("user")
                    .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                    .set(hashMap)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(SignUpActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        mAuth.createUserWithEmailAndPassword(getEmail, getPassword)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        Intent intent= new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    });

        });



        signinstead.setOnClickListener(View ->{
            Intent intent= new Intent(SignUpActivity.this, SigninActivity.class);
            startActivity(intent);
        });


    }
}