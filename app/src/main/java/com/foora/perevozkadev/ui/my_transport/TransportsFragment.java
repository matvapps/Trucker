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
import com.foora.perevozkadev.ui.transport.TransportAdapter;
import com.foora.perevozkadev.ui.transport.TransportActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransportsFragment extends BasePresenterFragment<MyTransportPresenter> implements MyTransportMvpView {

    private static final String TYPE_KEY = "transport_type";

    public static final String GARAGE_TYPE = "garage";
    public static final String ARCHIVE_TYPE = "archive";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_add)
    FloatingActionButton btnAddTransport;

    private TransportAdapter transportAdapter;

    public static TransportsFragment newInstance(String type) {
        Bundle args = new Bundle();
        TransportsFragment fragment = new TransportsFragment();
        args.putString(TYPE_KEY, type);
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

    @OnClick(R.id.btn_add)
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

        String screenType = getArguments().getString(TYPE_KEY, GARAGE_TYPE);

        transportAdapter = new TransportAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(transportAdapter);

        transportAdapter.setListener((pos, transport) -> TransportActivity.start(getActivity(), transport.getId()));

        switch (screenType) {
            case GARAGE_TYPE: {
                getPresenter().getUserTransport();
                break;
            }
            case ARCHIVE_TYPE: {
                btnAddTransport.hide();
                break;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().getUserTransport();
    }

    @Override
    public void onGetTransports(List<Transport> transports) {
        transportAdapter.setItems(transports);
    }
}
