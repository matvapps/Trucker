package com.foora.perevozkadev.ui.my_transport;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.add_transport.AddTransportActivity;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.adapter.TransportAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransportsFragment extends BasePresenterFragment<MyTransportPresenter> implements MyTransportMvpView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_add_transport)
    FloatingActionButton btnAddTransport;

    private TransportAdapter transportAdapter;

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

    @OnClick(R.id.btn_add_transport)
    void onAddTransport() {
        AddTransportActivity.start(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        return view;
    }

    @Override
    protected void setUp(View view) {
        super.setUp(view);

        setUnBinder(ButterKnife.bind(this, view));

        transportAdapter = new TransportAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(transportAdapter);

        List<Transport> transports = new ArrayList<>();

        transports.add(new Transport());
        transports.add(new Transport());
        transports.add(new Transport());

        transportAdapter.setItems(transports);

    }
}
