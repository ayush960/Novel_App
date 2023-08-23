package com.example.novel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.nav);
        nav.setOnItemReselectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                case R.id.explore:
                    Toast.makeText(MainActivity.this, "Explore", Toast.LENGTH_SHORT).show();
                    Intent intent1= new Intent(MainActivity.this, ExploreActivity.class);
                    startActivity(intent1);
                case R.id.add:
                    Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_SHORT).show();
                    Intent intent3= new Intent(MainActivity.this, AddOnActivity.class);
                    startActivity(intent3);
                case R.id.profile:
                    Intent intent2= new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent2);
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();


            }
        });


    }

}