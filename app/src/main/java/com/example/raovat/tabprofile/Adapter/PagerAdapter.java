package com.example.raovat.tabprofile.Adapter;


import android.os.Bundle;
import android.util.Log;

import com.example.raovat.tabprofile.TabDeny;
import com.example.raovat.tabprofile.TabSell;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                TabSell sell = new TabSell();



                return sell;
            }
            case 1: {
                TabDeny deny = new TabDeny();
                return deny;
            }

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="Đang bán";
                break;
            case 1:
                title="Đã bán";
                break;
        }
        return title;
    }
}