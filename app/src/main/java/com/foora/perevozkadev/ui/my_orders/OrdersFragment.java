package com.foora.perevozkadev.ui.my_orders;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.search_order.orders.OrdersAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Alexandr.
 */
public class OrdersFragment extends BasePresenterFragment<MyOrdersPresenter> implements MyOrdersMvpView {

    public static final String TAG = OrdersFragment.class.getSimpleName();

    @BindView(R.id.order_list)
    RecyclerView recyclerView;


    private OrdersAdapter ordersAdapter;

    public static OrdersFragment newInstance() {
        Bundle args = new Bundle();
        OrdersFragment fragment = new OrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MyOrdersPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(getContext());
        LocalRepo localRepo = new LocalRepoImpl(getContext());
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);
        MyOrdersPresenter presenter = new MyOrdersPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        return view;
    }

    @Override
    protected void setUp(View view) {
        super.setUp(view);

        setUnBinder(ButterKnife.bind(this, view));

        ordersAdapter = new OrdersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(ordersAdapter);

//        ordersAdapter.setListener((pos, transport) -> TransportActivity.start(getActivity(), transport.getId()));

        getPresenter().getUserOrders();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getUserOrders();
    }

    @Override
    public void onGetUserOrders(List<Order> orders) {
        Log.d(TAG, "onGetUserOrders: " + orders);

        ordersAdapter.setItems(orders);
    }
}
