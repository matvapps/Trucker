package com.foora.perevozkadev.ui.profile.profile_settings.use_2fa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

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
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.github.matvapps.AppEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class EnableSmsActivity extends BasePresenterActivity<EnableSmsMvpPresenter> implements EnableSmsMvpView {

    public static final String TAG = EnableSmsActivity.class.getSimpleName();

    @BindView(R.id.phone)
    AppEditText phoneEdtxt;
    @BindView(R.id.action_done)
    View actionDone;
    @BindView(R.id.sms_switch)
    Switch smsSwitch;
    @BindView(R.id.btn_back)
    View btnBack;


    public static void start(Activity activity) {
        Intent intent = new Intent(activity, EnableSmsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_sms);

        setUnBinder(ButterKnife.bind(this));
        setUp();
    }

    @Override
    protected void setUp() {
        getPresenter().getProfile();

        phoneEdtxt.setEnabled(false);
        phoneEdtxt.setClickable(false);

        actionDone.setOnClickListener(v -> {
            int enabled = smsSwitch.isChecked() ? 1 : 0;
            getPresenter().set2FaEnabled(enabled);
        });

        btnBack.setOnClickListener(v -> finish());

    }


    @Override
    protected EnableSmsMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        LocalRepo localRepo = new LocalRepoImpl(this);
        PrefRepo prefRepo = new PrefRepoImpl(this);

        DataManager dataManager = new DataManagerImpl(remoteRepo, prefRepo, localRepo);
        EnableSmsMvpPresenter presenter = new EnableSmsPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        smsSwitch.setChecked(prefRepo.getIs2FaEnabled());

        return presenter;
    }


    @Override
    public void onGetProfile(Profile profile) {
        phoneEdtxt.setText(profile.getPhone());
        smsSwitch.setChecked(profile.getIs2faEnabled());
    }

    @Override
    public void onIs2FaChanged() {
        Toast.makeText(this, "Профиль успешно изменен", Toast.LENGTH_SHORT).show();
        finish();
    }
}
