package com.example.l8411.wut2eat29.Activity;

import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Fragment.SetStatusFragment;
import com.example.l8411.wut2eat29.R;

public class SettingsActivity extends AppCompatActivity {


    private TextView mtvLan;
    private TextView mtvLogOut;
    private TextView mtvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null) {
            setContentView(R.layout.activity_settings);
            mtvLan = (TextView) findViewById(R.id.textViewLanguage);
            mtvLan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLocalSettings();
                }
            });

            mtvLogOut = (TextView) findViewById(R.id.textViewLogOut);
            mtvLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            mtvStatus = (TextView) findViewById(R.id.textViewStatus);
            mtvStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment mStatusfragment = new SetStatusFragment();
//                    SettingsActivity.this.findViewById(R.id.textViewLanguage).setVisibility(View.GONE);
//                    SettingsActivity.this.findViewById(R.id.textViewLogOut).setVisibility(View.GONE);
//                    SettingsActivity.this.findViewById(R.id.textViewStatus).setVisibility(View.GONE);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.SettingLayoutContainer, mStatusfragment);
                    ft.addToBackStack("SettingsActivity");

                    ft.commit();
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.SettingLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack("Settings").commit();
                }
            });
        }
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
}
