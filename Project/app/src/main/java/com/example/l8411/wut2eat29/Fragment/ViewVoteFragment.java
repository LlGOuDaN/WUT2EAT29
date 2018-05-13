package com.example.l8411.wut2eat29.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.l8411.wut2eat29.Adapter.HistoryAdapter;
import com.example.l8411.wut2eat29.Adapter.ViewVoteAdapter;
import com.example.l8411.wut2eat29.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewVoteFragment extends Fragment {

    private static final String ARG_UID = "UID";

    // TODO: Rename and change types of parameters
    private String UID;

    public ViewVoteFragment() {
        // Required empty public constructor
    }

    public static ViewVoteFragment newInstance(String UID){
        ViewVoteFragment fragment = new ViewVoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_UID,UID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            UID = getArguments().getString(ARG_UID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_vote, container, false);
        TextView mTextView = view.findViewById(R.id.view_vote_top_text);
        mTextView.setText(String.format(getString(R.string.your_today_s_vote_s),"Chauncey"));
        RecyclerView recyclerView = view.findViewById(R.id.view_vote_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        ViewVoteAdapter adapter = new ViewVoteAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

}
