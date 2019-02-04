package com.foora.perevozkadev.ui.messages_info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.profile.ProfilePresenter;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MessagesInfoActivity extends BasePresenterActivity<MessagesInfoMvpPresenter> implements MessagesInfoMvpView {

    public static final String TAG = MessagesInfoActivity.class.getSimpleName();

    private RecyclerView messagesList;

    private MessagesInfoAdapter messagesInfoAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MessagesInfoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);

        setUnBinder(ButterKnife.bind(this));

    }

    @Override
    protected void setUp() {

        messagesList = findViewById(R.id.messages_list);

        messagesInfoAdapter = new MessagesInfoAdapter();
        messagesList.setLayoutManager(new LinearLayoutManager(this));
        messagesList.setAdapter(messagesInfoAdapter);

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


}
