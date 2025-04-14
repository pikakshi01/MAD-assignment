package com.example.mad_question5;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {

    private ArrayList<File> imageFiles;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(File imageFile);
    }

    public GalleryAdapter(ArrayList<File> imageFiles, OnItemClickListener onItemClickListener) {
        this.imageFiles = imageFiles;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        File imageFile = imageFiles.get(position);
        holder.imageView.setImageURI(Uri.fromFile(imageFile));

        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(imageFile);
        });
    }

    @Override
    public int getItemCount() {
        return imageFiles.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
