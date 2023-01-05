package com.example.new_meditation.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.new_meditation.Database.DatabaseHandler;
import com.example.new_meditation.Model.Downloads;
import com.example.new_meditation.R;
import com.example.new_meditation.activity.AndroidBuildingMusicPlayerActivity;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    FragmentActivity context;
    SQLiteDatabase db;
    DatabaseHandler dbh;
    Typeface face;
    
    public ArrayList<Downloads> mDataset;
    ArrayList<String> myList = new ArrayList<>();
    ArrayList<Downloads> str;

    public PlaylistAdapter(FragmentActivity fragmentActivity, ArrayList<Downloads> arrayList) {
        this.context = fragmentActivity;
        this.str = arrayList;
        this.dbh = new DatabaseHandler(fragmentActivity);
        this.db = this.dbh.getReadableDatabase();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.playlist_item, viewGroup, false);
        this.face = Typeface.createFromAsset(this.context.getAssets(), "fonts/Pathway Gothic One Regular.ttf");
        return new ViewHolder(inflate);
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        try {
            final String dname = this.str.get(i).getDname();
            viewHolder.playlist_item_text.setText(this.str.get(i).getDname());
            this.mDataset = this.dbh.selectPlaylist(this.context, dname);
            viewHolder.relative.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                public void onClick(View view) {
                    try {
                        if (PlaylistAdapter.this.dbh.playlistchildcount(PlaylistAdapter.this.str.get(i).getPlaylistid()) == 0) {
                            Toast.makeText(PlaylistAdapter.this.context, "Playlist is empty", 0).show();
                        } else if (PlaylistAdapter.this.mDataset.size() > 0) {
                            try {
                                Intent intent = new Intent(PlaylistAdapter.this.context, AndroidBuildingMusicPlayerActivity.class);
                                intent.putExtra("item_selected_key", dname);
//                                intent.putExtra("imageKey",mDataset.get(i).getImageurl());
                                PlaylistAdapter.this.context.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
            viewHolder.play.setOnClickListener(v -> {
                try {
                    if (PlaylistAdapter.this.dbh.playlistchildcount(PlaylistAdapter.this.str.get(i).getPlaylistid()) == 0) {
                        Toast.makeText(PlaylistAdapter.this.context, "Playlist is empty", 0).show();
                    } else if (PlaylistAdapter.this.mDataset.size() > 0) {
                        try {
                            Intent intent = new Intent(PlaylistAdapter.this.context, AndroidBuildingMusicPlayerActivity.class);
                            intent.putExtra("item_selected_key", dname);
                            PlaylistAdapter.this.context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

            viewHolder.delete.setOnClickListener(v -> {
                final Dialog dialog = new Dialog(PlaylistAdapter.this.context);
                dialog.setContentView(R.layout.alert_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                dialog.setCancelable(false);
                Button button = (Button) dialog.findViewById(R.id.customDialogCancel);
                Button button2 = (Button) dialog.findViewById(R.id.customDialogOk);
                TextView textView = (TextView) dialog.findViewById(R.id.alert_msg);
                TextView textView2 = (TextView) dialog.findViewById(R.id.alert_title);
                button.setTypeface(PlaylistAdapter.this.face);
                button2.setTypeface(PlaylistAdapter.this.face);
                textView2.setTypeface(PlaylistAdapter.this.face);
                textView.setText("Are You Sure to Delete This Playlist ?");
                textView2.setText(PlaylistAdapter.this.context.getResources().getString(R.string.app_name));
                textView.setTypeface(PlaylistAdapter.this.face);
                button2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        try {
                            dialog.dismiss();
                            if (PlaylistAdapter.this.str.size() > 0) {
                                PlaylistAdapter.this.dbh.delplaylist(PlaylistAdapter.this.str.get(i).getPlaylistid());
                                PlaylistAdapter.this.str.remove(i);
                                PlaylistAdapter.this.notifyItemRemoved(i);
                                PlaylistAdapter.this.notifyItemRangeChanged(i, PlaylistAdapter.this.str.size());
                                PlaylistAdapter.this.notifyDataSetChanged();
                            }
                            PlaylistAdapter.this.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                try {
                    dialog.show();
                } catch (Exception unused) {
                }
            });
            viewHolder.edit.setOnClickListener(v -> {
                if (PlaylistAdapter.this.str.size() > 0) {
                    PlaylistAdapter.this.showChangeLangDialog(PlaylistAdapter.this.str.get(i).getPlaylistid(), i);
                    PlaylistAdapter.this.notifyItemChanged(i);
                    PlaylistAdapter.this.notifyDataSetChanged();
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void showChangeLangDialog(int i, int i2) {
        final Dialog dialog = new Dialog(this.context);
        dialog.setContentView(R.layout.dialog_playlist);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        TextView textView = (TextView) dialog.findViewById(R.id.title);
        final EditText editText = (EditText) dialog.findViewById(R.id.edit1);
        ((TextView) dialog.findViewById(R.id.txttitle)).setText("Please input Playlist name\t\t\t\t\t\t\t");
        final int i3 = i;
        final int i4 = i2;
        final Dialog dialog2 = dialog;
        ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            /* JADX WARNING: Removed duplicated region for block: B:13:0x0071 A[Catch:{ Exception -> 0x0077 }] */
            /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                try {
                    String obj = editText.getText().toString();
                    if (!obj.isEmpty() && obj.trim().length() != 0 && !obj.equals("")) {
                        if (obj != null) {
                            PlaylistAdapter.this.dbh.updatepname(i3, obj);
                            PlaylistAdapter.this.str.set(i4, new Downloads(obj, "", "", 0, i3, 0));
                            PlaylistAdapter.this.notifyItemChanged(i4);
                            PlaylistAdapter.this.notifyDataSetChanged();
                            Toast.makeText(PlaylistAdapter.this.context, "Playlist Renamed", 0).show();
                            if (dialog2 == null) {
                                dialog2.dismiss();
                                return;
                            }
                            dialog2.dismiss();
                            return;
                        }
                    }
                    Toast.makeText(PlaylistAdapter.this.context, "Enter playlist name", 0).show();
                    if (dialog2 == null) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ((Button) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Rect locateView(View view) {
        int[] iArr = new int[2];
        if (view == null) {
            return null;
        }
        try {
            view.getLocationOnScreen(iArr);
            Rect rect = new Rect();
            rect.left = iArr[0];
            rect.top = iArr[1];
            rect.right = rect.left + view.getWidth();
            rect.bottom = rect.top + view.getHeight();
            return rect;
        } catch (NullPointerException unused) {
            return null;
        }
    }

    public int getItemCount() {
        return str == null ? 0 : str.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView  play, delete, edit;
        RelativeLayout relative;
        TextView playlist_item_text;

        public ViewHolder(View view) {
            super(view);
            play = view.findViewById(R.id.play);
            delete = view.findViewById(R.id.delete);
            edit = view.findViewById(R.id.edit);
            playlist_item_text = (TextView) view.findViewById(R.id.playlist_item_text);
            this.relative = (RelativeLayout) view.findViewById(R.id.relative_playlist_item);
        }
    }
}
