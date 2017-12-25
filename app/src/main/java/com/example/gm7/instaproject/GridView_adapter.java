package com.example.gm7.instaproject;

/**
 * Created by GM7 on 25/12/2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Clipcodes on 08/11/2017.
 */

public class GridView_adapter  extends RecyclerView.Adapter<GridView_adapter.ViewHolder>{

    Activity activity;
    Fragment fragment;
    List<Item_Pojo> items;

    public GridView_adapter(Menu1 activity, List<Item_Pojo> items){
        this.activity = activity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.itemmodel_grid, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridView_adapter.ViewHolder hholder, int position) {
        hholder.image.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}