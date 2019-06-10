package com.foora.perevozkadev.ui.my_transport.transport;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_transport.AddTransportActivity;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.my_transport.MyTransportActivity;
import com.foora.perevozkadev.ui.my_transport.MyTransportMvpView;
import com.foora.perevozkadev.ui.my_transport.MyTransportPresenter;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.transport.TransportActivity;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.ItemSpacingDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TransportsFragment extends BasePresenterFragment<MyTransportPresenter>
        implements MyTransportMvpView, TransportAdapter.Callback {

    private static final String TAG = TransportsFragment.class.getSimpleName();

    private static final String TYPE_KEY = "transport_type";

    public static final String GARAGE_TYPE = "garage";
    public static final String ARCHIVE_TYPE = "archive";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_add)
    FloatingActionButton btnAddTransport;

    private TransportAdapter transportAdapter;
    private String screenType;

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
        PrefRepo preferencesHelper = new PrefRepoImpl(getContext());
        LocalRepo localRepo = new LocalRepoImpl(getContext());
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

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

        screenType = getArguments().getString(TYPE_KEY);

        transportAdapter = new TransportAdapter(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(transportAdapter);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(ViewUtils.dpToPx(8), ViewUtils.dpToPx(8), ViewUtils.dpToPx(8), 0));


        switch (screenType) {

            case ARCHIVE_TYPE: {
                btnAddTransport.hide();
                transportAdapter = new TransportAdapter(true);
                recyclerView.setAdapter(transportAdapter);
                getPresenter().getTransportArchive();
                transportAdapter.setListener(this);
                break;
            }
            default:
                getPresenter().getUserTransport();
                transportAdapter = new TransportAdapter(false);
                recyclerView.setAdapter(transportAdapter);
                transportAdapter.setListener(this);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        switch (screenType) {

            case ARCHIVE_TYPE: {
                btnAddTransport.hide();
                getPresenter().getTransportArchive();
                break;
            }
            default:
                getPresenter().getUserTransport();
                break;
        }
    }

    @Override
    public void onGetTransports(List<Transport> transports) {
        transportAdapter.setItems(transports);
        Log.d(TAG, "onGetTransports: " + transports);
    }

    @Override
    public void onGetTransportArchive(List<Transport> transports) {
        transportAdapter.setItems(transports);
        Log.d(TAG, "onGetTransports: " + transports);
    }

    @Override
    public void onAddTransportToArchive(int transportId) {
        ((MyTransportActivity) getActivity()).refresh();
    }

    @Override
    public void onRemoveTransportFromArchive(int transportId) {
        ((MyTransportActivity) getActivity()).refresh();
    }


    @Override
    public void onClick(int pos, Transport transport) {
        TransportActivity.start(getActivity(), transport.getId());
    }

    @Override
    public void onActionEdit(int pos, Transport transport) {
        Log.d(TAG, "onActionEdit: ");
        AddTransportActivity.start(getActivity(), transport);
    }

    @Override
    public void onActionAddToArchive(int pos, Transport transport) {
        Log.d(TAG, "onActionAddToArchive: ");
        getPresenter().addTransportToArchive(transport.getId());
    }

    @Override
    public void onActionRestoreFromArchive(int pos, Transport transport) {
        Log.d(TAG, "onActionRestoreFromArchive: ");
        getPresenter().removeTransportFromArchive(transport.getId());
    }
}
