package com.example.l8411.wut2eat29.Fragment.BottomNavi;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Activity.SettingsActivity;
import com.example.l8411.wut2eat29.Fragment.HistoryFragment;
import com.example.l8411.wut2eat29.Model.History;
import com.example.l8411.wut2eat29.Model.Restaurant;
import com.example.l8411.wut2eat29.Model.UserProfile;
import com.example.l8411.wut2eat29.R;
import com.example.l8411.wut2eat29.Utils.utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ProfileFragment extends android.support.v4.app.Fragment implements View.OnClickListener, View.OnLongClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PROFILE = "profile";

    // TODO: Rename and change types of parameters
    private Bitmap bitmap;
    private UserProfile mProfile;
    private TextView mTodayChoice;
    private TextView mNickName;
    private TextView mTopAChoice;
    private TextView mTopBChoice;
    private TextView mTopCChoice;
    private ArrayList<String> top3Choices;
    private TextView mViewHistory;
    private TextView mViewVotes;
    private TextView mSetting;
    private TextView mUserId;
    private View userView;
    private ImageView head;


    DatabaseReference mRef;
    FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(UserProfile userProfile) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PROFILE, userProfile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProfile = getArguments().getParcelable(ARG_PROFILE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //firebase stuff
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        // Inflate the layout for this fragment
        mProfile = getArguments().getParcelable(ARG_PROFILE);
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        userView = rootView.findViewById(R.id.view_user);
        mNickName = rootView.findViewById(R.id.UID);
        mTopAChoice = rootView.findViewById(R.id.topChoiceA);
        mTopBChoice = rootView.findViewById(R.id.topChoiceB);
        mTopCChoice = rootView.findViewById(R.id.topChoiceC);
        mUserId = rootView.findViewById(R.id.user_id);
        head = rootView.findViewById(R.id.head);
        mTodayChoice = rootView.findViewById(R.id.todayChoice);
        mViewHistory = rootView.findViewById(R.id.view_history);
        mViewVotes = rootView.findViewById(R.id.view_votes);
        mSetting = rootView.findViewById(R.id.setting);

        final DatabaseReference mUserRef = mRef.child("user");
        mUserRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("wtf", dataSnapshot.getKey());
                mProfile = dataSnapshot.getValue(UserProfile.class);
//                mProfile = new UserProfile("1212");
                mNickName.setText(mProfile.getUserNickName());
                top3Choices = (ArrayList) mProfile.getTop3Choice();
                mTopAChoice.setText(top3Choices.get(0));
                mTopBChoice.setText(top3Choices.get(1));
                mTopCChoice.setText(top3Choices.get(2));
                mUserId.setText(String.format("UID: %s", mProfile.getUserID()));
                History choice = mProfile.getTodayChoice();
                if(choice == null){
                    choice = getEmptyHistory();
                }
                //
                if(choice.getDateFormated().equals("NULL") || !choice.getDateFormated().equals(utils.parseDate( Calendar.getInstance().getTime())) ){
                    mTodayChoice.setText(R.string.not_decided_yet);
                    if(!choice.getDateFormated().equals("NULL")){
                        if(mProfile.getHistory() == null){
                            mProfile.setHistory(new ArrayList<History>());
                        }
                        mProfile.getHistory().add(0,choice);
                        mUserRef.child(mAuth.getCurrentUser().getUid()).child("history").setValue(mProfile.getHistory());
                        mUserRef.child(mAuth.getCurrentUser().getUid()).child("todayChoice").setValue(getEmptyHistory());
                    }
                }else{
                    mTodayChoice.setText(String.format("Choice: %s", mProfile.getTodayChoice().getResturant().getName()));
                }

                new GetImageTask().execute(mProfile.getAvatarUrl());
            }

            private History getEmptyHistory() {
                History history = new History();
                Restaurant restaurant = new Restaurant();
                restaurant.setName("NULL");
                restaurant.setVicinity("NULL");
                history.setResturant(restaurant);
                history.setDateFormated("NULL");
                history.setDate(Calendar.getInstance().getTime());
                return history;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mViewHistory.setOnClickListener(this);
        mViewVotes.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        userView.setOnLongClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        int id = view.getId();
        if(id == R.id.view_history){
            Log.d("History", "click");
            ft.add(R.id.fragment_container, HistoryFragment.newInstance("UID")).commit();
            ft.addToBackStack("History");
            return;
        }
        if(id == R.id.view_votes){
            Log.d("Votes", "click");
            return;
        }
        if(id == R.id.setting){
            Log.d("Setting", "setting");
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
            return;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        Log.d("long", R.id.view_user + "" + view.getId() + "");
        if(view.getId() == R.id.view_user){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getString(R.string.editNickName));
            final EditText editText = new EditText(getContext());
            editText.setText(mProfile.getUserNickName());
            editText.setSelection(mProfile.getUserNickName().length());
            builder.setView(editText);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String newNickName = editText.getText().toString();
                    mRef.child("user").child(mAuth.getCurrentUser().getUid()).child("userNickName").setValue(newNickName);
                }
            });

            builder.setNegativeButton(android.R.string.cancel, null);
            builder.create().show();
        }
        return false;
    }

    class GetImageTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String urlString = strings[0];
            try {
                InputStream in = new URL(urlString).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            head.setImageBitmap(bitmap);
        }
    }

}
