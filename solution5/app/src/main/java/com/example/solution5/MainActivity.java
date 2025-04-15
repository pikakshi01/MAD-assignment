package com.example.solution5;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_FOLDER_PICKER = 102;

    private Uri selectedFolderUri;
    private Uri photoUri;
    private File photoFile;
    private ImageView imageView;
    private Button selectFolderButton, takePhotoButton, openGalleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
        selectFolderButton = findViewById(R.id.select_folder_button);
        takePhotoButton = findViewById(R.id.take_photo_button);
        openGalleryButton = findViewById(R.id.open_gallery_button);

        selectFolderButton.setOnClickListener(v -> pickFolder());

        takePhotoButton.setOnClickListener(v -> {
            if (selectedFolderUri != null) {
                captureImage();
            } else {
                showToast("Please select a folder first.");
            }
        });

        openGalleryButton.setOnClickListener(v -> {
            if (selectedFolderUri != null) {
                String folderPath = FileUtil.getFullPathFromTreeUri(selectedFolderUri, this);
                if (folderPath != null) {
                    Intent intent = new Intent(this, GalleryActivity.class);
                    intent.putExtra("folderPath", folderPath);
                    startActivity(intent);
                } else {
                    showToast("Unable to resolve folder path.");
                }
            } else {
                showToast("Please select a folder first.");
            }
        });
    }

    private void pickFolder() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_FOLDER_PICKER);
    }

    private void captureImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String fileName = "IMG_" + timeStamp;
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile(fileName, ".jpg", storageDir);

                photoUri = FileProvider.getUriForFile(
                        this,
                        getPackageName() + ".fileprovider",
                        photoFile
                );

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } catch (Exception e) {
                showToast("Error: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FOLDER_PICKER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                int takeFlags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                try {
                    getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    selectedFolderUri = uri;
                    showToast("Folder selected!");
                    captureImage();
                } catch (SecurityException e) {
                    e.printStackTrace();
                    showToast("Permission error!");
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageView.setImageURI(photoUri);
            saveToGallery(photoFile);
            if (selectedFolderUri != null) {
                saveToFolder(photoFile);
            }
        }
    }

    private void saveToGallery(File imageFile) {
        try {
            String fileName = imageFile.getName();
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera");

            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try (OutputStream out = getContentResolver().openOutputStream(uri);
                     InputStream in = new FileInputStream(imageFile)) {
                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    showToast("Saved to gallery");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Failed to save to gallery");
        }
    }

    private void saveToFolder(File imageFile) {
        try {
            String fileName = imageFile.getName();
            Uri docUri = DocumentsContract.buildDocumentUriUsingTree(
                    selectedFolderUri,
                    DocumentsContract.getTreeDocumentId(selectedFolderUri)
            );

            Uri imageUri = DocumentsContract.createDocument(
                    getContentResolver(),
                    docUri,
                    "image/jpeg",
                    fileName
            );

            if (imageUri != null) {
                try (OutputStream out = getContentResolver().openOutputStream(imageUri);
                     InputStream in = new FileInputStream(imageFile)) {
                    byte[] buf = new byte[4096];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    showToast("Saved to selected folder");
                }
            } else {
                showToast("Failed to create image URI");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showToast("Save failed: " + e.getMessage());
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
