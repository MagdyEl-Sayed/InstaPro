package com.example.gm7.instaproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gm7.instaproject.R;

/**
 * Created by emad on 12/20/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.homeViewHolder>{

    private Context mContext;

    @Override
    public homeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.itemmodel_grid,parent,false);
        return new homeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(homeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class homeViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgThumbnails;
        private TextView mTvDescription;



        public homeViewHolder(View itemView) {
            super(itemView);
            mImgThumbnails = itemView.findViewById(R.id.img_thumbnails);
            mTvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
