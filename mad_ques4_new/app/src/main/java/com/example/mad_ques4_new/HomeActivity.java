package com.example.mad_ques4_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.ComponentActivity;

import com.google.android.gms.auth.api.signin.*;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Link layout XML

        TextView welcomeText = findViewById(R.id.welcomeText);
        Button logoutBtn = findViewById(R.id.logoutBtn);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            welcomeText.setText("Welcome, " + account.getDisplayName() + "!");
        } else {
            welcomeText.setText("Welcome!");
        }

        logoutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut().addOnCompleteListener(task -> {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            });
        });
    }
}
