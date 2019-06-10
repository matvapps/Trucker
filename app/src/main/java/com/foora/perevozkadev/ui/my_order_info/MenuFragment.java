package com.foora.perevozkadev.ui.my_order_info;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_drivers_to_order.order_drivers.OrderDriversActivity;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.change_status.ChangeStatusFragment;
import com.foora.perevozkadev.ui.docs.DocsActivity;
import com.google.gson.Gson;

/**
 * Created by Alexandr.
 */
public class MenuFragment extends BottomSheetDialogFragment {

    public static final String TAG = MenuFragment.class.getSimpleName();

    private static final String ORDER_KEY = "order_key";
    private static final String USER_ID_KEY = "user_id_key";

    private Order order;
    private int userId;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
    }


    public static MenuFragment newInstance(String orderJson, int userId) {
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        args.putString(ORDER_KEY, orderJson);
        args.putInt(USER_ID_KEY, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_order_menu, null);

        String orderJson = getArguments().getString(ORDER_KEY);
        userId = getArguments().getInt(USER_ID_KEY);

        Gson gson = new Gson();
        order = gson.fromJson(orderJson, Order.class);

        View orderTrack = view.findViewById(R.id.order_track);
        View orderFiles = view.findViewById(R.id.order_files);
        View orderChangeState = view.findViewById(R.id.order_change_state);
        View addDrivers = view.findViewById(R.id.add_drivers);

        orderTrack.setVisibility(View.GONE);

        if (userId == order.getUser()) {
            addDrivers.setVisibility(View.GONE);
        }

        orderTrack.setOnClickListener(v -> {

        });

        orderFiles.setOnClickListener(v -> {
            DocsActivity.start(getActivity(), order.getId());
        });

        orderChangeState.setOnClickListener(v -> {
            ChangeStatusFragment.newInstance(orderJson).show(getFragmentManager());
        });

        addDrivers.setOnClickListener(v -> {
            OrderDriversActivity.start(getActivity(), order.getId());
        });


        getDialog().setContentView(view);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();


        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";

                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";

                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";

                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });


        }
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public void show(FragmentManager fragmentManager, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prevFragment = fragmentManager.findFragmentByTag(tag);
        if (prevFragment != null) {
            transaction.remove(prevFragment);
        }
        transaction.addToBackStack(null);
        show(transaction, tag);
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0f;
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(windowParams);
    }

}