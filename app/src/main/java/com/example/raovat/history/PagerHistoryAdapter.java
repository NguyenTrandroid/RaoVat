package com.example.raovat.history;


import android.util.Log;

import com.example.raovat.tabprofile.TabDeny;
import com.example.raovat.tabprofile.TabSell;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class PagerHistoryAdapter extends FragmentStatePagerAdapter {
    public PagerHistoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                TabSuccess tabSuccess = new TabSuccess();
                Log.d("AAA","0");
                return tabSuccess;
            }
            case 1: {
                TabCancel tabCancel  = new TabCancel();
                Log.d("AAA","1");

                return tabCancel;
            }
            case 2: {
                TabSave tabSave  = new TabSave();
                Log.d("AAA","1");

                return tabSave;
            }

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="Đã bán";
                break;
            case 1:
                title="Đã hủy";
                break;
            case 2:
                title="Tin đã lưu";
                break;
        }
        return title;
    }
}
