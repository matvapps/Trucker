package com.github.matvapps.gallery;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexandr.
 */
public class GalleryView extends FrameLayout {

    private GalleryAdapter galleryAdapter;
    private RecyclerView recyclerView;

    private List<String> links;

    private int maxItems = 3;

    public GalleryView(@NonNull Context context) {
        super(context);
        init();
    }

    public GalleryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initStyleableAttrs(context, attrs);
        init();
    }

    public GalleryView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyleableAttrs(context, attrs);
        init();
    }

    private void initStyleableAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GalleryView);

        maxItems = a.getInt(R.styleable.GalleryView_gv_maxItems, maxItems);

        a.recycle();
    }

    private void init() {
        if (!isInEditMode())
            Fresco.initialize(getContext());

        links = new ArrayList<>();

        View view = inflate(getContext(), R.layout.gallery_view, this);

        galleryAdapter = new GalleryAdapter();

        setMaxItems(maxItems);

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


        if (isInEditMode()) {
            List<String> photoLinks = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                photoLinks.add("");
            }
            galleryAdapter.setItems(photoLinks);
        }

    }


    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
        galleryAdapter.setMaxItems(maxItems);
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
        galleryAdapter.setItems(links);
    }

    public void addLink(String link) {
        this.links.add(link);
        galleryAdapter.addItem(link);
    }


}
