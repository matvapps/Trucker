package com.foora.perevozkadev.ui.docs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.network.model.FileResponse;
import com.foora.perevozkadev.ui.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Matvienko on 05.02.2019.
 */
public class DocsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = DocsAdapter.class.getSimpleName();

    private List<FileResponse> items;

    public DocsAdapter() {
        items = new ArrayList<>();
    }

    public void setItems(List<FileResponse> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_doc_preview, viewGroup, false);

        int height = viewGroup.getMeasuredWidth() / 2;

        ViewGroup.LayoutParams params = rootView.getLayoutParams();
        params.height = height;
        rootView.setLayoutParams(params);
        return new FileViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FileViewHolder fileViewHolder = (FileViewHolder) viewHolder;
        fileViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public FileResponse getItem(int position) {
        return items.get(position);
    }

    private class FileViewHolder extends BaseViewHolder {

        ImageView fileImage;

        public FileViewHolder(View itemView) {
            super(itemView);

            fileImage = itemView.findViewById(R.id.image_view);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            String url = "http://dev.perevozka.me" + getItem(position).getFilePath();

            Log.d(TAG, "onBind: " + url);

            Picasso.get()
                    .load(url)
                    .into(fileImage);

        }
    }

    public interface Callback {
        void onItemClick(int position, FileResponse fileResponse);
    }

}
