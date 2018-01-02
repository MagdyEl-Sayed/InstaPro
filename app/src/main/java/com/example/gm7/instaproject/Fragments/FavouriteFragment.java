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

import com.example.gm7.instaproject.Adapter.FavouriteAdapter;
import com.example.gm7.instaproject.R;

/**
 * Created by emad on 1/2/18.
 */

public class FavouriteFragment extends Fragment {
    private RecyclerView mFavRecycler;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_favourite_layout,container,false);
        mFavRecycler = fragmentView.findViewById(R.id.fav_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFavRecycler.setLayoutManager(layoutManager);
        mFavRecycler.setHasFixedSize(true);
        mFavRecycler.setAdapter(new FavouriteAdapter());
        return fragmentView;
    }
}
