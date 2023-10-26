package com.example.hiddenpixel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class activity_login extends AppCompatActivity {

    EditText ed1,ed2;
    Button btn1;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView tv2;

    @Override
    public void onStart() {
        super.onStart();
        // if user logged in already, user details is opened.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent welcome = new Intent(this,activity_userProfile.class);
            startActivity(welcome);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv2 = findViewById(R.id.tv2);
        mAuth = FirebaseAuth.getInstance();
        ed1 = findViewById(R.id.email);
        ed2= findViewById(R.id.password);
        btn1 = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progressBar);

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg = new Intent(activity_login.this, activity_register.class);
                startActivity(reg);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(ed1.getText());
                password = String.valueOf(ed2.getText());

                // Define regex patterns for email and password validation
                String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
                String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";

                // Create Pattern and Matcher objects for validation
                Pattern emailMatcher = Pattern.compile(emailPattern);
                Pattern passwordMatcher = Pattern.compile(passwordPattern);

                // Reset errors on EditText fields
                ed1.setError(null);
                ed2.setError(null);

                if (TextUtils.isEmpty(email)) {
                    ed1.setError("Enter Email");
                } else if (!emailMatcher.matcher(email).matches()) {
                    ed1.setError("Invalid Email Format");
                }

                if (TextUtils.isEmpty(password)) {
                    ed2.setError("Enter Password");
                } else if (!passwordMatcher.matcher(password).matches()) {
                    ed2.setError("Password must contain at least 8 characters, including at least one uppercase letter, one lowercase letter, and one digit");
                }

                if (emailMatcher.matcher(email).matches() && passwordMatcher.matcher(password).matches()) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(activity_login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                        Intent welcome = new Intent(activity_login.this, activity_homepage.class);
                                        startActivity(welcome);
                                        finish();
                                    } else {
                                        Toast.makeText(activity_login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}