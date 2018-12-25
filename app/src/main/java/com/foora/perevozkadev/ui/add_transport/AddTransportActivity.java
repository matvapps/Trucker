package com.foora.perevozkadev.ui.add_transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.utils.custom.ViewPagerNoScroll;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddTransportActivity extends BasePresenterActivity<AddTransportMvpPresenter> implements AddTransportMvpView {

    public static final String TAG = AddTransportActivity.class.getSimpleName();

    private ViewPagerNoScroll viewPager;
    private TabLayout tabLayout;

    private AddTransportPagerAdapter addTransportPagerAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AddTransportActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transport);

        setUnBinder(ButterKnife.bind(this));
    }

    @Override
    protected void setUp() {
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);


        addTransportPagerAdapter = new AddTransportPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(addTransportPagerAdapter);
        viewPager.setPagingEnabled(false);

//        try {
//            Field mScroller;
//            mScroller = ViewPager.class.getDeclaredField("mScroller");
//            mScroller.setAccessible(true);
//            FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), new LinearInterpolator());
//            mScroller.set(viewPager, scroller);
//        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
//        }

    }


    @Override
    protected AddTransportMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PreferencesHelper prefs = new SharedPrefsHelper(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs);

        AddTransportPresenter addTransportPresenter = new AddTransportPresenter(dataManager, AndroidSchedulers.mainThread());
        addTransportPresenter.onAttach(this);

        return addTransportPresenter;
    }


}
