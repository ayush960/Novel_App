package com.example.novel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    TextView Firstname, Lastname, Email, Enroll;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Firstname = findViewById(R.id.showfirstname);
        Lastname = findViewById(R.id.showlastname);
        Email = findViewById(R.id.showemail);
        Enroll = findViewById(R.id.showenroll);
        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        DocumentReference documentReference = fStore.collection("user").document("user data");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    Firstname.setText(documentSnapshot.getString("firstname"));
                    Lastname.setText(documentSnapshot.getString("lastname"));
                    Email.setText(documentSnapshot.getString("email"));
                    Enroll.setText(documentSnapshot.getString("enroll"));

                }


            }
        });
    }

    public void logout(View view) {
        fAuth.signOut();
        finish();
        Intent intent= new Intent(ProfileActivity.this, SigninActivity.class);
        startActivity(intent);

    }
}