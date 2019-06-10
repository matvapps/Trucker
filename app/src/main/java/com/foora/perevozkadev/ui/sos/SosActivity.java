package com.foora.perevozkadev.ui.sos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;

import butterknife.ButterKnife;

public class SosActivity extends BasePresenterActivity<SosMvpPresenter> implements SosMvpView {

    public static final String TAG = SosActivity.class.getSimpleName();


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SosActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUnBinder(ButterKnife.bind(this));

    }

    @Override
    protected void setUp() {


    }


    @Override
    protected SosMvpPresenter createPresenter() {
        return null;
    }


}
