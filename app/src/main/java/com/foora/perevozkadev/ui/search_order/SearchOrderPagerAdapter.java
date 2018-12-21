package com.foora.perevozkadev.ui.search_order;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.foora.perevozkadev.ui.search_order.filter.FilterFragment;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.ui.search_order.orders.OrdersFragment;
import com.foora.perevozkadev.utils.custom.pager_adapter.ArrayFragmentPagerAdapter;

import java.util.List;


/**
 * Created by Alexander Matvienko on 10.12.2018.
 */
public class SearchOrderPagerAdapter extends ArrayFragmentPagerAdapter<Filter> {

    public SearchOrderPagerAdapter(FragmentManager fm, List<Filter> filters) {
        super(fm, filters);
    }

    @Override
    public Fragment getFragment(Filter item, int position) {
        if (position == 0) {
            // return first FilterFragment
            return FilterFragment.newInstance();
        } else {
            return OrdersFragment.newInstance(item);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "+";
            default:
                return "RU - UA";
        }
    }
}
