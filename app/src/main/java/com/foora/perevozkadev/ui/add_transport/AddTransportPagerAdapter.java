package com.foora.perevozkadev.ui.add_transport;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foora.perevozkadev.ui.add_transport.general_info.FragmentGeneralInfo;
import com.foora.perevozkadev.ui.add_transport.register_info.FragmentRegisterInfo;

/**
 * Created by Alexander Matvienko on 10.12.2018.
 */
public class AddTransportPagerAdapter extends FragmentPagerAdapter {

    public AddTransportPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentGeneralInfo.newInstance();
            case 1:
                return FragmentRegisterInfo.newInstance();
        }
        return FragmentGeneralInfo.newInstance();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Основная информация";
            case 1:
                return "Регистрационная информация";
            default:
                return null;
        }

    }
}
