package com.example.gm7.instaproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gm7.instaproject.Adapter.HomeAdapter;
import com.example.gm7.instaproject.R;

/**
 * Created by emad on 12/29/17.
 *
 */

public class HomeFragment extends Fragment {
    private RecyclerView mHomeRecycler;
    private HomeAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getActivity() != null;
        getActivity().setTitle(getString(R.string.menu_home));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_home_layout,container,false);
        mHomeRecycler = fragmentView.findViewById(R.id.home_recycler);
        mAdapter = new HomeAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mHomeRecycler.setLayoutManager(layoutManager);
        mHomeRecycler.setHasFixedSize(true);
        mHomeRecycler.setAdapter(mAdapter);
        return fragmentView;
    }
}
