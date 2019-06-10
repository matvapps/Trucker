package com.foora.perevozkadev.ui.staff.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foora.perevozkadev.ui.staff.EmployeesFragment;

/**
 * Created by Alexandr.
 */
public class EmployeesPagerAdapter extends FragmentPagerAdapter {

    public EmployeesPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return EmployeesFragment.newInstance(EmployeesFragment.MANAGER);
            case 1:
                return EmployeesFragment.newInstance(EmployeesFragment.DRIVER);
            case 2:
                return EmployeesFragment.newInstance(EmployeesFragment.ARCHIVE);

        }
        return EmployeesFragment.newInstance(EmployeesFragment.MANAGER);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Менеджеры";
            case 1:
                return "Водители";
            case 2:
                return "Архив";
            default:
                return null;
        }
    }
}