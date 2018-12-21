package com.foora.perevozkadev.ui.add_order;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foora.perevozkadev.ui.add_order.cargo_info.CargoInfoFragment;
import com.foora.perevozkadev.ui.add_order.contact_info.ContactInfoFragment;
import com.foora.perevozkadev.ui.add_order.route.RouteFragment;

/**
 * Created by Alexander Matvienko on 10.12.2018.
 */
public class AddOrderPagerAdapter extends FragmentPagerAdapter {

    public AddOrderPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return RouteFragment.newInstance();
            case 1:
                return CargoInfoFragment.newInstance();
            case 2:
                return ContactInfoFragment.newInstance();
        }
        return RouteFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Маршрут";
            case 1:
                return "Информация о грузе";
            case 2:
                return "Контактная информация";
            default:
                return null;
        }

    }
}
