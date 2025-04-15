package com.example.mad_question5;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;
import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class ImageDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView detailText;
    private Button deleteButton;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageView = findViewById(R.id.detailImageView);
        detailText = findViewById(R.id.detailText);
        deleteButton = findViewById(R.id.deleteButton);

        // Retrieve image URI passed from previous activity
        String uriStr = getIntent().getStringExtra("imageUri");
        if (uriStr != null) {
            imageUri = Uri.parse(uriStr);
            imageView.setImageURI(imageUri);
            showImageDetails(imageUri);
        }

        deleteButton.setOnClickListener(v -> showDeleteConfirmation());
    }

    // Show image details
    private void showImageDetails(Uri uri) {
        String path = uri.getPath();
        File imageFile = new File(path);
        String info = "Name: " + imageFile.getName() + "\nSize: " + imageFile.length() + " bytes\nPath: " + path;
        detailText.setText(info);
    }

    // Show delete confirmation dialog
    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Image")
                .setMessage("Are you sure you want to delete this image?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    File imageFile = new File(imageUri.getPath());
                    if (imageFile.delete()) {
                        finish();
                    } else {
                        new AlertDialog.Builder(this)
                                .setMessage("Failed to delete image")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
