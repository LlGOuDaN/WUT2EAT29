package com.example.l8411.wut2eat29.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.l8411.wut2eat29.Adapter.NavigationPagerAdapter;

import com.example.l8411.wut2eat29.Fragment.AddContactFragment;
import com.example.l8411.wut2eat29.Fragment.InvitationFragment;

import com.example.l8411.wut2eat29.Fragment.BottomNavi.ProfileFragment;
import com.example.l8411.wut2eat29.Fragment.StartAVoteFragment;
import com.example.l8411.wut2eat29.GooglePlaces.GetNearbyPlacesData;
import com.example.l8411.wut2eat29.Model.History;
import com.example.l8411.wut2eat29.Model.Restaurant;
import com.example.l8411.wut2eat29.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, ViewPager.OnPageChangeListener, SearchView.OnQueryTextListener {

    private GoogleMap mMap;
    private FragmentManager fragmentManager;
    private SupportMapFragment mMapFragment;
    private ProfileFragment mProfileFragmet;
    private Fragment mAddContactFragment;
    private Fragment mStartAVoteFragment;
    private Fragment mInvitationFragment;
    private Fragment mFriendFragment;
    private NavigationPagerAdapter navigationPagerAdapter;
    public ViewPager viewPager;
    public BottomNavigationView navigationView;
    private String previousFrag;
    private SearchView searchView;
    private String keyWord;
    private FloatingActionButton fab_here;
    private Restaurant choice = null;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        previousFrag = "";
        keyWord = "food";
        fab_here = this.findViewById(R.id.fab_here);
        searchView = this.findViewById(R.id.search_view);
        fragmentManager = getSupportFragmentManager();
        navigationPagerAdapter = new NavigationPagerAdapter(fragmentManager);
        viewPager = this.findViewById(R.id.container);
        viewPager.setAdapter(navigationPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

        } else {
            mMapFragment = (SupportMapFragment) navigationPagerAdapter.getItem(0);
            mMapFragment.getMapAsync(this);
        }


        //enable BottomNavigationView here
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);

        //search View
        searchView.setOnQueryTextListener(this);

        //fab_here
        fab_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("You Choice of Day");
                if (choice != null) {
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                            History dayOfChoice = new History(choice, Calendar.getInstance().getTime());
                            mRef.child("user").child(mAuth.getCurrentUser().getUid()).child("todayChoice").setValue(dayOfChoice);
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, null);
                    builder.setMessage(choice.getName() + "\n" + choice.getVicinity() + "\nAre you sure?");
                } else {
                    builder.setMessage("Please make a choice first.");
                }

                builder.create().show();
            }
        });


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager.popBackStack();
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    mMapFragment = (SupportMapFragment) navigationPagerAdapter.getItem(0);
                    mMapFragment.getMapAsync(MainActivity.this);
                    MainActivity.this.findViewById(R.id.search_view).setVisibility(View.VISIBLE);
                    MainActivity.this.findViewById(R.id.fab_here).setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_friend:
                    MainActivity.this.findViewById(R.id.search_view).setVisibility(View.GONE);
                    MainActivity.this.findViewById(R.id.fab_here).setVisibility(View.GONE);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_profile:
                    MainActivity.this.findViewById(R.id.search_view).setVisibility(View.GONE);
                    MainActivity.this.findViewById(R.id.fab_here).setVisibility(View.GONE);
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
        mMap.clear();
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


        String url = gerUrl(Lat, log, keyWord);
        Object dataTransfer[] = {googleMap, url};
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(dataTransfer);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                } else {
                    choice = new Restaurant(marker.getTitle(), marker.getSnippet());
                    marker.showInfoWindow();
                }

                return true;
            }
        });
    }

    private String gerUrl(double lat, double log, String keyword) {
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + lat + "," + log);
        sb.append("&radius=10000");
        sb.append("&keyword=" + keyword);
        sb.append("&key=AIzaSyB9YgxMfphQ23EW4PsHm1YFBCaDZZCGuPY");
        return sb.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fm = this.getSupportFragmentManager();
//        viewPager.setVisibility(View.GONE);

        if (id == R.id.action_addContact) {
            mAddContactFragment = new AddContactFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fm.popBackStack(previousFrag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.add(R.id.fragment_container, mAddContactFragment).commit();
            ft.addToBackStack("AddContact");
            previousFrag = "AddContact";
            navigationView.setVisibility(View.GONE);
            MainActivity.this.findViewById(R.id.search_view).setVisibility(View.GONE);
            MainActivity.this.findViewById(R.id.fab_here).setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            return true;
        }

        if (id == R.id.action_startVote) {
            mStartAVoteFragment = new StartAVoteFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fm.popBackStack(previousFrag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.add(R.id.fragment_container, mStartAVoteFragment).commit();
            ft.addToBackStack("StartAVote");
            previousFrag = "StartAVote";
            navigationView.setVisibility(View.GONE);
            MainActivity.this.findViewById(R.id.search_view).setVisibility(View.GONE);
            MainActivity.this.findViewById(R.id.fab_here).setVisibility(View.GONE);
            return true;
        }

        if (id == R.id.action_Invitation) {
            mInvitationFragment = new InvitationFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fm.popBackStack(previousFrag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ft.add(R.id.fragment_container, mInvitationFragment).commit();
            ft.addToBackStack("Invitation");
            previousFrag = "Invitation";
            navigationView.setVisibility(View.GONE);
            MainActivity.this.findViewById(R.id.search_view).setVisibility(View.GONE);
            MainActivity.this.findViewById(R.id.fab_here).setVisibility(View.GONE);
            return true;
        }

        Log.d("back", "back");

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMapFragment = (SupportMapFragment) navigationPagerAdapter.getItem(0);
                mMapFragment.getMapAsync(this);
            } else {
                finish();
                System.exit(0);
            }
        }
    }

    //pager change listener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position < 3) {
            navigationView.setSelectedItemId(navigationView.getMenu().getItem(position).getItemId());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    //search view
    @Override
    public boolean onQueryTextSubmit(String s) {
        keyWord = s;
        mMapFragment.getMapAsync(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}

