package com.example.mad_question5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<Uri> imageUris;
    private GalleryAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = findViewById(R.id.gridView);
        imageUris = new ArrayList<>();

        // Load images from external storage
        loadImagesFromFolder();

        galleryAdapter = new GalleryAdapter(imageUris, this);
        gridView.setAdapter(galleryAdapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Uri imageUri = imageUris.get(position);
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            startActivity(intent);
        });
    }

    private void loadImagesFromFolder() {
        File storageDir = getExternalFilesDir("Pictures"); // Pictures folder
        if (storageDir != null) {
            File[] files = storageDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isImageFile(file)) {
                        imageUris.add(Uri.fromFile(file));
                    }
                }
            }
        }
    }

    private boolean isImageFile(File file) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif"};
        String fileName = file.getName().toLowerCase();
        for (String ext : imageExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
