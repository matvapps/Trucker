package com.foora.perevozkadev.ui.nav;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.data.prefs.PrefRepo;
import com.foora.perevozkadev.data.prefs.PrefRepoImpl;
import com.foora.perevozkadev.ui.add_order.AddOrderActivity;
import com.foora.perevozkadev.ui.base.BaseDialog;
import com.foora.perevozkadev.ui.messages.MessagesActivity;
import com.foora.perevozkadev.ui.my_orders.MyOrdersActivity;
import com.foora.perevozkadev.ui.my_transport.MyTransportActivity;
import com.foora.perevozkadev.ui.profile.ProfileActivity;
import com.foora.perevozkadev.ui.search_order.SearchOrderActivity;
import com.foora.perevozkadev.ui.staff.EmployeesActivity;
import com.foora.perevozkadev.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.txtv_user_name)
    TextView userNameTxtv;
    @BindView(R.id.short_name)
    TextView userShortNameTxtv;

    private Dialog dialog;
    private PrefRepo preferencesHelper;
    private String userName;

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
    protected void setUp(View view) {
        btnCreateOrder.setOnClickListener(this);
        btnSearchOrders.setOnClickListener(this);
        btnMyOrders.setOnClickListener(this);
        btnSearchTransport.setOnClickListener(this);
        btnMyTransport.setOnClickListener(this);
        btnStaff.setOnClickListener(this);
        btnMessages.setOnClickListener(this);

        if (userName != null && !userName.equals("")) {
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

        }
    }


    @OnClick(R.id.nav_header)
    void onHeaderClick() {
        ProfileActivity.start(getActivity());
    }

    @Override
    public void onClick(View v) {
        btnCreateOrder.setActivated(false);
        btnSearchOrders.setActivated(false);
        btnMyOrders.setActivated(false);
        btnSearchTransport.setActivated(false);
        btnMyTransport.setActivated(false);
        btnStaff.setActivated(false);
        btnMessages.setActivated(false);

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
            }
        }
    }
}
