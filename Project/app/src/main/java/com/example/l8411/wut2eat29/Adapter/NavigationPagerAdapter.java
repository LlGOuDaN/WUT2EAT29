package com.example.l8411.wut2eat29.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.example.l8411.wut2eat29.Fragment.BottomNavi.FriendListFragment;
import com.example.l8411.wut2eat29.Fragment.BottomNavi.ProfileFragment;
import com.example.l8411.wut2eat29.Model.UserProfile;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.l8411.wut2eat29.Utils.utils.CURRENT_USER;

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
        mList.add(ProfileFragment.newInstance(new UserProfile("1")));
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
