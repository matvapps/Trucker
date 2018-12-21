package com.foora.perevozkadev.ui.my_transport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;

import butterknife.ButterKnife;

public class MyTransportActivity extends BasePresenterActivity<MyTransportMvpPresenter> implements MyTransportMvpView {

    public static final String TAG = MyTransportActivity.class.getSimpleName();


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MyTransportActivity.class);
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
    protected MyTransportMvpPresenter createPresenter() {
        return null;
    }


}
