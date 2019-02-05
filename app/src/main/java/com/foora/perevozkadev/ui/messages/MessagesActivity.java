package com.foora.perevozkadev.ui.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.nav.BaseNavPresenterActivity;

import java.util.List;

import butterknife.ButterKnife;

public class MessagesActivity extends BaseNavPresenterActivity<MessagesMvpPresenter> implements MessagesMvpView {

    public static final String TAG = MessagesActivity.class.getSimpleName();

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MessagesPagerAdapter pagerAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MessagesActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUnBinder(ButterKnife.bind(this));

    }

    @Override
    protected int getMenuResource() {
        return R.drawable.ic_menu;
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_messages;
    }

    @Override
    protected String getTitleStr() {
        return "Сообщения";
    }

    @Override
    protected void setUp() {
        super.setUp();
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        pagerAdapter = new MessagesPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    protected MessagesMvpPresenter createPresenter() {
        return null;
    }


    @Override
    public void onGetUserRequests(List<OrderRequest> orderRequests) {

    }

    @Override
    public void onGetToUserRequests(List<OrderRequest> orderRequests) {

    }

    @Override
    public void onGetOrderByRequest(OrderRequest orderRequest, Order order) {

    }

}
