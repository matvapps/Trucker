package com.foora.perevozkadev.ui.nav;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.network.RemoteRepo;
import com.foora.perevozkadev.data.network.RemoteRepoImpl;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.AddOrderActivity;
import com.foora.perevozkadev.ui.base.BaseDialog;
import com.foora.perevozkadev.ui.entry.EntryActivity;
import com.foora.perevozkadev.ui.map.MapActivity;
import com.foora.perevozkadev.ui.map.calculate.CalcDistanceFragment;
import com.foora.perevozkadev.ui.map.track.TrackFragment;
import com.foora.perevozkadev.ui.messages.MessagesActivity;
import com.foora.perevozkadev.ui.my_orders.MyOrdersActivity;
import com.foora.perevozkadev.ui.my_transport.MyTransportActivity;
import com.foora.perevozkadev.ui.profile.ProfileActivity;
import com.foora.perevozkadev.ui.profile.model.Profile;
import com.foora.perevozkadev.ui.profile.profile_settings.ProfileSettingsActivity;
import com.foora.perevozkadev.ui.search_order.SearchOrderActivity;
import com.foora.perevozkadev.ui.staff.EmployeesActivity;
import com.foora.perevozkadev.utils.ViewUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alexandr.
 */
public class NavDrawerFragment extends BaseDialog implements View.OnClickListener {

    public static final String TAG = NavDrawerFragment.class.getSimpleName();

