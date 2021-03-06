package com.foora.perevozkadev.ui.messages;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Alexandr.
 */
public class MessagesPagerAdapter extends FragmentPagerAdapter {
    public MessagesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return MessagesFragment.newInstance();
        } else {
            return MessagesFragment.newInstance();
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
            case 1:
                return "Сообщения";
            case 0:
                return "Оповещения";
            default:
                return null;
        }
    }
}
