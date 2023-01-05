package com.example.new_meditation.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.new_meditation.Database.DatabaseHandler;
import com.example.new_meditation.Model.Downloads;
import com.example.new_meditation.R;

import java.util.ArrayList;

public class SoftsongAdapter extends RecyclerView.Adapter<SoftsongAdapter.ViewHolder> {
    Activity context;
    SQLiteDatabase db;
    DatabaseHandler dbh;
    public ArrayList<Integer> selected_usersList = new ArrayList<>();
    ArrayList<Downloads> str;

    public SoftsongAdapter(Activity activity, ArrayList<Downloads> arrayList, ArrayList<Integer> arrayList2) {
        this.context = activity;
        this.str = arrayList;
        this.selected_usersList = arrayList2;
        this.dbh = new DatabaseHandler(activity);
        this.db = this.dbh.getReadableDatabase();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.playlists_item, viewGroup, false));
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            if (this.selected_usersList.contains(Integer.valueOf(i))) {
                viewHolder.check.setVisibility(0);
                viewHolder.deleteView.setVisibility(0);
            } else {
                viewHolder.check.setVisibility(8);
                viewHolder.deleteView.setVisibility(8);
            }
            viewHolder.tv.setText(this.str.get(i).getSname());
            try {
                Glide.with(this.context).load(this.str.get(i).getImageurl()).fitCenter().centerCrop().error((int) R.drawable.banner_default).into(viewHolder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(this.context).load(this.str.get(i).getImageurl()).fitCenter().centerCrop().error((int) R.drawable.banner_default).into(viewHolder.imageView);
                viewHolder.tv.setText(this.str.get(i).getSname());
                if (this.dbh.getstatus(this.str.get(i).getDname()) == 0) {
                    viewHolder.btnfav.setVisibility(0);
                    viewHolder.btnfavfull.setVisibility(8);
                    return;
                }
                viewHolder.btnfav.setVisibility(8);
                viewHolder.btnfavfull.setVisibility(0);
            } catch (Exception e2) {
                e2.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void remove(int i) {
        try {
            this.dbh.deletePlaylist(this.str.get(i).getDid(), this.str.get(i).getPlaylistid());
            this.str.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, this.str.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.str.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnfav;
        ImageView btnfavfull;
        ImageView check;
        RelativeLayout deleteView;
        ImageView imageView;
        TextView tv;

        public ViewHolder(View view) {
            super(view);
            this.btnfav = (ImageView) view.findViewById(R.id.dwn_btnfav);
            this.btnfavfull = (ImageView) view.findViewById(R.id.dwn_btnfavfull);
            this.check = (ImageView) view.findViewById(R.id.check);
            this.deleteView = (RelativeLayout) view.findViewById(R.id.deleteView);
            this.imageView = (ImageView) view.findViewById(R.id.softsong_item_imageview);
            this.tv = (TextView) view.findViewById(R.id.songTitle);
        }
    }
}
