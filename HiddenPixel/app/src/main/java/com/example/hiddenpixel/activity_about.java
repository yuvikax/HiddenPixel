package com.example.hiddenpixel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class activity_about extends AppCompatActivity {
    ImageButton user_details;
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        nav=findViewById(R.id.bottomNavigationView);
        navigation();
        user_details = findViewById(R.id.userbtn);

        user_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getApplicationContext(),activity_userProfile.class);
                startActivity(profile);
            }
        });
    }

    public void navigation(){
        nav.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.navigation_home) {
                    intent = new Intent(getApplicationContext(), activity_homepage.class);
                } else if (item.getItemId() == R.id.navigation_encode) {
                    intent = new Intent(getApplicationContext(), activity_encode.class);
                } else if (item.getItemId() == R.id.navigation_decode) {
                    intent = new Intent(getApplicationContext(), activity_decode.class);
                } else if (item.getItemId() == R.id.navigation_about) {
                    intent = new Intent(getApplicationContext(), activity_about.class);
                }
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }
}