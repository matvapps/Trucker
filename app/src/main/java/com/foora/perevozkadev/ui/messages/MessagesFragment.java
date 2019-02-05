package com.foora.perevozkadev.ui.messages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.foora.perevozkadev.data.network.model.OrderRequest;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.messages.model.Message;
import com.foora.perevozkadev.ui.messages_info.MessagesInfoActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Alexandr.
 */
public class MessagesFragment extends BasePresenterFragment<MessagesPresenter> implements MessagesMvpView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private MessageAdapter messageAdapter;
    private List<OrderRequest> orderRequests;

    public static MessagesFragment newInstance() {
        Bundle args = new Bundle();
        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MessagesPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(getContext());
        LocalRepo localRepo = new LocalRepoImpl(getContext());
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        MessagesPresenter presenter = new MessagesPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        return view;
    }

    @Override
    protected void setUp(View view) {
        super.setUp(view);

        setUnBinder(ButterKnife.bind(this, view));
        messageAdapter = new MessageAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(messageAdapter);

        orderRequests = new ArrayList<>();

        messageAdapter.setListener(new MessageAdapter.Callback() {
            @Override
            public void onItemClick(int pos, Message message) {
                MessagesInfoActivity.start(getActivity(), orderRequests.get(pos).getId(), message.getRoute());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        messageAdapter.clear();
        getPresenter().getUserRequests();
        getPresenter().getToUserRequests();
    }

    @Override
    public void onGetUserRequests(List<OrderRequest> orderRequests) {
//        messageAdapter.setItems(orderRequests);
        for (OrderRequest orderRequest : orderRequests) {
            getPresenter().getOrderByRequest(orderRequest);
        }
    }

    @Override
    public void onGetToUserRequests(List<OrderRequest> orderRequests) {
        for (OrderRequest orderRequest : orderRequests) {
            getPresenter().getOrderByRequest(orderRequest);
        }
    }

    @Override
    public void onGetOrderByRequest(OrderRequest orderRequest, Order order) {
        Log.d(TAG, "onGetOrderByRequest: " + order);
        String name = "";
        String route;
        String message;
        String dateStr = "";

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        SimpleDateFormat outFormat = new SimpleDateFormat("dd MMM");
        try {
            Date date;
            if (orderRequest.getUpdatedAt() != null)
                date = format.parse(orderRequest.getUpdatedAt());
            else
                date = format.parse(orderRequest.getCreatedAt());
            dateStr = outFormat.format(date);

        } catch (ParseException e) {
            Log.e(TAG, "onGetOrderByRequest: " + e.getMessage(), e);
        }

        String firstPlace = order.getLoadingPlaces()
                .get(0).getName().split(",")[0];
        String secondPlace = order.getUnloadingPlaces()
                .get(order.getUnloadingPlaces().size() - 1).getName().split(",")[0];

        int placeCount = order.getLoadingPlaces().size() + order.getUnloadingPlaces().size() - 2;

        name = firstPlace.substring(0, 1) + secondPlace.substring(0, 1);

        route = String.format(Locale.getDefault(), "%s - (%d) - %s", firstPlace, placeCount, secondPlace);
        message = orderRequest.getText();

        Message messageObj = new Message(name, route, message, dateStr);
        messageAdapter.addItem(messageObj);
        orderRequests.add(orderRequest);
    }
}
