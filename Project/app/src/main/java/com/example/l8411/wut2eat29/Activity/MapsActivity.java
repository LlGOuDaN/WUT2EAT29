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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;

import com.example.l8411.wut2eat29.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentManager fragmentManager;
    private SupportMapFragment mMapFragment;
    private SupportMapFragment mMapFragment2;
    private boolean justforfun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fragmentManager = getSupportFragmentManager();
        justforfun = true;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mMapFragment = SupportMapFragment.newInstance();
        fragmentManager.beginTransaction().add(R.id.container, mMapFragment).commit();
        mMapFragment.getMapAsync(this);


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
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.container, mMapFragment).commit();
                    justforfun = true;
                    mMapFragment.getMapAsync(MapsActivity.this);
                    return true;
                case R.id.navigation_dashboard:
                    //This part is just for test my learning of switching fragments
                    mMapFragment2 = SupportMapFragment.newInstance();
                    fragmentManager.beginTransaction().replace(R.id.container, mMapFragment2).commit();
                    justforfun = false;
                    mMapFragment2.getMapAsync(MapsActivity.this);
                    return true;
                case R.id.navigation_notifications:

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
        if (justforfun) {
            LatLng beijing = new LatLng(39.9138184, 116.363625);
            mMap.addMarker(new MarkerOptions().position(beijing).title("Marker in Beijing"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(beijing));
        } else {
            Log.d("in", "no in 1");
            double Lat = 0;
            double log = 0;
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }else{
                Log.d("emmmm", "wtf");
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Lat = location.getLatitude();
                log = location.getLongitude();
                Log.d("in", "no in 2");
                mMap.setMyLocationEnabled(true);
            }


            LatLng mPosition = new LatLng(Lat, log);
            mMap.addMarker(new MarkerOptions().position(mPosition).title("Marker my position"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mPosition));

        }

    }
}
