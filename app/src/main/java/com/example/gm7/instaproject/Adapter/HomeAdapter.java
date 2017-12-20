package com.example.gm7.instaproject.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by emad on 12/20/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.homeViewHolder>{


    @Override
    public homeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(homeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class homeViewHolder extends RecyclerView.ViewHolder {
        public homeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
