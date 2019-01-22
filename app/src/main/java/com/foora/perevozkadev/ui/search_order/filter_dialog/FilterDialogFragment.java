package com.foora.perevozkadev.ui.search_order.filter_dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.route.RouteListAdapter;
import com.foora.perevozkadev.ui.add_order.route.model.RouteItem;
import com.foora.perevozkadev.ui.base.BaseDialog;
import com.foora.perevozkadev.utils.ViewUtils;
import com.foora.perevozkadev.utils.custom.SpeedyLinearLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alexandr.
 */
public class FilterDialogFragment extends BaseDialog implements RouteListAdapter.Callback {

    public static final String TAG = FilterDialogFragment.class.getSimpleName();

    @BindView(R.id.routes_list)
    RecyclerView routeList;


    private RouteListAdapter routeListAdapter;

    public static FilterDialogFragment newInstance() {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.action_cancel)
    void onCancel() {
        super.dismissDialog(TAG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.END);
            dialog.getWindow().setWindowAnimations(
                    R.style.dialog_right_animation_slide);
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
        View view = inflater.inflate(R.layout.fragment_dialog_filter, container, false);

        setUnBinder(ButterKnife.bind(this, view));

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
        routeListAdapter = new RouteListAdapter(getContext());

        routeList.setNestedScrollingEnabled(false);
        routeList.setLayoutManager(new SpeedyLinearLayoutManager(getContext(), 100f));
        routeList.setAdapter(routeListAdapter);

        routeListAdapter.setListener(this);

//
        routeListAdapter.addItem(RouteItem.Type.LOADING_PLACE);
        routeListAdapter.addItem(RouteItem.Type.UNLOADING_PLACE);
//        routeListAdapter.addItem(null);
    }

    @Override
    public void onAddNewItem() {
        int pos = routeList.getAdapter().getItemCount() - 1;
        routeList.smoothScrollToPosition(pos);
    }

    @Override
    public void onCountryChange(int id, RouteItem.Type routeType, String country) {

    }

    @Override
    public void onRegionChange(int id, RouteItem.Type routeType, String region) {

    }

    @Override
    public void onCityChange(int id, RouteItem.Type routeType, String city) {

    }

}
