package com.example.gm7.instaproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gm7.instaproject.Activities.CreateProjectActivity;
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

        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_add_project){
            Intent intent = new Intent(getActivity(), CreateProjectActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
