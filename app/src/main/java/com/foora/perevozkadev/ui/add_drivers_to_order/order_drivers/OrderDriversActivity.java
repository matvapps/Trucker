package com.foora.perevozkadev.ui.add_drivers_to_order.order_drivers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_drivers_to_order.AddDriversToOrderActivity;
import com.foora.perevozkadev.ui.add_drivers_to_order.order_drivers.adapter.OrderDriversAdapter;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class OrderDriversActivity extends BasePresenterActivity<OrderDriversMvpPresenter> implements OrderDriversMvpView {

    public static final String TAG = OrderDriversActivity.class.getSimpleName();

    public static final String ORDER_ID_EXTRA = "order_id_extra";

    private RecyclerView driverList;
    private FloatingActionButton btnAdd;
    private TextView carQuantTxtv;
    private View btnBack;

    private Order order;
    private OrderDriversAdapter orderDriversAdapter;
    private boolean canAddNewDriver = true;

    private int id;

    public static void start(Activity activity, int orderId) {
        Intent intent = new Intent(activity, OrderDriversActivity.class);
        intent.putExtra(ORDER_ID_EXTRA, orderId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_drivers);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {

        id = getIntent().getIntExtra(ORDER_ID_EXTRA, 0);

        driverList = findViewById(R.id.driver_list);
        btnBack = findViewById(R.id.btn_back);
        carQuantTxtv = findViewById(R.id.car_quant_txtv);
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(v -> {
            if (canAddNewDriver)
                AddDriversToOrderActivity.start(this, order.getId());
            else
                showErrorMessage("Вы не можете добавить больше водителей");
        });
        btnBack.setOnClickListener(v -> finish());

        driverList.setLayoutManager(new LinearLayoutManager(this));
        orderDriversAdapter = new OrderDriversAdapter(false);
        driverList.setAdapter(orderDriversAdapter);

        orderDriversAdapter.setListener(new OrderDriversAdapter.Callback() {
            @Override
            public void onItemSelectChange(Profile profile, boolean selected) {

            }

            @Override
            public void onItemRemove(Profile profile) {
                List<Integer> id = new ArrayList<>();
                id.add(profile.getUserId());
                getPresenter().removeDriversFromOrder(order.getId(), id);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().getOrder(id);
    }

    @Override
    protected OrderDriversMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        OrderDriversMvpPresenter presenter = new OrderDriversPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }


    @Override
    public void onGetOrder(Order order) {
        this.order = order;
        getPresenter().getDriversFromOrder(order.getId());
        carQuantTxtv.setText(String.format(Locale.getDefault(), "Вам необходимо %d водителя", order.getCarQuantity()));
    }


    @Override
    public void onRemoveDriversFromOrder() {
        getPresenter().getDriversFromOrder(order.getId());
    }

    @Override
    public void onGetDriversFromOrder(List<Integer> profileIds) {
        Log.d(TAG, "onGetDriversFromOrder: Profiles " + profileIds);

        orderDriversAdapter.setItems(new ArrayList<>());

        for (Integer id : profileIds)
            getPresenter().getDriverProfile(id);

        if (order.getCarQuantity() == profileIds.size()) {
            canAddNewDriver = false;
        } else {
            canAddNewDriver = true;
        }

    }

    @Override
    public void onGetDriverProfile(Profile user) {
        orderDriversAdapter.addItem(user);
    }
}
