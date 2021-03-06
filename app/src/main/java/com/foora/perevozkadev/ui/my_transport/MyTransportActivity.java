package com.foora.perevozkadev.ui.my_transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.nav.BaseNavPresenterActivity;
import com.foora.perevozkadev.ui.my_transport.model.Transport;

import java.util.List;

import butterknife.ButterKnife;

public class MyTransportActivity extends BaseNavPresenterActivity<MyTransportMvpPresenter> implements MyTransportMvpView {

    public static final String TAG = MyTransportActivity.class.getSimpleName();

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MyTransportPagerAdapter pagerAdapter;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MyTransportActivity.class);
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
        return R.layout.activity_my_transport;
    }

    @Override
    protected String getTitleStr() {
        return "Мой транспорт";
    }

    @Override
    protected void setUp() {
        super.setUp();
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        pagerAdapter = new MyTransportPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        pagerAdapter = new MyTransportPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    public void refresh() {
        onResume();
    }

    @Override
    protected MyTransportMvpPresenter createPresenter() {
        return null;
    }


    @Override
    public void onGetTransports(List<Transport> transports) {

    }

    @Override
    public void onGetTransportArchive(List<Transport> transports) {

    }

    @Override
    public void onAddTransportToArchive(int transportId) {

    }

    @Override
    public void onRemoveTransportFromArchive(int transportId) {

    }
}
