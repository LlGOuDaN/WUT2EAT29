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
import android.support.v4.app.FragmentManager;
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
import com.example.l8411.wut2eat29.Fragment.ViewVoteFragment;

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
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


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
        mUserRef.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProfile = dataSnapshot.getValue(UserProfile.class);
//                mProfile = new UserProfile("1212");
                mNickName.setText(mProfile.getUserNickName());
                if(mProfile.getHistory() == null){
                    mProfile.setHistory(new ArrayList<History>());
                }
                top3Choices = getTopThreeChoice(mProfile.getHistory());
                mTopAChoice.setText(top3Choices.get(0));
                mTopBChoice.setText(top3Choices.get(1));
                mTopCChoice.setText(top3Choices.get(2));
                mUserId.setText(String.format("UID: %s", mProfile.getUserID()));
                History choice = mProfile.getTodayChoice();
                if(choice == null){
                    choice = utils.getEmptyHistory();
                }

                if(choice.getDateFormated().equals("NULL") || !choice.getDateFormated().equals(utils.parseDate( Calendar.getInstance().getTime())) ){
                    mTodayChoice.setText(R.string.not_decided_yet);
                    if(!choice.getDateFormated().equals("NULL")){
                        mProfile.getHistory().add(0,choice);
                        mUserRef.child(mAuth.getCurrentUser().getUid()).child("history").setValue(mProfile.getHistory());
                        mUserRef.child(mAuth.getCurrentUser().getUid()).child("todayChoice").setValue( choice = utils.getEmptyHistory());
                    }
                }else{
                    mTodayChoice.setText(String.format("Choice: %s", mProfile.getTodayChoice().getResturant().getName()));
                }

                new GetImageTask().execute(mProfile.getAvatarUrl());
            }

            private ArrayList<String> getTopThreeChoice(List<History> history) {
                ArrayList<String> resTop3 = new ArrayList<>();
                Map<String, Integer> frequentMap = new HashMap<>();
                for(int i = 0; i < history.size(); i++){
                    String name = history.get(i).getResturant().getName();
                    if(frequentMap.containsKey(name)){
                        int current = frequentMap.get(name);
                        frequentMap.put(name,current++);
                    }else{
                        frequentMap.put(name, 1);
                    }
                }

                Set<Map.Entry<String,Integer>> set = frequentMap.entrySet();
                List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String, Integer>>(set);
                Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> stringIntegerEntry, Map.Entry<String, Integer> t1) {
                        return (t1.getValue().compareTo(stringIntegerEntry.getValue()));
                    }
                });

                if(list.size() <= 3){
                    for(int i = 0; i < list.size(); i++){
                        resTop3.add(list.get(i).getKey());
                    }
                    for(int i = 0; i < 3-list.size(); i++){
                        resTop3.add("N/A");
                    }
                }else{
                    for(int i = 0; i < 3; i++){
                        resTop3.add(list.get(i).getKey());
                    }
                }


                return resTop3;
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
            ft.add(R.id.fragment_container, ViewVoteFragment.newInstance("UID")).commit();
            ft.addToBackStack("Votes");
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
