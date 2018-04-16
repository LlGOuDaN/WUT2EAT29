package com.example.l8411.wut2eat29.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;

import com.example.l8411.wut2eat29.Adapter.NavigationPagerAdapter;
import com.example.l8411.wut2eat29.Fragment.FriendListFragment;
import com.example.l8411.wut2eat29.Fragment.ProfileFragment;
import com.example.l8411.wut2eat29.Model.UserProfile;
import com.example.l8411.wut2eat29.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentManager fragmentManager;
    private SupportMapFragment mMapFragment;
    private ProfileFragment mProfileFragmet;
    private Fragment mFriendFragment;
    private NavigationPagerAdapter navigationPagerAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fragmentManager = getSupportFragmentManager();
        navigationPagerAdapter = new NavigationPagerAdapter(fragmentManager);
        viewPager = this.findViewById(R.id.container);
        viewPager.setAdapter(navigationPagerAdapter);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

        } else {
            mMapFragment = (SupportMapFragment) navigationPagerAdapter.getItem(0);
            mMapFragment.getMapAsync(this);
        }


//        TODO: add and create other fragments
//        friendListFragment = FriendListFragment.newInstance("asd","qwd");


        //enable BottomNavigationView here
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    mMapFragment = (SupportMapFragment) navigationPagerAdapter.getItem(0);
                    mMapFragment.getMapAsync(MapsActivity.this);
                    viewPager.setCurrentItem(0);
//                    mMapFragment.getMapAsync(MapsActivity.this);
                    return true;
                case R.id.navigation_friend:
                    //This part is just for test my learning of switching fragments
                    //mMapFragment2 = SupportMapFragment.newInstance();
//                    mFriendFragment = new FriendListFragment();
//                    fragmentManager.beginTransaction().replace(R.id.container, mFriendFragment).commit();
                    //justforfun = false;
                    //mMapFragment2.getMapAsync(MapsActivity.this);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_profile:
//                    String[] top3 = {"Aa", "Bb", "Cc"};
//                    mProfileFragmet = mProfileFragmet.newInstance(new UserProfile(001,top3, new ArrayList<String>(), new ArrayList<String>()));
//                    fragmentManager.beginTransaction().replace(R.id.container,mProfileFragmet).commit();
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        Log.d("in", "no in 1");
        double Lat = 0;
        double log = 0;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            Log.d("emmmm", "wtf");
            Location location = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            Lat = location.getLatitude();
            log = location.getLongitude();
            Log.d("in", "no in 2");
            mMap.setMyLocationEnabled(true);
        }


        LatLng mPosition = new LatLng(Lat, log);
        mMap.addMarker(new MarkerOptions().position(mPosition).title("Marker my position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mPosition));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_addContact) {

            return true;
        }

        if (id == R.id.action_startVote) {

            return true;
        }

        if (id == R.id.action_Invitation) {

            return true;
        }

        if (id == R.id.action_Setting) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mMapFragment = (SupportMapFragment) navigationPagerAdapter.getItem(0);
                mMapFragment.getMapAsync(this);
            }else{
                finish();
                System.exit(0);
            }
        }
    }
}