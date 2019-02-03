package com.github.matvapps.setmapplacesview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.matvapps.setmapplacesview.adapter.PlaceArrayAdapter;
import com.github.matvapps.setmapplacesview.model.Place;

/**
 * Created by Alexandr.
 */
public class MapPlacesView extends FrameLayout implements PlaceArrayAdapter.Callback {

    private RecyclerView placeList;
    private View addCont;
    private PlaceArrayAdapter placeArrayAdapter;
    private ImageView btnMain;

    private Callback listener;

    private boolean isBtnBack = false;

    private int anchorId = -1;

    public MapPlacesView(@NonNull Context context) {
        super(context);
        init();
    }

    public MapPlacesView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getStyleableAttrs(attrs, context);
        init();
    }

    public MapPlacesView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getStyleableAttrs(attrs, context);
        init();
    }

    void getStyleableAttrs(AttributeSet attrs, Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MapPlacesView);

        anchorId = a.getResourceId(R.styleable.MapPlacesView_mpw_dropdownAnchor, -1);

        a.recycle();
    }

    public void setAnchorId(int anchorId) {
        placeArrayAdapter.setAnchorId(anchorId);
    }

    private void init() {
        View view = inflate(getContext(), R.layout.map_places_view, this);

        placeList = view.findViewById(R.id.place_list);
        addCont = view.findViewById(R.id.add_container);
        btnMain = view.findViewById(R.id.btn_main);

        addCont.setVisibility(GONE);

        placeList.setLayoutManager(new LinearLayoutManager(getContext()));
        placeArrayAdapter = new PlaceArrayAdapter();
        if (anchorId != -1)
            placeArrayAdapter.setAnchorId(anchorId);
        placeList.setAdapter(placeArrayAdapter);

        placeArrayAdapter.addItem(new Place());
        placeArrayAdapter.setListener(this);

        btnMain.setOnClickListener(v -> {
            if (!isBtnBack) {
                if (listener != null)
                    listener.onBtnMenuClick();
            } else {
                placeArrayAdapter.reset();
                isBtnBack = false;
                addCont.setVisibility(GONE);
                btnMain.setImageResource(R.drawable.ic_menu_btn);
                if (listener != null)
                    listener.onBtnBackClick();
            }
        });

        addCont.setOnClickListener(v -> placeArrayAdapter.addItem(new Place()));


    }

    @Override
    public void onItemInserted(int pos, Place place) {
        if (listener != null)
            listener.onItemInserted(pos, place);
        if (placeArrayAdapter.getItemCount() > 1) {
            addCont.setVisibility(VISIBLE);
            btnMain.setImageResource(R.drawable.ic_back);
            isBtnBack = true;
        }
    }

    @Override
    public void onItemRemoved(int pos, Place place) {
        if (listener != null)
            listener.onItemRemoved(pos, place);
        if (placeArrayAdapter.getItemCount() == 1) {
            if (placeArrayAdapter.getItem(0).getPlaceText() != null &&
                    !placeArrayAdapter.getItem(0).getPlaceText().isEmpty()) {
                placeArrayAdapter.addItem(new Place());
                return;
            }

            addCont.setVisibility(GONE);
            btnMain.setImageResource(R.drawable.ic_menu_btn);
            isBtnBack = false;
        }
    }

    @Override
    public void onReverseList() {
        if (listener != null)
            listener.onReverseList();
    }

    @Override
    public void onItemSelected(int pos, Place place) {
        if (listener != null)
            listener.onItemSelected(pos, place);
    }


    public void setListener(Callback listener) {
        this.listener = listener;
    }

    public interface Callback {
        void onItemInserted(int pos, Place place);
        void onItemRemoved(int pos, Place place);
        void onItemSelected(int pos, Place place);
        void onReverseList();
        void onBtnBackClick();
        void onBtnMenuClick();
    }

}
