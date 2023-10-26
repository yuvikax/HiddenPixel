package com.example.hiddenpixel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_userProfile extends AppCompatActivity {

    FirebaseAuth auth;
    Button logout;
    TextView user_details;
    FirebaseUser user;
    BottomNavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        nav=findViewById(R.id.bottomNavigationView);
        navigation();

        auth = FirebaseAuth.getInstance();
        logout = findViewById(R.id.logout);
        user_details = findViewById(R.id.user_details);
        user = auth.getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(),activity_login.class);
            startActivity(intent);
            finish();
        }

        else {
            user_details.setText(user.getEmail());
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),activity_login.class);
                startActivity(intent);
                finish();
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