package com.foora.perevozkadev.ui.my_orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterNavActivity;

import java.util.List;

import butterknife.ButterKnife;

public class MyOrdersActivity extends BasePresenterNavActivity<MyOrdersMvpPresenter> implements MyOrdersMvpView {

    public static final String TAG = MyOrdersActivity.class.getSimpleName();

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MyOrdersPagerAdapter pagerAdapter;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MyOrdersActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnBinder(ButterKnife.bind(this));

    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_my_orders;
    }

    @Override
    protected String getTitleStr() {
        return "Мои закакзы";
    }

    @Override
    protected void setUp() {
        super.setUp();

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        pagerAdapter = new MyOrdersPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

    }


    @Override
    protected MyOrdersMvpPresenter createPresenter() {
        return null;
    }


    @Override
    public void onGetUserOrders(List<Order> orders) {

    }
}
