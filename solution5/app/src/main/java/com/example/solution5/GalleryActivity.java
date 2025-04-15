package com.example.solution5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class GalleryActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<File> imageFiles = new ArrayList<>();
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = findViewById(R.id.grid_view);

        String folderPath = getIntent().getStringExtra("folderPath");
        if (folderPath != null) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith(".jpg")) {
                        imageFiles.add(file);
                    }
                }
            }
        }

        adapter = new ImageAdapter(this, imageFiles);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((adapterView, view, position, id) -> {
            File selectedImage = imageFiles.get(position);
            Intent intent = new Intent(this, ImageDetailActivity.class);
            intent.putExtra("imagePath", selectedImage.getAbsolutePath());
            startActivity(intent);
        });
    }
}
