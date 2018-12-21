package com.perevozka.foora.routedisplayview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Matvienko on 14.12.2018.
 */
public class RouteDisplayView extends FrameLayout {

    private static final String TAG = RouteDisplayView.class.getSimpleName();

    private RecyclerView routeList;
    private RouteListAdapter routeListAdapter;
    private boolean useFullView;

    private List<RouteItem> routes;

    public RouteDisplayView(@NonNull Context context) {
        super(context);
        init();
    }

    public RouteDisplayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getStyleableAttrs(attrs, context);
        init();
    }

    public RouteDisplayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getStyleableAttrs(attrs, context);
        init();
    }

    void getStyleableAttrs(AttributeSet attrs, Context context) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RouteDisplayView);

        useFullView = a.getBoolean(R.styleable.RouteDisplayView_rdv_useFullView, true);

        a.recycle();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.route_display_view, this);
        routeList = view.findViewById(R.id.routes_list);

        routeListAdapter = new RouteListAdapter();

        routeList.setLayoutManager(new LinearLayoutManager(getContext()));
        routeList.setNestedScrollingEnabled(true);
        routeList.setAdapter(routeListAdapter);

        if (isInEditMode()) {
            List<RouteItem> items = new ArrayList<>();
            items.add(new RouteItem("12.03.2018", "Запорожье", "UA"));
            items.add(new RouteItem("13.03.2018", "Харьков", "UA"));
            items.add(new RouteItem("15.03.2018", "Львов", "UA"));

            routeListAdapter.setItems(items);
        }

        routeListAdapter.displayAllItems(useFullView);

    }

    public void setRoutes(List<RouteItem> routes) {
        this.routes = routes;
        routeListAdapter.setItems(routes);
    }

}
