package com.foora.perevozkadev.ui.add_employee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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
import com.github.matvapps.AppEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddEmployeeActivity extends BasePresenterActivity<AddEmployeeMvpPresenter> implements AddEmployeeMvpView {

    public static final String TAG = AddEmployeeActivity.class.getSimpleName();

    private RecyclerView typeListView;
    private Button addEmployeeBtn;
    private View btnBack;

    private AppEditText nameEdtxt;
    private AppEditText surnameEdtxt;
    private AppEditText phoneEdtxt;
    private AppEditText emailEdtxt;
    private AppEditText loginEdtxt;
    private AppEditText passwordEdtxt;

    private UserTypeAdapter userTypeAdapter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AddEmployeeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        setUnBinder(ButterKnife.bind(this));

        nameEdtxt = findViewById(R.id.name);
        surnameEdtxt = findViewById(R.id.surname);
        phoneEdtxt = findViewById(R.id.phone);
        emailEdtxt = findViewById(R.id.email);
        loginEdtxt = findViewById(R.id.login);
        passwordEdtxt = findViewById(R.id.password);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());
        typeListView = findViewById(R.id.type_list);
        addEmployeeBtn = findViewById(R.id.add_employee);

        typeListView.setLayoutManager(
                new LinearLayoutManager(
                        this, LinearLayoutManager.HORIZONTAL, false));

        List<String> types = new ArrayList<>();
        types.add("Водитель");
        types.add("Менеджер ур1");
        types.add("Менеджер ур2");
        types.add("Менеджер ур3");

        userTypeAdapter = new UserTypeAdapter();
        userTypeAdapter.setItems(types);

        typeListView.setAdapter(userTypeAdapter);

        addEmployeeBtn.setOnClickListener(v -> addEmployee());

    }

    @Override
    protected void setUp() {


    }


    private void addEmployee() {
        String group = "";
        String firstName = nameEdtxt.getText();
        String lastName = surnameEdtxt.getText();
        String phone = phoneEdtxt.getText();
        String email = emailEdtxt.getText();
        String login = loginEdtxt.getText();
        String password = passwordEdtxt.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()
                || email.isEmpty() || login.isEmpty() || password.isEmpty()) {
            onError("Заполните все поля");
            return;
        }

        if (userTypeAdapter.getSelectedPos() == -1) {
            onError("Выберите тип сотрудника");
            return;
        }

        switch (userTypeAdapter.getSelectedPos()) {
            case 0:
                group = "driver";
                break;
            case 1:
                group = "manager_1";
                break;
            case 2:
                group = "manager_2";
                break;
            case 3:
                group = "manager_3";
                break;
        }

        getPresenter().addEmployee(login, password, email,
                phone, "1", firstName, lastName, group);

    }


    @Override
    protected AddEmployeeMvpPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo prefs = new PrefRepoImpl(this);
        LocalRepo localRepo = new LocalRepoImpl(this);
        DataManager dataManager = new DataManagerImpl(remoteRepo, prefs, localRepo);

        AddEmployeeMvpPresenter presenter = new AddEmployeePresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }


    @Override
    public void onAddEmployee() {
        showMessage("пользователь успешно добавлен");
        finish();
    }
}
