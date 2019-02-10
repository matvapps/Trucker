package com.foora.perevozkadev.ui.search_order;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.foora.perevozkadev.ui.add_order.model.Place;
import com.foora.perevozkadev.ui.search_order.filter.FilterFragment;
import com.foora.perevozkadev.ui.search_order.filter.model.Filter;
import com.foora.perevozkadev.ui.search_order.orders.OrdersFragment;
import com.foora.perevozkadev.utils.AppUtils;
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

    @Override
    public CharSequence getPageTitle(int position) {
        String countryFirst = "  ";
        String countrySecond = "  ";

        Log.d("SearchOrderPagerAdapter", "getPageTitle: " + getItem(position));

        Filter filter = getItem(position);

        if (filter.getLoadingPlaces() != null && !filter.getLoadingPlaces().isEmpty()
                && filter.getLoadingPlaces().size() >= 1) {

            Place place = filter.getLoadingPlaces().get(0);
            int index = place.getName().split(",").length - 1;
            countryFirst = place.getName().split(",")[index].replaceAll("\\s", "");

            Log.d("SearchOrderPagerAdapter", "getPageTitle: " + countryFirst);

        }

        if (filter.getUnloadingPlaces() != null && !filter.getUnloadingPlaces().isEmpty()
                && filter.getUnloadingPlaces().size() >= 1) {

            Place place = filter.getUnloadingPlaces().get(0);
            int index = place.getName().split(",").length - 1;
            countrySecond = place.getName().split(",")[index].replaceAll("\\s", "");

            Log.d("SearchOrderPagerAdapter", "getPageTitle: " + countrySecond);

        }


        switch (position) {
            case 0:
                return "";
            default:
                return String.format("%s - %s", AppUtils.getCountryCode(countryFirst)
                        , AppUtils.getCountryCode(countrySecond));
        }
    }
}
