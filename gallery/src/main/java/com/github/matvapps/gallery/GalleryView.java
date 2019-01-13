package com.github.matvapps.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;

/**
 * Created by Alexandr.
 */
public class GalleryView extends FrameLayout {

    private GalleryAdapter galleryAdapter;
    private RecyclerView recyclerView;

    private List<String> links;

    public GalleryView(@NonNull Context context) {
        super(context);
        init();
    }

    public GalleryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Fresco.initialize(getContext());

        View view = inflate(getContext(), R.layout.gallery_view, this);

        galleryAdapter = new GalleryAdapter();

        recyclerView = view.findViewById(R.id.gallery_list);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setAdapter(galleryAdapter);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(ViewUtils.dpToPx(8), 0, 0, 0));

        galleryAdapter.setListener(new GalleryAdapter.Callback() {
            @Override
            public void onItemClick(int pos) {
                new ImageViewer.Builder<>(getContext(), getLinks())
                        .setStartPosition(pos)
                        .show();
            }
        });

    }


    public void setMaxItems(int maxItems) {
        galleryAdapter.setMaxItems(maxItems);
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
        galleryAdapter.setItems(links);
    }


}
