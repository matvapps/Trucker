package com.foora.perevozkadev.ui.my_orders;

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
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.my_order_info.MyOrderInfoActivity;
import com.foora.perevozkadev.ui.search_order.orders.OrdersAdapter;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.ItemSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Alexandr.
 */
public class MyOrderFragment extends BasePresenterFragment<MyOrdersPresenter> implements MyOrdersMvpView {

    public static final String TAG = MyOrderFragment.class.getSimpleName();

    @BindView(R.id.order_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    public static final String TYPE_ACTIVE = "active";
    public static final String TYPE_FINISHED = "finished";

    private static final String TYPE_KEY = "type_key";

    private OrdersAdapter ordersAdapter;
    private String fragmentType;

    public static MyOrderFragment newInstance(String type) {
        Bundle args = new Bundle();
        MyOrderFragment fragment = new MyOrderFragment();
        args.putString(TYPE_KEY, type);
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

        fragmentType = getArguments().getString(TYPE_KEY);

        setUnBinder(ButterKnife.bind(this, view));

        ordersAdapter = new OrdersAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ItemSpacingDecoration(ViewUtils.dpToPx(8), ViewUtils.dpToPx(8), ViewUtils.dpToPx(8), 0));
        recyclerView.setAdapter(ordersAdapter);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));

        ordersAdapter.setListener(order -> MyOrderInfoActivity.start(getActivity(), order.getId()));

//        ordersAdapter.setListener((pos, transport) -> TransportActivity.start(getActivity(), transport.getId()));

//        getPresenter().getUserOrders();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getUserOrders();
    }

    @Override
    public void onGetUserOrders(List<Order> orders) {
        Log.d(TAG, "onGetUserOrders: " + orders);
        ordersAdapter.setItems(new ArrayList<>());

        for (Order order : orders) {
            if (fragmentType.equals(TYPE_FINISHED)) {
                if (order.getStatus().equals("finished")
                        || order.getStatus().equals("Груз доставлен")) {
                    if (!ordersAdapter.getItems().contains(order))
                        ordersAdapter.addItem(order);
                }
            } else {
                if (!order.getStatus().equals("finished")
                        || order.getStatus().equals("Груз доставлен")) {
                    if (!ordersAdapter.getItems().contains(order))
                        ordersAdapter.addItem(order);
                }
            }
        }
        getPresenter().getExecutorOrders();
    }

    @Override
    public void onGetExecutorOrders(List<Order> orders) {
        Log.d(TAG, "onGetExecutorOrders: " + orders);

        for (Order order : orders) {
            if (fragmentType.equals(TYPE_FINISHED)) {
                if (order.getStatus().equals("finished")
                        || order.getStatus().equals("Груз доставлен")) {
                    if (!ordersAdapter.getItems().contains(order))
                        ordersAdapter.addItem(order);
                }
            } else {
                if (!order.getStatus().equals("finished")
                        || order.getStatus().equals("Груз доставлен")) {
                    if (!ordersAdapter.getItems().contains(order))
                        ordersAdapter.addItem(order);
                }
            }
        }
    }
}
