package com.example.new_meditation.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.new_meditation.Database.DatabaseHandler;
import com.example.new_meditation.Model.Downloads;
import com.example.new_meditation.R;

import java.util.ArrayList;

@SuppressLint("WrongConstant")
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {
    Context context;
    SQLiteDatabase db;
    DatabaseHandler dbh;
    private onFavClickListner mListner;
    public ArrayList<Integer> selected_usersList = new ArrayList<>();
    ArrayList<Downloads> str;

    public interface onFavClickListner {
        void playFavSongClick(int i);
    }

    public FavouriteAdapter(FragmentActivity fragmentActivity, ArrayList<Downloads> arrayList, ArrayList<Integer> arrayList2) {
        this.context = fragmentActivity;
        this.str = arrayList;
        this.dbh = new DatabaseHandler(fragmentActivity);
        this.db = this.dbh.getReadableDatabase();
        this.selected_usersList = arrayList2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favouritelist_item, viewGroup, false), this.mListner);
    }

    public void setonFavClickListner(onFavClickListner onfavclicklistner) {
        this.mListner = onfavclicklistner;
    }


    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            if (this.selected_usersList.contains(Integer.valueOf(i))) {
                viewHolder.check.setVisibility(0);
                viewHolder.deleteView.setVisibility(0);
            } else {
                viewHolder.check.setVisibility(8);
                viewHolder.deleteView.setVisibility(8);
            }
            this.str.get(i).getDname();
            try {
                viewHolder.tv.setText(this.str.get(i).getSname());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(this.context).load(this.str.get(i).getImageurl()).fitCenter().centerCrop().error((int) R.drawable.banner_default).into(viewHolder.imageView);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void addplaylist(int i, int i2) {
        try {
            this.dbh.savePlaylist(i, this.str.get(i2).getDid());
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public int getItemCount() {
        return str == null ? 0 : str.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView addImg;
        ImageView check;
        RelativeLayout deleteView;
        TextView fav_item_Play;
        ImageView imageView;
        onFavClickListner mListner;
        RelativeLayout select_item;
        TextView tv;

        public ViewHolder(View view, onFavClickListner onfavclicklistner) {
            super(view);
            this.mListner = onfavclicklistner;
            this.imageView = (ImageView) view.findViewById(R.id.favsong_item_imageview);
            this.tv = (TextView) view.findViewById(R.id.favlist_item_text);
            this.fav_item_Play = (TextView) view.findViewById(R.id.fav_item_Play);
            this.check = (ImageView) view.findViewById(R.id.check);
            this.select_item = (RelativeLayout) view.findViewById(R.id.select_item);
            this.deleteView = (RelativeLayout) view.findViewById(R.id.deleteView);
            this.fav_item_Play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    ViewHolder.this.mListner.playFavSongClick(ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }
}
