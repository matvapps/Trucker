package com.foora.perevozkadev.ui.employees.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.foora.perevozkadev.ui.employees.EmployeesFragment;

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

        }
        return EmployeesFragment.newInstance(EmployeesFragment.MANAGER);
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
                return "Менеджеры";
            case 1:
                return "Водители";
            default:
                return null;
        }
    }
}