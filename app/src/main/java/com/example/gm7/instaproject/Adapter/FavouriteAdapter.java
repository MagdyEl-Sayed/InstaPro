package com.example.gm7.instaproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gm7.instaproject.R;

/**
 * Created by emad on 1/2/18.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.favouriteHolder> {
    private Context mContext;

    @Override
    public favouriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View rootView = LayoutInflater.from(mContext)
                .inflate(R.layout.favourite_item_row,parent,false);
        return new favouriteHolder(rootView);
    }

    @Override
    public void onBindViewHolder(favouriteHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class favouriteHolder extends RecyclerView.ViewHolder {
        public favouriteHolder(View itemView) {
            super(itemView);
        }
    }
}
