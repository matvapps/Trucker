package com.foora.perevozkadev.ui.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.nav.BaseNavPresenterActivity;
import com.foora.perevozkadev.ui.staff.adapter.EmployeesPagerAdapter;
import com.foora.perevozkadev.ui.profile.model.Profile;

import java.util.List;

import butterknife.ButterKnife;

public class EmployeesActivity extends BaseNavPresenterActivity<EmployeesMvpPresenter> implements EmployeesMvpView {

    public static final String TAG = EmployeesActivity.class.getSimpleName();

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private EmployeesPagerAdapter pagerAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, EmployeesActivity.class);
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
        return R.layout.activity_employees;
    }

    @Override
    protected String getTitleStr() {
        return "Мои сотрудники";
    }

    @Override
    protected void setUp() {
        super.setUp();
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        pagerAdapter = new EmployeesPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        pagerAdapter = new EmployeesPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    public void refresh(){
        onResume();
    }

    @Override
    protected EmployeesMvpPresenter createPresenter() {
        return null;
    }


    @Override
    public void onReceiveEmployees(List<Profile> profiles) {

    }

    @Override
    public void onGetEmployeesArchive(List<Profile> profiles) {

    }

    @Override
    public void onRestoreFromArchive() {

    }
}
