package com.example.ques4_mad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class HomeActivity extends AppCompatActivity {

    TextView welcomeText;
    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeText = findViewById(R.id.welcome_text);
        logoutBtn = findViewById(R.id.logout_button);

        String name = getIntent().getStringExtra("name");
        Log.d("HomeActivity", "Received name: " + name);

        if (name != null) {
            welcomeText.setText("Welcome, " + name + "!");
        } else {
            welcomeText.setText("Welcome!");
            Log.d("HomeActivity", "Name not found in intent!");
        }

        logoutBtn.setOnClickListener(view -> {
            Log.d("HomeActivity", "Logout button clicked");
            GoogleSignInClient signInClient = GoogleSignIn.getClient(
                    this,
                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
            );
            signInClient.signOut().addOnCompleteListener(task -> {
                Log.d("HomeActivity", "Signed out successfully");
                startActivity(new Intent(HomeActivity.this, MainActivity.class));  // Go back to sign-in screen
                finish();
            });
        });
    }
}
