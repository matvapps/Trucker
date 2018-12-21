package com.foora.perevozkadev.ui.search_order.orders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.search_order.SearchOrderMvpView;
import com.foora.perevozkadev.ui.search_order.SearchOrderPresenter;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Alexandr.
 */
public class OrdersFragment extends BasePresenterFragment<SearchOrderPresenter> implements SearchOrderMvpView {

    public static final String TAG = OrdersFragment.class.getSimpleName();

    private final static String FILTER_KEY = "filter_key";

    private Filter filter;

    @BindView(R.id.order_list)
    RecyclerView orderListView;

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

        if (savedInstanceState != null)
            filter = (Filter) savedInstanceState.getSerializable(FILTER_KEY);

        return rootView;
    }

    @Override
    protected SearchOrderPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PreferencesHelper preferencesHelper = new SharedPrefsHelper(getContext());
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper);

        SearchOrderPresenter searchOrderPresenter = new SearchOrderPresenter<>(dataManager, AndroidSchedulers.mainThread());
        searchOrderPresenter.onAttach(this);

        return searchOrderPresenter;
    }

    @Override
    protected void setUp(View view) {
        ordersAdapter = new OrdersAdapter();

        orderListView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderListView.setAdapter(ordersAdapter);

        getPresenter().getOrders();


//        List<Order> orders = new ArrayList<>();
//        orders.add(new Order());
//        orders.add(new Order());
//        orders.add(new Order());

//        ordersAdapter.setItems(orders);

    }


    @Override
    public void onGetOrders(List<Order> orders) {
        ordersAdapter.setItems(orders);
    }
}
