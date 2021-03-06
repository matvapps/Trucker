package com.foora.perevozkadev.ui.messages_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.my_transport.model.Transport;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.rate.RateActivity;
import com.foora.perevozkadev.ui.transport.TransportActivity;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.ItemSpacingDecoration;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MessagesInfoActivity extends BasePresenterActivity<MessagesInfoMvpPresenter> implements MessagesInfoMvpView, MessagesInfoAdapter.Callback {

    public static final String TAG = MessagesInfoActivity.class.getSimpleName();

    private static final String KEY_REQUEST_ID = "request_id";
    private static final String KEY_TITLE = "titleTxtv";

    private RecyclerView messagesList;
    private View btnBack;
    private TextView titleTxtv;

    private Handler handler = new Handler();
    private MessagesInfoAdapter messagesInfoAdapter;

    private int requestId;
    private String title;

    public static void start(Activity activity, int requestId, String title) {
        Intent intent = new Intent(activity, MessagesInfoActivity.class);
        intent.putExtra(KEY_REQUEST_ID, requestId);
        intent.putExtra(KEY_TITLE, title);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);

        setUnBinder(ButterKnife.bind(this));
        title = getIntent().getStringExtra(KEY_TITLE);
        requestId = getIntent().getIntExtra(KEY_REQUEST_ID, -1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUp();
    }

    @Override
    protected void setUp() {
        messagesList = findViewById(R.id.messages_list);
        btnBack = findViewById(R.id.btn_back);
        titleTxtv = findViewById(R.id.toolbar_title);

        titleTxtv.setText(title);

        btnBack.setOnClickListener(v -> finish());

        messagesInfoAdapter = new MessagesInfoAdapter();
        messagesInfoAdapter.setListener(this);
        messagesList.setLayoutManager(new LinearLayoutManager(this));
        messagesList.addItemDecoration(new ItemSpacingDecoration(0, ViewUtils.dpToPx(12), 0, 0));
        messagesList.setAdapter(messagesInfoAdapter);

        handler.post(statusCheck);

    }


    @Override
    protected MessagesInfoMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        MessagesInfoMvpPresenter messagesPresenter = new MessagesInfoPresenter(dataManager, AndroidSchedulers.mainThread());
        messagesPresenter.onAttach(this);

        return messagesPresenter;
    }


    @Override
    public void onGetRequestInfo(OrderRequest orderRequest) {
        Log.d(TAG, "onGetRequestInfo: " + orderRequest.getActions());
        messagesInfoAdapter.setOrderRequest(orderRequest);
    }

    @Override
    public void onGetTransport(Transport transport) {
        Log.d(TAG, "onGetTransport: " + transport.toString());
        messagesInfoAdapter.addTransport(transport);
    }

    @Override
    public void onGetProfile(Profile profile) {
        Log.d(TAG, "onGetProfile: " + profile.toString());
        messagesInfoAdapter.setProfile(profile);
        getPresenter().getRequestInfo(requestId);
    }

    @Override
    public void onRejectRequest() {
        finish();
    }

    @Override
    public void onConfirmRequest() {
        getPresenter().getProfile();
    }

    @Override
    public void onGetOrder(Order order) {
        Log.d(TAG, "onGetOrder: " + order.toString());
        messagesInfoAdapter.setOrder(order);
    }


    @Override
    public void onRequestTransport(int id) {
        Log.d(TAG, "onRequestTransport: " + id);
        getPresenter().getTransport(id);
    }

    @Override
    public void onAcceptRequest(int requestId) {
        getPresenter().confirmRequest(requestId);
    }

    @Override
    public void onRefuseRequest(int requestId) {
        getPresenter().rejectRequest(requestId);
    }

    @Override
    public void onRequestOrder(int orderId) {
        getPresenter().getOrderById(orderId);
    }

    @Override
    public void onOpenOrder(int orderId) {
        RateActivity.start(this, orderId);
    }

    @Override
    public void onOpenTransport(int trasportId) {
        TransportActivity.start(MessagesInfoActivity.this, trasportId);
    }

    private Runnable statusCheck = new Runnable() {
        @Override
        public void run() {
            getPresenter().getProfile();
            handler.postDelayed(statusCheck, 5000);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(statusCheck);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(statusCheck);
    }
}
