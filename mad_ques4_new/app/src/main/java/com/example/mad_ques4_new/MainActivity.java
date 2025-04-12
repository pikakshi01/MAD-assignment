package com.example.mad_ques4_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity; // Use AppCompatActivity

import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.*;
import com.google.android.gms.tasks.Task;  // Add this import for Task class

public class MainActivity extends AppCompatActivity { // Use AppCompatActivity for compatibility
    private static final int RC_SIGN_IN = 100;  // Request code for Google Sign-In
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set the content view to activity_main.xml

        mAuth = FirebaseAuth.getInstance();  // Get an instance of FirebaseAuth

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))  // Use Web client ID from strings.xml
                .requestEmail()  // Request email address
                .build();

        // Build GoogleSignInClient with the options
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Get reference to SignIn button
        Button signInButton = findViewById(R.id.signInBtn);

        // Set OnClickListener for SignIn button to trigger signIn() method
        signInButton.setOnClickListener(view -> signIn());
    }

    // Start Google Sign-In process
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();  // Get sign-in intent from GoogleSignInClient
        startActivityForResult(signInIntent, RC_SIGN_IN);  // Start activity with request code RC_SIGN_IN
    }

    // Handle result from Google Sign-In
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);  // Use Task class
            try {
                // Successfully signed in with Google, get account details
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);  // Authenticate with Firebase using the Google account
            } catch (ApiException e) {
                // Sign-in failed, show a message to the user
                Toast.makeText(this, "Sign In Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Authenticate with Firebase using Google credentials
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);  // Create credential
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-in successful, start the HomeActivity
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();  // Finish current activity
                    } else {
                        // Sign-in failed, show a message to the user
                        Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
