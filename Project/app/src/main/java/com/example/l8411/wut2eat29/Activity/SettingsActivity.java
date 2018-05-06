package com.example.l8411.wut2eat29.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Fragment.SetStatusFragment;
import com.example.l8411.wut2eat29.Fragment.SettingPageFragment;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements SettingPageFragment.OnFragmentInteractionListener {


    private SettingPageFragment settingFragment;
    private SetStatusFragment statusFragment;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        settingFragment = new SettingPageFragment();
        statusFragment = new SetStatusFragment();


        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.SettingLayoutContainer, settingFragment);
            ft.commit();
        }


//        mtvLan = (TextView) findViewById(R.id.textViewLanguage);
//        mtvLan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        mtvLogOut = (TextView) findViewById(R.id.textViewLogOut);
//        mtvLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        mtvStatus = (TextView) findViewById(R.id.textViewStatus);
//        mtvStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment mStatusfragment = new SetStatusFragment();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.SettingLayoutContainer, mStatusfragment);
//                ft.addToBackStack("SettingsActivity");
//
//                ft.commit();
////                getSupportFragmentManager().beginTransaction()
////                        .add(R.id.SettingLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack("Settings").commit();
//            }
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SettingsActivity.this.findViewById(R.id.textViewLanguage).setVisibility(View.VISIBLE);
        SettingsActivity.this.findViewById(R.id.textViewLogOut).setVisibility(View.VISIBLE);
        SettingsActivity.this.findViewById(R.id.textViewStatus).setVisibility(View.VISIBLE);
    }

    public void openLocalSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onFragmentInteraction(View clickedView) {

        if (clickedView.getId() == R.id.textViewLanguage) {
            Log.d("Setting", "lan");
            openLocalSettings();
            return;
        }

        if (clickedView.getId() == R.id.textViewStatus) {
            Log.d("Setting", "status");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.SettingLayoutContainer, statusFragment);
            ft.commit();
            ft.addToBackStack("Status");

            return;
        }

        if (clickedView.getId() == R.id.textViewLogOut) {
            Log.d("Setting", "logout");
            //TODO create Log out function for Auth

            //log out
            mAuth.signOut();
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return;
        }

    }
}
