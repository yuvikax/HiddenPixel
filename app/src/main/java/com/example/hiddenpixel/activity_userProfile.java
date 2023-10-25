//package com.example.hiddenpixel;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.google.firebase.Firebase;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class activity_userProfile extends AppCompatActivity {
//
//    FirebaseAuth auth;
//    Button logout;
//    TextView user_details;
//    FirebaseUser user;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_profile);
//
//        auth = FirebaseAuth.getInstance();
//        logout = findViewById(R.id.logout);
//        user_details = findViewById(R.id.user_details);
//        user = auth.getCurrentUser();
//
//        if (user == null) {
//            Intent intent = new Intent(getApplicationContext(),activity_login.class);
//            startActivity(intent);
//            finish();
//        }
//
//        else {
//            user_details.setText(user.getEmail());
//        }
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(),activity_login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }
//}