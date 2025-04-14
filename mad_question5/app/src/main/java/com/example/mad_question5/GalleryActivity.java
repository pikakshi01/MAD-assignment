package com.example.mad_question5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements GalleryAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ArrayList<File> imageFiles = new ArrayList<>();
    private GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        String folderUriStr = getIntent().getStringExtra("folderUri");
        Uri folderUri = Uri.parse(folderUriStr);
        loadImagesFromUri(folderUri);
    }

    private void loadImagesFromUri(Uri uri) {
        DocumentFile dir = DocumentFile.fromTreeUri(this, uri);
        imageFiles.clear();

        if (dir != null && dir.isDirectory()) {
            for (DocumentFile file : dir.listFiles()) {
                if (file.isFile() && file.getType() != null && file.getType().startsWith("image/")) {
                    imageFiles.add(new File(file.getUri().getPath()));
                }
            }
        }

        adapter = new GalleryAdapter(imageFiles, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(File imageFile) {
        Intent intent = new Intent(this, ImageDetailActivity.class);
        intent.putExtra("imagePath", imageFile.getAbsolutePath());
        startActivity(intent);
    }
}
