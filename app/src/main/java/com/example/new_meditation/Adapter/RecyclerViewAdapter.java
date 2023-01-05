package com.example.new_meditation.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.new_meditation.Database.DatabaseHandler;
import com.example.new_meditation.Model.Downloads;
import com.example.new_meditation.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("WrongConstant")
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SimpleViewHolder> {
    public SQLiteDatabase db;
    public DatabaseHandler dbh;
    File file;
    public Context mContext;
    public ArrayList<Downloads> mDataset;
    private onDownloadItemclick mListner;
    public ArrayList<Integer> selected_usersList = new ArrayList<>();
    String targetPath;

    public interface onDownloadItemclick {
        void addFevSongClick(SimpleViewHolder simpleViewHolder, int i);

        void playDownloadSongClick(int i);

        void removeDownloadSongClick(int i);

        void removeFevSongClick(SimpleViewHolder simpleViewHolder, int i);
    }

    public int getItemViewType(int i) {
        return i;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        ImageView btnfav;
        ImageView btnfavfull;
        Button buttonDelete;
        ImageView check;
        RelativeLayout deleteView;
        TextView download_item_Play;
        TextView download_item_remove;
        ImageView grid_item_image;
        
        public onDownloadItemclick mlistner;
        RelativeLayout select_item;
        TextView textViewPos;

        public SimpleViewHolder(View view, onDownloadItemclick ondownloaditemclick) {
            super(view);
            this.mlistner = ondownloaditemclick;
            this.btnfav =  view.findViewById(R.id.dwn_btnfav);
            this.btnfavfull =  view.findViewById(R.id.dwn_btnfavfull);
            this.textViewPos = (TextView) view.findViewById(R.id.txtdownload);
            this.download_item_Play = (TextView) view.findViewById(R.id.download_item_Play);
            this.download_item_remove = (TextView) view.findViewById(R.id.download_item_remove);
            this.select_item = (RelativeLayout) view.findViewById(R.id.select_item);
            this.grid_item_image = (ImageView) view.findViewById(R.id.grid_item_image);
            this.check = (ImageView) view.findViewById(R.id.check);
            this.deleteView = (RelativeLayout) view.findViewById(R.id.deleteView);
            this.btnfav.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (RecyclerViewAdapter.this.dbh.updateRecord(RecyclerViewAdapter.this.mDataset.get(SimpleViewHolder.this.getAdapterPosition()).getDname(), 1) != 0) {
                            SimpleViewHolder.this.btnfav.setVisibility(8);
                            SimpleViewHolder.this.btnfavfull.setVisibility(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
            this.btnfavfull.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    try {
                        if (RecyclerViewAdapter.this.dbh.updateRecord(RecyclerViewAdapter.this.mDataset.get(SimpleViewHolder.this.getAdapterPosition()).getDname(), 0) != 0) {
                            SimpleViewHolder.this.btnfav.setVisibility(0);
                            SimpleViewHolder.this.btnfavfull.setVisibility(8);
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            });
            this.download_item_Play.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    SimpleViewHolder.this.mlistner.playDownloadSongClick(SimpleViewHolder.this.getAdapterPosition());
                }
            });
            this.download_item_remove.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    SimpleViewHolder.this.mlistner.removeDownloadSongClick(SimpleViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    public RecyclerViewAdapter(Context context, ArrayList<Downloads> arrayList, ArrayList<Integer> arrayList2) {
        this.mContext = context;
        this.mDataset = arrayList;
        this.selected_usersList = arrayList2;
        this.dbh = new DatabaseHandler(this.mContext);
        this.db = this.dbh.getReadableDatabase();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new SimpleViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.download_item, viewGroup, false), this.mListner);
    }

    public void setonDownloadItemclick(onDownloadItemclick ondownloaditemclick) {
        this.mListner = ondownloaditemclick;
    }


    public void onBindViewHolder(SimpleViewHolder simpleViewHolder, int i) {
        try {
            if (this.selected_usersList.contains(Integer.valueOf(i))) {
                simpleViewHolder.check.setVisibility(0);
                simpleViewHolder.deleteView.setVisibility(0);
            } else {
                simpleViewHolder.check.setVisibility(8);
                simpleViewHolder.deleteView.setVisibility(8);
            }
            try {
                Glide.with(this.mContext).load(this.mDataset.get(i).getImageurl()).fitCenter().centerCrop().error((int) R.drawable.banner_default).into(simpleViewHolder.grid_item_image);
                simpleViewHolder.textViewPos.setText(this.mDataset.get(i).getSname());
                if (this.dbh.getstatus(this.mDataset.get(i).getDname()) == 0) {
                    simpleViewHolder.btnfav.setVisibility(0);
                    simpleViewHolder.btnfavfull.setVisibility(8);
                    return;
                }
                simpleViewHolder.btnfav.setVisibility(8);
                simpleViewHolder.btnfavfull.setVisibility(0);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.mDataset.size();
    }

    private static class ViewHolder {
        TextView tvSname;

        private ViewHolder() {
        }
    }

    public static class MyAdapter extends ArrayAdapter<Downloads> {
        LayoutInflater inflater;
        Context myContext;
        List<Downloads> newList;

        public MyAdapter(Context context, int i, ArrayList<Downloads> arrayList) {
            super(context, i, arrayList);
            this.myContext = context;
            this.newList = arrayList;
            this.inflater = LayoutInflater.from(context);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            View inflate;
            if (view == null) {
                try {
                    viewHolder = new ViewHolder();
                    inflate = this.inflater.inflate(R.layout.listview_item, (ViewGroup) null);
                } catch (Exception e) {
                    e = e;
                    e.printStackTrace();
                    return view;
                }
                try {
                    viewHolder.tvSname = (TextView) inflate.findViewById(R.id.tvtext_item);
                    viewHolder.tvSname.setTextColor(i == this.newList.size() + -1 ? -7829368 : -12303292);
                    inflate.setTag(viewHolder);
                    view = inflate;
                } catch (Exception e2) {
                    view = inflate;
                    e2.printStackTrace();
                    return view;
                }
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tvSname.setText(this.newList.get(i).getDname());
            return view;
        }
    }

    public void remove(int i) {
        File externalFilesDir = this.mContext.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file2 = new File(externalFilesDir.getAbsolutePath() + "/sleep_music");
        try {
            if (new File(file2 + "/" + this.mDataset.get(i).getDname()).delete()) {
                this.dbh.delete(this.mDataset.get(i).getDname());
                this.mDataset.remove(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addplaylist(int i, int i2) throws Throwable {
        try {
            this.dbh.savePlaylist(i, this.mDataset.get(i2).getDid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
