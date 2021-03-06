package com.foora.perevozkadev.ui.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
import com.foora.perevozkadev.ui.entry.confirm.ConfirmFragment;
import com.foora.perevozkadev.ui.entry.login.LoginFragment;
import com.foora.perevozkadev.ui.entry.otp.OtpCodeDialogFragment;
import com.foora.perevozkadev.ui.entry.register.RegisterFragment;
import com.foora.perevozkadev.ui.search_order.SearchOrderActivity;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.foora.perevozkadev.ui.entry.otp.OtpCodeDialogFragment.REGISTER;

public class EntryActivity extends BasePresenterActivity<EntryPresenter> implements EntryMvpView,
        RegisterFragment.Callback, LoginFragment.Callback, OtpCodeDialogFragment.Callback,
        ConfirmFragment.Callback {

    public static final String TAG = EntryActivity.class.getSimpleName();

    private String login;
    private String password;
    private String phone;
    private int userId;

    private PrefRepo preferencesHelper;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, EntryActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencesHelper = new PrefRepoImpl(this);
        if (!preferencesHelper.getUserToken().equals("token ")) {
            openMainActivity();
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setUnBinder(ButterKnife.bind(this));

        // by default open register fragment
        showLoginFragment();
    }

    @Override
    public void showLoginFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .replace(R.id.root_view, LoginFragment.newInstance(), LoginFragment.TAG)
                .commit();
    }

    @Override
    public void showRegisterFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .replace(R.id.root_view, RegisterFragment.newInstance(), RegisterFragment.TAG)
                .commit();
    }

    @Override
    public void showConfirmFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.root_view, ConfirmFragment.newInstance(), ConfirmFragment.TAG)
                .commit();
    }

    @Override
    public void showOtpcodeFragment(String fromType) {
        OtpCodeDialogFragment.newInstance(fromType).show(getSupportFragmentManager());
    }

    @Override
    public void onReceiveUserId(int userId) {
        this.userId = userId;
        showOtpcodeFragment(REGISTER);
    }

    @Override
    protected EntryPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        EntryPresenter registerPresenter = new EntryPresenter(dataManager, AndroidSchedulers.mainThread());
        registerPresenter.onAttach(this);

        return registerPresenter;
    }

    @Override
    protected void setUp() {

    }

    // Presenter callback
    @Override
    public void onConfirmAuth(String phone) {
        this.phone = phone;
        showOtpcodeFragment(OtpCodeDialogFragment.LOGIN);
    }

    @Override
    public void onSmsSend() {

    }

    @Override
    public void openMainActivity() {
        finish();
        SearchOrderActivity.start(this);
    }

    // Fragment callback
    @Override
    public void onTryLogin(String login, String password) {
        this.login = login;
        this.password = password;
        getPresenter().onLoginClick(login, password);
    }

    @Override
    public void onCallLogin() {
        showLoginFragment();
    }

    @Override
    public void onTryRegister(String login, String password, String group, String user_type) {
        this.login = login;
        this.password = password;

//        getPresenter().sendSms(login, password);
        getPresenter().onCheckUserData(login, password);
//        showConfirmFragment();
    }

    @Override
    public void onCallRegister() {
        showRegisterFragment();
    }

    @Override
    public void onCallManager() {

    }

    @Override
    public void onReceiveSmsCode(String confirmType, String smsCode) {
//        showMessage("SMS CODE = " + smsCode);

        switch (confirmType) {
            case OtpCodeDialogFragment.LOGIN:
                getPresenter().onConfirmLogin(login, password, smsCode);
                break;
            case REGISTER:
                getPresenter().activate(userId, smsCode);
                break;
        }

    }

    @Override
    public void onReceivePhone(String phone) {
        getPresenter().onRegisterClick(
                login, password,
                phone, "1", "owner");
    }
}
