package com.example.solution5;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;

public class FileUtil {
    public static String getFullPathFromTreeUri(Uri uri, Context context) {
        if (uri == null) return null;

        final String docId = DocumentsContract.getTreeDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        if ("primary".equalsIgnoreCase(type)) {
            return context.getExternalFilesDir(null).getAbsolutePath() + "/" + split[1];
        } else {
            File[] externalDirs = context.getExternalFilesDirs(null);
            if (externalDirs.length > 1) {
                return externalDirs[1].getAbsolutePath() + "/" + split[1];
            }
        }
        return null;
    }
}
