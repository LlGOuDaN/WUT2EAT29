package com.example.l8411.wut2eat29.Fragment;

import android.content.ClipData;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SettingPageFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    public SettingPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting_page, container, false);
        setOnclickListener(view);
        return view;
    }

    private void setOnclickListener(View view) {
        TextView lan = view.findViewById(R.id.textViewLanguage);
        TextView status = view.findViewById(R.id.textViewStatus);
        TextView logout = view.findViewById(R.id.textViewLogOut);

        lan.setOnClickListener(this);
        status.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    public void onButtonPressed(View clickedView) {
        if (mListener != null) {
            mListener.onFragmentInteraction(clickedView);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        mListener.onFragmentInteraction(view);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(View clickedView);
    }
}
