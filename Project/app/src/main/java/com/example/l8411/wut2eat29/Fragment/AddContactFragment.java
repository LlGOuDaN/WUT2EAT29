package com.example.l8411.wut2eat29.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.l8411.wut2eat29.Activity.MainActivity;
import com.example.l8411.wut2eat29.Fragment.BottomNavi.FriendListFragment;
import com.example.l8411.wut2eat29.Model.Invitation;
import com.example.l8411.wut2eat29.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddContactFragment extends android.support.v4.app.Fragment implements View.OnKeyListener, View.OnClickListener {


    private FriendListFragment.OnFragmentInteractionListener mListener;
    private EditText edit_uid;
    private View confirmButton;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;


    public AddContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddContactFragment newInstance() {
        AddContactFragment fragment = new AddContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        confirmButton = view.findViewById(R.id.button_send_invitation);
        edit_uid = view.findViewById(R.id.edit_uid);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(this);

        confirmButton.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            Log.d("back", "back click");
            MainActivity main = (MainActivity) getContext();
            getFragmentManager().popBackStack("AddContact", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            main.navigationView.setVisibility(View.VISIBLE);
            main.viewPager.setVisibility(View.VISIBLE);
            if (main.viewPager.getCurrentItem() == 0) {
                main.findViewById(R.id.search_view).setVisibility(View.VISIBLE);
                main.findViewById(R.id.fab_here).setVisibility(View.VISIBLE);
            }
            return true;
        }
        Log.d("back", "back click");
        return false;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_send_invitation){
            final String recv_uid = edit_uid.getText().toString();
            Query query = mRef.child("user").orderByChild("userID").equalTo(recv_uid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        DatabaseReference sendRef = mRef.child("user").child(mAuth.getCurrentUser().getUid());
                        sendRef.child("userID").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String send_uid = (String) dataSnapshot.getValue();
                                String invitationKey = mRef.child("invitation").push().getKey();
                                mRef.child("invitation").child(invitationKey).setValue(new Invitation(send_uid, recv_uid));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle(R.string.sorry);
                        builder.setMessage(R.string.user_does_not_exist);
                        builder.create().show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
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
