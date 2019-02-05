package com.foora.perevozkadev.ui.change_status;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foora.foora.perevozkadev.R;
import com.foora.perevozkadev.ui.add_order.model.Order;
import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.base.BaseDialog;
import com.foora.perevozkadev.ui.entry.FragmentCallback;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexandr.
 */
public class ChangeStatusFragment extends BaseDialog {

    public static final String TAG = ChangeStatusFragment.class.getSimpleName();

    private static final String KEY_ORDER_JSON = "order_json";

    private Callback listener;

    private Order order;

    @BindView(R.id.status_list)
    RecyclerView statusList;

    public static ChangeStatusFragment newInstance(String orderJson) {
        ChangeStatusFragment fragment = new ChangeStatusFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ORDER_JSON, orderJson);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status_change, container, false);

        Gson gson = new Gson();

        order = gson.fromJson(getArguments().getString(KEY_ORDER_JSON), Order.class);

        setUnBinder(ButterKnife.bind(this, view));

        return view;
    }

    @Override
    protected void setUp(View view) {
        ChangeStatusAdapter changeStatusAdapter = new ChangeStatusAdapter();

        statusList.setLayoutManager(new LinearLayoutManager(getContext()));
        statusList.setAdapter(changeStatusAdapter);

        changeStatusAdapter.setItems(calculateItems());

        changeStatusAdapter.setStatus(order.getStatus());

        changeStatusAdapter.setListener((pos, item) -> {
            if (listener != null) {
                listener.onChangeStatus(item);
                dismissDialog();
            }
        });

    }

    private List<String> calculateItems() {
        List<String> items = new ArrayList<>();

        for (Place place: order.getLoadingPlaces()) {
            items.add(String.format(Locale.getDefault(), "%s %s", "Прибыл на склад", place.getName()));
            items.add(String.format(Locale.getDefault(), "%s %s", "Отправился со склада", place.getName()));
        }

        for (Place place: order.getUnloadingPlaces()) {
            items.add(String.format(Locale.getDefault(), "%s %s", "Прибыл на склад", place.getName()));
            items.add(String.format(Locale.getDefault(), "%s %s", "Отправился со склада", place.getName()));
        }

        items.add("Поломался");
        items.add("Опаздываю");
        items.add("Груз доставлен");

        return items;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (Callback) context;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    public void dismissDialog() {
        super.dismissDialog(TAG);
    }

    public interface Callback extends FragmentCallback {
        void onChangeStatus(String status);
    }

}
