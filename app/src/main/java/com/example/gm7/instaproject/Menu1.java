package com.example.gm7.instaproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 18/09/16.
 */


public class Menu1 extends Fragment {
    RecyclerView gridRecycler;
    GridView_adapter gridView_adapter;
    List<Item_Pojo> socialicon;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.profile_screen, container, false);


        ///
        gridRecycler = (RecyclerView)view.findViewById(R.id.socialGrid);

        Data();

        gridRecycler.setHasFixedSize(true);


        //   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridRecycler.setLayoutManager(gridLayoutManager);
        gridView_adapter = new GridView_adapter(this, socialicon);

        gridRecycler.setAdapter(gridView_adapter);
        return view ;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
    }
    public void Data(){
        socialicon = new ArrayList<>();
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));
        socialicon.add(new Item_Pojo(R.drawable.ic_launcher_background));




        //Thanks for Watching
        //Subscribe Clipcodes
    }
}
