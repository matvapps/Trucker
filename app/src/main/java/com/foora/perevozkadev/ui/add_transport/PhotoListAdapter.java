package com.foora.perevozkadev.ui.add_transport;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = PhotoListAdapter.class.getSimpleName();

    private List<File> photos;


    public PhotoListAdapter() {
        photos = new ArrayList<>();
    }

    public void addItem(File photo) {
        photos.add(photo);
        notifyItemInserted(photos.size() - 1);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo, viewGroup, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        PhotoViewHolder photoViewHolder = (PhotoViewHolder) viewHolder;
        photoViewHolder.onBind(i);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public File getItem(int pos) {
        return photos.get(pos);
    }

    public List<File> getItems() {
        return photos;
    }

    private class PhotoViewHolder extends BaseViewHolder {

        private RoundedImageView imageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.photo);

        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            File photo = getItem(position);

            Log.d(TAG, "onBind: view on position " + position + "file path: " + photo.getAbsolutePath());

            Picasso.get()
                    .load(photo)
                    .centerCrop()
                    .fit()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "onSuccess: ");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "onError: " + e.getMessage(), e);
                        }
                    });

        }
    }

}
