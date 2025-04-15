package com.example.solution5;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ImageDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private Button deleteButton;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageView = findViewById(R.id.detail_image_view);
        textView = findViewById(R.id.image_info);
        deleteButton = findViewById(R.id.delete_button);

        String imagePath = getIntent().getStringExtra("imagePath");
        if (imagePath != null) {
            imageFile = new File(imagePath);
            imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));
            textView.setText("Path: " + imagePath);
        }

        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Image")
                    .setMessage("Are you sure you want to delete this image?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (imageFile.delete()) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
