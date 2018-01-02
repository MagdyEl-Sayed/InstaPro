package com.example.gm7.instaproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gm7.instaproject.Model.PostModel;
import com.example.gm7.instaproject.Model.UserModel;
import com.example.gm7.instaproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by emad on 12/20/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.homeViewHolder>{

    private Context mContext;
    private List<PostModel> posts;
    public HomeAdapter(List<PostModel> posts) {
        this.posts = posts;
    }

    @Override
    public homeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_home_row,parent,false);
        return new homeViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(homeViewHolder holder, int position) {

        getEmailFromUid(posts.get(position).uid,holder);
        Glide.with(mContext)
                .load(posts.get(position).mainImageUrl)
                .into(holder.mThumbnails);
        holder.mProjectDescription.setText(posts.get(position).description);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class homeViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mAuthorImage;
        private TextView mAuthorName;
        private ImageView mThumbnails;
        private TextView mProjectDescription;

        public homeViewHolder(View itemView) {
            super(itemView);
            mAuthorImage = itemView.findViewById(R.id.authorImage);
            mAuthorName = itemView.findViewById(R.id.author_name);
            mThumbnails = itemView.findViewById(R.id.thumbnails);
            mProjectDescription = itemView.findViewById(R.id.project_description);
        }
    }

    private void getEmailFromUid(String uid , final homeViewHolder holder){
        final String email="";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        Query query = reference.child("users").orderByChild("uid").equalTo(uid);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GenericTypeIndicator<UserModel> model = new GenericTypeIndicator<UserModel>(){};
                UserModel userModel = dataSnapshot.getValue(model);
                if (userModel != null && userModel.username.equals("")) {
                    holder.mAuthorName.setText(userModel.email);
                } else {
                    holder.mAuthorName.setText(userModel.username);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
