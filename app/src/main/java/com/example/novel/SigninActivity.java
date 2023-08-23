package com.example.novel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        EditText id = findViewById(R.id.username);
        EditText pass = findViewById(R.id.password);
        Button login = findViewById(R.id.loginbtn);
        fAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            String email = id.getText().toString();
            String password = pass.getText().toString();

            fAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SigninActivity.this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SigninActivity.this, "Sign in success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SigninActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SigninActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        //sign in


        TextView SignUp = findViewById(R.id.signupinstead);
        SignUp.setOnClickListener(view -> {
            Intent intent= new Intent(SigninActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        googleBtn = findViewById(R.id.google_btn);
        googleBtn.setOnClickListener(view -> signin());
    }

    private void signin() {
        Intent signinIntent = gsc.getSignInIntent();
        startActivityForResult(signinIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            Task<GoogleSignInAccount> Task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Task.getResult(ApiException.class);
                navigateToSecondActivity();

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToSecondActivity() {
        finish();
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);


    }

    public void login(View view) {

    }
}

