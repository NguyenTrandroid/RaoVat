package com.example.raovat.tabprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raovat.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FragmentProfile extends Fragment {
    private ViewPager pager;
    private TabLayout tabLayout;
    TabSell tabSell;
    TabDeny tabDeny;
    private SharedPreferences sharedPreferences;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, null);
        sharedPreferences = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        pager = view.findViewById(R.id.vp_profile);
        tabLayout = view.findViewById(R.id.tl_profile);
        tabSell = new TabSell();
        tabDeny = new TabDeny();
        setupViewPager(pager, tabSell, tabDeny);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch ((tab.getPosition())) {
                    case 0:
                        if (tabDeny.checkupdate) {
                            tabSell.listPost.clear();
                            tabSell.getListPostUser(sharedPreferences.getString("IdUser", ""));
                            tabDeny.checkupdate = false;
                        }
                        break;
                    case 1:
                        if (tabSell.checkupdate) {
                            tabDeny.listPostDeny.clear();
                            tabDeny.getListPostUserDeny(sharedPreferences.getString("IdUser", ""));
                            tabSell.checkupdate = false;
                        }
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    private void setupViewPager(ViewPager viewPager, TabSell tabSell, TabDeny tabDeny) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(tabSell, "Đang bán");
        adapter.addFragment(tabDeny, "Đã bán");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}