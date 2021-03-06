package com.foora.perevozkadev.ui.my_transport;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foora.perevozkadev.ui.my_transport.transport.TransportsFragment;

public class MyTransportPagerAdapter extends FragmentPagerAdapter {
    public MyTransportPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return TransportsFragment.newInstance(TransportsFragment.GARAGE_TYPE);
        } else {
            return TransportsFragment.newInstance(TransportsFragment.ARCHIVE_TYPE);
        }
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
                return "Гараж";
            case 1:
                return "Архив";
            default:
                return null;
        }
    }
}
