package com.foora.perevozkadev.ui.search_order.filter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.route.RouteListAdapter;
import com.foora.perevozkadev.ui.add_order.route.model.RouteItem;
import com.foora.perevozkadev.ui.base.BaseFragment;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.utils.custom.SpeedyLinearLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexandr.
 */
public class FilterFragment extends BaseFragment {

    public static final String TAG = FilterFragment.class.getSimpleName();

    @BindView(R.id.routes_list)
    RecyclerView routeList;

    private RouteListAdapter routeListAdapter;

    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        setUnBinder(ButterKnife.bind(this, rootView));
        return rootView;
    }

    @Override
    protected void setUp(View view) {
        routeListAdapter = new RouteListAdapter(getContext());

        routeList.setNestedScrollingEnabled(false);
        routeList.setLayoutManager(new SpeedyLinearLayoutManager(getContext(), 100f));
        routeList.setAdapter(routeListAdapter);


//
        routeListAdapter.addItem(RouteItem.Type.LOADING_PLACE);
        routeListAdapter.addItem(RouteItem.Type.UNLOADING_PLACE);
//        routeListAdapter.addItem(null);
    }


    public interface Callback {
        void onCreateNewFilter(Filter filter);
    }

}
