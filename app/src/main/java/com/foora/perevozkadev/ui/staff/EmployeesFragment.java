package com.foora.perevozkadev.ui.staff;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.DataManager;
import com.foora.perevozkadev.data.DataManagerImpl;
import com.foora.perevozkadev.data.db.LocalRepo;
import com.foora.perevozkadev.data.db.LocalRepoImpl;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_employee.AddEmployeeActivity;
import com.foora.perevozkadev.ui.base.BasePresenterFragment;
import com.foora.perevozkadev.ui.employee.EmployeeActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.staff.adapter.EmployeesAdapter;
import com.foora.perevozkadev.utils.custom.ItemSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Alexandr.
 */
public class EmployeesFragment extends BasePresenterFragment<EmployeesPresenter> implements EmployeesMvpView {

    public static final String TAG = EmployeesFragment.class.getSimpleName();

    public static final String TYPE_KEY = "user_type";

    public static final int MANAGER = 221;
    public static final int DRIVER = 222;
    public static final int ARCHIVE = 223;

    private int type = MANAGER;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;

    private EmployeesAdapter employeesAdapter;

    public static EmployeesFragment newInstance(int type) {
        EmployeesFragment fragment = new EmployeesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected EmployeesPresenter createPresenter() {
        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo preferencesHelper = new PrefRepoImpl(getContext());
        LocalRepo localRepo = new LocalRepoImpl(getContext());
        DataManager dataManager = new DataManagerImpl(remoteRepo, preferencesHelper, localRepo);

        EmployeesPresenter presenter = new EmployeesPresenter(dataManager, AndroidSchedulers.mainThread());
        presenter.onAttach(this);

        return presenter;
    }

    @OnClick(R.id.btn_add)
    void onAdd() {
        AddEmployeeActivity.start(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employees, container, false);

        if (getArguments() != null)
            type = getArguments().getInt(TYPE_KEY, MANAGER);

        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void setUp(View view) {
        super.setUp(view);

        setUnBinder(ButterKnife.bind(this, view));

        employeesAdapter = new EmployeesAdapter();
        employeesAdapter.setListener((view1, profile) -> {

            if (type == ARCHIVE) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view1, Gravity.END);
                int menuId = R.menu.archive_context_menu;
                popupMenu.getMenuInflater().inflate(menuId, popupMenu.getMenu());

                for (int i = 0; i < popupMenu.getMenu().size(); i++) {
                    MenuItem item = popupMenu.getMenu().getItem(i);

                    SpannableString s = new SpannableString("Восстановить из архива");
                    s.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, s.length(), 0);
                    item.setTitle(s);
                }

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.action_restore_from_archive:
                            getPresenter().restoreFromArchive(profile.getUserId());
                            return true;
                        default:
                            return false;
                    }
                });

                popupMenu.show();
            } else {
                EmployeeActivity.start(getActivity(), profile);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(employeesAdapter);
        recyclerView.addItemDecoration(new ItemSpacingDecoration(0, 0, 0, 0));

        if (type == ARCHIVE) {
            getPresenter().getEmployeesArchive();
            btnAdd.setVisibility(View.GONE);
        } else {
            getPresenter().getEmployees();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (type == ARCHIVE) {
            getPresenter().getEmployeesArchive();
        } else {
            getPresenter().getEmployees();
        }
    }

    @Override
    public void onReceiveEmployees(List<Profile> profiles) {
        List<Profile> sortProfiles = new ArrayList<>();

        switch (type) {
            case MANAGER:
                for (Profile profile : profiles) {
                    if (!profile.getGroups().contains("driver")) {
                        sortProfiles.add(profile);
                    }
                }
                break;
            case DRIVER:
                for (Profile profile : profiles) {
                    if (profile.getGroups().contains("driver")) {
                        sortProfiles.add(profile);
                    }
                }
                break;
        }


        employeesAdapter.setItems(sortProfiles);


    }

    @Override
    public void onGetEmployeesArchive(List<Profile> profiles) {
        Log.d(TAG, "onGetEmployeesArchive: " + profiles);
        employeesAdapter.setItems(profiles);
    }

    @Override
    public void onRestoreFromArchive() {
        ((EmployeesActivity) getActivity()).refresh();
    }
}
