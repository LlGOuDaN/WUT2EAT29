package com.example.l8411.wut2eat29.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.l8411.wut2eat29.Fragment.FriendListFragment;
import com.example.l8411.wut2eat29.Fragment.ProfileFragment;
import com.example.l8411.wut2eat29.Model.UserProfile;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by l8411 on 4/14/2018.
 */

public class NavigationPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;

    public NavigationPagerAdapter(FragmentManager fm) {
        super(fm);
        mList = new ArrayList<>();
        mList.add(SupportMapFragment.newInstance());
        mList.add(FriendListFragment.newInstance());
        String[] top3 = {"Aa", "Bb", "Cc"};
        mList.add(ProfileFragment.newInstance(new UserProfile(001,top3, new ArrayList<String>(), new ArrayList<String>())));
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

}
