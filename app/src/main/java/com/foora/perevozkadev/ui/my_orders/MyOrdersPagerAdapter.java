package com.foora.perevozkadev.ui.my_orders;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foora.perevozkadev.ui.my_transport.TransportsFragment;

public class MyOrdersPagerAdapter extends FragmentPagerAdapter {
    public MyOrdersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return OrdersFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Активные";
            case 1:
                return "Выполненные";
            default:
                return null;
        }
    }
}
