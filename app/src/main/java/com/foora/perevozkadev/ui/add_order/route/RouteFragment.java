package com.foora.perevozkadev.ui.add_order.route;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.add_order.route.model.RouteItem;
import com.foora.perevozkadev.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import foora.perevozka.com.choosecityview.ChooseCityView;

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
                ChooseCityView view = (ChooseCityView) loadList.getChildAt(i);
                String name = String.format("%s, %s, %s", view.getCountry(), view.getRegion(), view.getCity());
                if (view.getTitle().equals("Место погрузки") &&
                        view.getCity() != null) {
                    loadingPlaces.add(new Place(i, name));
                }
            }

            for (int i = 0; i < unloadList.getChildCount(); i++) {
                ChooseCityView view = (ChooseCityView) unloadList.getChildAt(i);
                String name = String.format("%s, %s, %s", view.getCountry(), view.getRegion(), view.getCity());
                if (view.getTitle().equals("Место выгрузки") &&
                        view.getCity() != null) {
                    unloadingPlaces.add(new Place(i, name));
                }
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
        loadList.addView(getChooseCityView(RouteItem.Type.LOADING_PLACE));
    }

    @OnClick(R.id.add_route_2)
    void onAddUnLoadRoute() {
        unloadList.addView(getChooseCityView(RouteItem.Type.UNLOADING_PLACE));
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

        loadList.addView(getChooseCityView(RouteItem.Type.LOADING_PLACE));
        unloadList.addView(getChooseCityView(RouteItem.Type.UNLOADING_PLACE));

    }

    private ChooseCityView getChooseCityView(RouteItem.Type type) {
        ChooseCityView chooseCityView = new ChooseCityView(getContext());
        switch (type) {
            case LOADING_PLACE:
                chooseCityView.setTitle("Место погрузки");
                break;
            case UNLOADING_PLACE:
                chooseCityView.setTitle("Место выгрузки");
                break;
        }
        chooseCityView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return chooseCityView;
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


