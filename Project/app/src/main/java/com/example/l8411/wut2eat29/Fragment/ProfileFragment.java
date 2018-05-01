package com.example.l8411.wut2eat29.Fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Activity.LoginActivity;
import com.example.l8411.wut2eat29.Activity.MapsActivity;
import com.example.l8411.wut2eat29.Model.UserProfile;
import com.example.l8411.wut2eat29.R;


public class ProfileFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PROFILE = "profile";

    // TODO: Rename and change types of parameters
    private UserProfile mProfile;
    private TextView mUID;
    private TextView mTopAChoice;
    private TextView mTopBChoice;
    private TextView mTopCChoice;
    private String[] top3Choices;
    private TextView mViewHistory;
    private TextView mViewVotes;
    private TextView mLogout;


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
        // Inflate the layout for this fragment
        mProfile = getArguments().getParcelable(ARG_PROFILE);
        top3Choices = mProfile.getTop3Choice();
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mUID = rootView.findViewById(R.id.UID);
        mTopAChoice = rootView.findViewById(R.id.topChoiceA);
        mTopBChoice = rootView.findViewById(R.id.topChoiceB);
        mTopCChoice = rootView.findViewById(R.id.topChoiceC);

        mViewHistory = rootView.findViewById(R.id.view_history);
        mViewVotes = rootView.findViewById(R.id.view_votes);
        mLogout = rootView.findViewById(R.id.log_out);


        mUID.setText(mProfile.getUserID()+"");
        mTopAChoice.setText(top3Choices[0]);
        mTopBChoice.setText(top3Choices[1]);
        mTopCChoice.setText(top3Choices[2]);

        mViewHistory.setOnClickListener(this);
        mViewVotes.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        int id = view.getId();
        if(id == R.id.view_history){
            Log.d("History", "click");
            ft.add(R.id.fragment_container, HistoryFragment.newInstance("1")).commit();
            ft.addToBackStack("History");
            return;
        }
        if(id == R.id.view_votes){
            Log.d("Votes", "click");
            return;
        }
        if(id == R.id.log_out){
            Log.d("Logout", "logout");
            return;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
