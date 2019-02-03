package com.foora.perevozkadev.ui.messages_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;

import butterknife.ButterKnife;

public class MessagesInfoActivity extends BasePresenterActivity<MessagesInfoMvpPresenter> implements MessagesInfoMvpView {

    public static final String TAG = MessagesInfoActivity.class.getSimpleName();


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MessagesInfoActivity.class);
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
    protected MessagesInfoMvpPresenter createPresenter() {
        return null;
    }


}
