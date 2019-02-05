package com.foora.perevozkadev.ui.search_order.orders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.db.model.FilterJson;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.order.OrderFragment;
import com.foora.perevozkadev.ui.search_order.SearchOrderMvpView;
import com.foora.perevozkadev.ui.search_order.SearchOrderPresenter;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.ItemSpacingDecoration;
import com.google.gson.Gson;
import com.paginate.Paginate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Alexandr.
 */
public class OrdersFragment extends BasePresenterFragment<SearchOrderPresenter> implements SearchOrderMvpView, Paginate.Callbacks {

    public static final String TAG = OrdersFragment.class.getSimpleName();

    private final static String FILTER_KEY = "filter_key";

    private Filter filter;

    @BindView(R.id.order_list)
    RecyclerView orderListView;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private OrdersAdapter ordersAdapter;

    public static OrdersFragment newInstance(Filter filter) {
        Bundle args = new Bundle();
        OrdersFragment fragment = new OrdersFragment();
        args.putSerializable(FILTER_KEY, filter);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_orders, container, false);
        setUnBinder(ButterKnife.bind(this, rootView));

        if (getArguments() != null)
            filter = (Filter) getArguments().getSerializable(FILTER_KEY);

        return rootView;
    }

    @Override
    protected SearchOrderPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(getContext());
        LocalRepo localRepo = new LocalRepoImpl(getContext());
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        SearchOrderPresenter searchOrderPresenter = new SearchOrderPresenter<>(dataManager, AndroidSchedulers.mainThread());
        searchOrderPresenter.onAttach(this);

        return searchOrderPresenter;
    }

    @Override
    protected void setUp(View view) {
        ordersAdapter = new OrdersAdapter();

        orderListView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderListView.addItemDecoration(
                new ItemSpacingDecoration(ViewUtils.dpToPx(8),
                        ViewUtils.dpToPx(8),
                        ViewUtils.dpToPx(8),
                        0));
        orderListView.setAdapter(ordersAdapter);

        swipeRefreshLayout.setOnRefreshListener(
                () -> getPresenter().getOrders(filter.getWeightFrom(),
                        filter.getWeightTo(),
                        filter.getVolumeFrom(),
                        filter.getVolumeTo()));

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));

        ordersAdapter.setListener(order -> OrderFragment.newInstance(new Gson().toJson(order)).show(getFragmentManager(), OrderFragment.TAG));

        Paginate.with(orderListView, this)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .build();


        Log.d(TAG, "setUp: " + filter);

        getPresenter().getOrders(filter.getWeightFrom(), filter.getWeightTo(), filter.getVolumeFrom(), filter.getVolumeTo());


    }

    @Override
    public void onGetOrders(List<Order> orders) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
        ordersAdapter.setItems(orders);
    }

    @Override
    public void onGetFilters(List<FilterJson> filters) {

    }

    @Override
    public synchronized void onLoadMore() {
//        showMessage("Load more");
    }

    @Override
    public synchronized boolean isLoading() {
        return false;
    }

    @Override
    public synchronized boolean hasLoadedAllItems() {
        return false;
    }
}
