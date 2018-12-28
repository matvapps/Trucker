package com.foora.perevozkadev.ui.employee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PreferencesHelper;
import com.foora.perevozkadev.data.prefs.SharedPrefsHelper;
import com.foora.perevozkadev.ui.base.BasePresenterActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class EmployeeActivity extends BasePresenterActivity<EmployeeMvpPresenter> implements EmployeeMvpView {

    public static final String TAG = EmployeeActivity.class.getSimpleName();

    private final static String PROFILE_KEY = "profile";


    private View btnBack;
    private View btnRemove;

    private ImageView userImage;
    private TextView userName;
    private TextView userType;
    private TextView userPhone;
    private TextView userLogin;
    private TextView userPassword;

    private Profile profile;

    public static void start(Activity activity, Profile profile) {
        Intent intent = new Intent(activity, EmployeeActivity.class);
        intent.putExtra(PROFILE_KEY, profile);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        profile = (Profile) getIntent().getSerializableExtra(PROFILE_KEY);

        setUnBinder(ButterKnife.bind(this));

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        btnRemove = findViewById(R.id.btn_remove);
        btnRemove.setOnClickListener(v -> getPresenter().removeUser(profile.getUserId()));

        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        userType = findViewById(R.id.user_type);
        userPhone = findViewById(R.id.phone);
        userLogin = findViewById(R.id.login);
        userPassword = findViewById(R.id.password);

        userName.setText(String.format("%s %s", profile.getFirstName(), profile.getLastName()));
        userType.setText(profile.getUserGroup());
        userPhone.setText(profile.getPhone());
        userLogin.setText(profile.getUsername());

    }

    @Override
    protected void setUp() {

    }


    @Override
    protected EmployeeMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PreferencesHelper prefs = new SharedPrefsHelper(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs);

        EmployeeMvpPresenter presenter = new EmployeePresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }


    @Override
    public void onRemoveUser() {
        showMessage("Пользователь успешно удален");
        finish();
    }
}
