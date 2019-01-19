package com.foora.perevozkadev.ui.add_order.route;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.add_order.route.model.RouteItem;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.places.PlaceAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class RouteFragment extends BaseFragment {

    public static final String TAG = RouteFragment.class.getSimpleName();

    @BindView(R.id.btn_main)
    Button btnMain;

    @BindView(R.id.routes_list)
    LinearLayout loadList;
    @BindView(R.id.routes_list_2)
    LinearLayout unloadList;

    private Callback listener;

    private List<Place> loadingPlaces;
    private List<Place> unloadingPlaces;

    public static RouteFragment newInstance() {
        Bundle args = new Bundle();
        RouteFragment fragment = new RouteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_route, container, false);
        setUnBinder(ButterKnife.bind(this, rootView));

        return rootView;
    }

    @OnClick(R.id.btn_main)
    void onClick() {
        if (listener != null) {

            for (int i = 0; i < loadList.getChildCount(); i++) {
                PlaceAutoCompleteTextView placeAutoCompleteTextView = (PlaceAutoCompleteTextView) loadList.getChildAt(i);

                String name = placeAutoCompleteTextView.getText();

                if (!name.isEmpty())
                    loadingPlaces.add(new Place(i, name));
            }

            for (int i = 0; i < unloadList.getChildCount(); i++) {
                PlaceAutoCompleteTextView placeAutoCompleteTextView = (PlaceAutoCompleteTextView) unloadList.getChildAt(i);

                String name = placeAutoCompleteTextView.getText();

                if (!name.isEmpty())
                    unloadingPlaces.add(new Place(i, name));
            }

            if (loadingPlaces.size() == 0) {
                onError("Заполните место погрузки");
                return;
            }

            if (unloadingPlaces.size() == 0) {
                onError("Заполните место выгрузки");
                return;
            }

            listener.onReceiveRoutes(loadingPlaces, unloadingPlaces);
        }
    }

    @OnClick(R.id.add_route)
    void onAddLoadRoute() {
        loadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.LOADING_PLACE));
    }

    @OnClick(R.id.add_route_2)
    void onAddUnLoadRoute() {
        unloadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.UNLOADING_PLACE));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (Callback) context;
    }

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    @Override
    protected void setUp(View view) {
        loadingPlaces = new ArrayList<>();
        unloadingPlaces = new ArrayList<>();

        loadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.LOADING_PLACE));
        unloadList.addView(getPlaceAutoCompleteTxtv(RouteItem.Type.UNLOADING_PLACE));

    }

    private PlaceAutoCompleteTextView getPlaceAutoCompleteTxtv(RouteItem.Type type) {
        PlaceAutoCompleteTextView placeAutoCompleteTextView = new PlaceAutoCompleteTextView(getContext());
        switch (type) {
            case LOADING_PLACE:

                break;
            case UNLOADING_PLACE:

                break;
        }

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, ViewUtils.dpToPx(8), 0, 0);

        placeAutoCompleteTextView.setLayoutParams(layoutParams);

        return placeAutoCompleteTextView;
    }

    private boolean containsItemWithId(List<Place> items, int id) {
        for (Place item : items) {
            if (item.getId() == id)
                return true;
        }
        return false;
    }


    public interface Callback {
        void onReceiveRoutes(List<Place> loadingPlaces, List<Place> unloadingPlaces);
    }

}


