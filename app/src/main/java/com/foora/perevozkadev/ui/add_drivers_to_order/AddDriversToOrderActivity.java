package com.foora.perevozkadev.ui.add_drivers_to_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.foora.perevozkadev.ui.add_drivers_to_order.order_drivers.adapter.OrderDriversAdapter;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddDriversToOrderActivity extends BasePresenterActivity<AddDriversToOrderMvpPresenter> implements AddDriversToOrderMvpView {

    public static final String TAG = AddDriversToOrderActivity.class.getSimpleName();

    public static final String ORDER_ID_EXTRA = "order_id_extra";

    private View btnBack;
    private RecyclerView driverList;
    private TextView carQuantTxtv;
    private Button btnMain;

    private Order order;
    private OrderDriversAdapter orderDriversAdapter;
    private List<Profile> drivers;

    public static void start(Activity activity, int orderId) {
        Intent intent = new Intent(activity, AddDriversToOrderActivity.class);
        intent.putExtra(ORDER_ID_EXTRA, orderId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drivers_to_order);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        btnBack = findViewById(R.id.btn_back);
        driverList = findViewById(R.id.driver_list);
        carQuantTxtv = findViewById(R.id.car_quant_txtv);
        btnMain = findViewById(R.id.btn_main);

        int orderId = getIntent().getIntExtra(ORDER_ID_EXTRA, 0);
        getPresenter().getOrder(orderId);

        drivers = new ArrayList<>();

        btnBack.setOnClickListener(v -> finish());
        driverList.setLayoutManager(new LinearLayoutManager(this));
        orderDriversAdapter = new OrderDriversAdapter(true);
        orderDriversAdapter.setListener(new OrderDriversAdapter.Callback() {
            @Override
            public void onItemSelectChange(Profile profile, boolean selected) {
                if (selected)
                    drivers.add(profile);
                else
                    drivers.remove(profile);

            }

            @Override
            public void onItemRemove(Profile profile) {

            }
        });

        btnMain.setOnClickListener(v -> {
            Log.d(TAG, "setUp: " + drivers);

            List<Integer> array = new ArrayList<>();
            for (int i = 0; i < drivers.size(); i++) {
                array.add(drivers.get(i).getUserId());
            }

            if (array.size() >= order.getCarQuantity())
                getPresenter().addDriversToOrder(order.getId(), array);
            else
                showErrorMessage(String.format(Locale.getDefault(), "Вы не можете добавить больше %d автомобилей", order.getCarQuantity()));

        });

        driverList.setAdapter(orderDriversAdapter);

        getPresenter().getStaff();
    }


    @Override
    protected AddDriversToOrderMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        AddDriversToOrderMvpPresenter presenter = new AddDriversToOrderPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }


    @Override
    public void onGetStaff(List<Profile> users) {
        List<Profile> drivers = new ArrayList<>();
        for (Profile user : users) {
            if (user.getGroups().contains("driver")) {
                drivers.add(user);
            }
        }

        orderDriversAdapter.setItems(drivers);
    }

    @Override
    public void onGetMyOrders(List<Order> orders) {

    }

    @Override
    public void onGetOrder(Order order) {
        this.order = order;
        carQuantTxtv.setText(String.format(Locale.getDefault(), "Вам необходимо %d водителя", order.getCarQuantity()));
    }

    @Override
    public void onAddDriversToOrder() {
        finish();
    }

}
