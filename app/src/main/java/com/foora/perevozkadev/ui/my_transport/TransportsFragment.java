package com.foora.perevozkadev.ui.my_transport;

import android.os.Bundle;
import android.view.View;

import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransportsFragment extends BasePresenterFragment<MyTransportPresenter> implements MyTransportMvpView {

    public static TransportsFragment newInstance() {
        Bundle args = new Bundle();
        TransportsFragment fragment = new TransportsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MyTransportPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PreferencesHelper preferencesHelper = new SharedPrefsHelper(getContext());
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper);

        MyTransportPresenter presenter = new MyTransportPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }

    @Override
    protected void setUp(View view) {
        super.setUp(view);

        setUnBinder(ButterKnife.bind(this, view));
    }
}