    @BindView(R.id.create_order)
    View btnCreateOrder;
    @BindView(R.id.search_orders)
    View btnSearchOrders;
    @BindView(R.id.my_orders)
    View btnMyOrders;
    @BindView(R.id.search_transport)
    View btnSearchTransport;
    @BindView(R.id.my_transport)
    View btnMyTransport;
    @BindView(R.id.staff)
    View btnStaff;
    @BindView(R.id.messages)
    View btnMessages;
    @BindView(R.id.calculate_distance)
    View calculateDistance;
    @BindView(R.id.track_order)
    View trackOrder;
    @BindView(R.id.action_settings)
    View btnSettings;
    @BindView(R.id.exit)
    View btnExit;

    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.txtv_user_name)
    TextView userNameTxtv;
    @BindView(R.id.txtv_user_role)
    TextView userRoleTxtv;
    @BindView(R.id.short_name)
    TextView userShortNameTxtv;

    private Dialog dialog;
    private PrefRepo preferencesHelper;
    private String userName;
    private String userRole;

    public static NavDrawerFragment newInstance() {
        NavDrawerFragment fragment = new NavDrawerFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.START);
            dialog.getWindow().setWindowAnimations(
                    R.style.dialog_left_animation_slide);
            dialog.getWindow().setLayout(
                    ViewUtils.dpToPx(300),
                    ViewGroup.LayoutParams.MATCH_PARENT);
        }
        dialog.setCanceledOnTouchOutside(true);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_drawer, container, false);

        setUnBinder(ButterKnife.bind(this, view));

        preferencesHelper = new PrefRepoImpl(getContext());
        userName = preferencesHelper.getUserName();

        return view;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

    @Override
    public void onResume() {
        super.onResume();

        recreateHeader();
    }

    public void recreateHeader() {
        PrefRepo prefRepo = new PrefRepoImpl(getContext());

        String userType = "";
        userRole = prefRepo.getUserRole();

        if (userRole.equals("owner")) {
            userType = "Владелец аккаунта";
        } else if (userRole.equals("manager_1")) {
            userType = "Менеджер 1 ур.";
        } else if (userRole.equals("manager_2")) {
            userType = "Менеджер 2 ур.";
        } else if (userRole.equals("manager_3")) {
            userType = "Менеджер 3 ур.";
        } else if (userRole.equals("driver")) {
            userType = "Водитель";

            btnCreateOrder.setVisibility(View.GONE);
            btnStaff.setVisibility(View.GONE);
            btnMyTransport.setVisibility(View.GONE);

        }


        userRoleTxtv.setText(userType);

        if (userName != null && userName.length() > 2) {
            userNameTxtv.setText(userName);

            StringBuilder shortName = new StringBuilder();

            String[] names = userName.split(" ");
            for (int i = 0; i < names.length; i++) {
                if (i == 2) break;

                if (names.length > 1)
                    shortName.append(names[i].substring(0, 1));
            }

            if (!shortName.toString().equals("")) {
                userShortNameTxtv.setText(shortName.toString());
            }

        }
    }

    @Override
    protected void setUp(View view) {
        btnCreateOrder.setOnClickListener(this);
        btnSearchOrders.setOnClickListener(this);
        btnMyOrders.setOnClickListener(this);
//        btnSearchTransport.setOnClickListener(this);
        btnMyTransport.setOnClickListener(this);
        btnStaff.setOnClickListener(this);
        btnMessages.setOnClickListener(this);
        calculateDistance.setOnClickListener(this);
        trackOrder.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnSettings.setOnClickListener(this);

        RemoteRepo remoteRepo = new RemoteRepoImpl();
        PrefRepo prefRepo = new PrefRepoImpl(getContext());

        Log.e(TAG, "setUp: ");
        remoteRepo.getProfile(prefRepo.getUserToken())
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        if (response.isSuccessful()) {
                            Profile profile = response.body();
                            Log.e(TAG, "onResponse: " + profile);

                            prefRepo.setUserRole(profile.getGroups().get(0));
                            prefRepo.setUserName(String.format(Locale.getDefault(), "%s %s", profile.getFirstName(), profile.getLastName()));

                            recreateHeader();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {

                    }
                });

        if (userName != null && userName.length() > 2) {
            userNameTxtv.setText(userName);

            StringBuilder shortName = new StringBuilder();

            String[] names = userName.split(" ");
            for (int i = 0; i < names.length; i++) {
                if (i == 2) break;

                if (names.length > 1)
                    shortName.append(names[i].substring(0, 1));
            }

            if (!shortName.toString().equals(" ")) {
                userShortNameTxtv.setText(shortName.toString());
            }

        }

        if (getActivity() instanceof AddOrderActivity) {
            btnCreateOrder.setActivated(true);
        } else if (getActivity() instanceof SearchOrderActivity) {
            btnSearchOrders.setActivated(true);
        } else if (getActivity() instanceof MyTransportActivity) {
            btnMyTransport.setActivated(true);
        } else if (getActivity() instanceof EmployeesActivity) {
            btnStaff.setActivated(true);
        } else if (getActivity() instanceof MyOrdersActivity) {
            btnMyOrders.setActivated(true);
        } else if (getActivity() instanceof MapActivity) {
            calculateDistance.setActivated(true);
        } else if (getActivity() instanceof ProfileSettingsActivity) {
            btnSettings.setActivated(true);
        }
    }


    @OnClick(R.id.nav_header)
    void onHeaderClick() {
        if (userRole.equals("owner"))
            ProfileActivity.start(getActivity());
    }

    @Override
    public void onClick(View v) {
        btnCreateOrder.setActivated(false);
        btnSearchOrders.setActivated(false);
        btnMyOrders.setActivated(false);
//        btnSearchTransport.setActivated(false);
        btnMyTransport.setActivated(false);
        btnStaff.setActivated(false);
        btnMessages.setActivated(false);
        calculateDistance.setActivated(false);

        v.setActivated(true);

        switch (v.getId()) {
            case R.id.create_order:
                dialog.setOnDismissListener(dialog -> {
                    AddOrderActivity.start(getActivity());
                    getActivity().finish();
                });
                dismissDialog();
                break;
            case R.id.search_orders:
                dialog.setOnDismissListener(dialog -> {
                    SearchOrderActivity.start(getActivity());
                    getActivity().finish();
                });
                dismissDialog();
                break;
            case R.id.my_transport:
                dialog.setOnDismissListener(dialog -> {
                    MyTransportActivity.start(getActivity());
                    getActivity().finish();
                });
                dismissDialog();
                break;
            case R.id.staff:
                dialog.setOnDismissListener(dialog -> {
                    EmployeesActivity.start(getActivity());
                    getActivity().finish();
                });
                dismissDialog();
                break;
            case R.id.my_orders:
                dialog.setOnDismissListener(dialog -> {
                    MyOrdersActivity.start(getActivity());
                    getActivity().finish();
                });
                dismissDialog();
                break;
            case R.id.messages: {
                dialog.setOnDismissListener(dialog1 -> {
                    MessagesActivity.start(getActivity());
                    getActivity().finish();
                });
                dismissDialog();
                break;
            }
            case R.id.calculate_distance: {
                dialog.setOnDismissListener(dialog1 -> {
                    MapActivity.start(getActivity(), CalcDistanceFragment.TAG);
                    getActivity().finish();
                });
                dismissDialog();
                break;
            }
            case R.id.track_order: {
                dialog.setOnDismissListener(dialog1 -> {
                    MapActivity.start(getActivity(), TrackFragment.TAG);
                    getActivity().finish();
                });
                dismissDialog();
                break;
            }
            case R.id.action_settings: {
                dialog.setOnDismissListener(dialog1 -> {
                    ProfileSettingsActivity.start(getActivity());
                });
                dismissDialog();
                break;
            }
            case R.id.exit: {
                PrefRepo preferencesHelper = new PrefRepoImpl(getContext());
                dialog.setOnDismissListener(dialog1 -> {
                    preferencesHelper.clear();
                    EntryActivity.start(getActivity());
                    getActivity().finish();
                });
                dismissDialog();
                break;
            }
        }
    }
}
