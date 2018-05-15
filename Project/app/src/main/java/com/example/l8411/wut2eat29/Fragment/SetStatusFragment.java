package com.example.l8411.wut2eat29.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Activity.SettingsActivity;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Callback} interface
 * to handle interaction events.
 */
public class SetStatusFragment extends android.support.v4.app.Fragment implements View.OnKeyListener, View.OnClickListener {

    private Callback mCallback;

    public SetStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_status, container, false);
        TextView mtvFree = (TextView) view.findViewById(R.id.textViewFree);
        TextView mtvBusy = (TextView) view.findViewById(R.id.textViewBusy);
        mtvBusy.setOnClickListener(this);
        mtvFree.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mCallback != null) {
            mCallback.onStatusSelected();
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            Log.d("back", "back click");
            SettingsActivity main = (SettingsActivity) getContext();
            getFragmentManager().popBackStack("SettingsActivity", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            main.findViewById(R.id.textViewLanguage).setVisibility(View.VISIBLE);
            main.findViewById(R.id.textViewLogOut).setVisibility(View.VISIBLE);
            main.findViewById(R.id.textViewStatus).setVisibility(View.VISIBLE);
            return true;
        }
        Log.d("back", "back click");
        return false;
    }

    @Override
    public void onClick(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

        if(view.getId() == R.id.textViewFree){
            mRef.child("user").child(mAuth.getCurrentUser().getUid()).child("status").setValue(0);
            return;
        }

        if(view.getId() == R.id.textViewBusy){
            mRef.child("user").child(mAuth.getCurrentUser().getUid()).child("status").setValue(1);
            return;
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface Callback {
        // TODO: Update argument type and name
        void onStatusSelected();
    }
}
