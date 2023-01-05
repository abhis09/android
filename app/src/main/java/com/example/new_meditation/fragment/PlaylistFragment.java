package com.example.new_meditation.fragment;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.new_meditation.Adapter.PlaylistAdapter;
import com.example.new_meditation.Database.DatabaseHandler;
import com.example.new_meditation.Model.Downloads;
import com.example.new_meditation.R;
import com.example.new_meditation.activity.MainActivity;

import java.util.ArrayList;

public class PlaylistFragment extends Fragment {
    static FragmentTransaction fragmentTransaction;
    ImageView bg_profile;
    PlaylistAdapter adapter;
    Context con;
    SQLiteDatabase db;
    DatabaseHandler dbh;
    ArrayList<Downloads> myList;
    RecyclerView rv;
    TextView txtnofav;
    Typeface typeFace;
    View view;
    private GridLayoutManager gridLayout;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.playlist_fragment, viewGroup, false);
        if (getActivity() != null) {
            try {
                this.typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Amita" +
                        "-Regular.ttf");
                MainActivity.toolbarText.setText("Playlist");
                MainActivity.toolbarText.setTypeface(this.typeFace);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.con = context;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        try {
            this.myList = new ArrayList<>();
            this.dbh = new DatabaseHandler(getActivity());
            this.db = this.dbh.getReadableDatabase();
            this.rv = (RecyclerView) this.view.findViewById(R.id.playlistrv);
            this.gridLayout = new GridLayoutManager(this.con, 1);
            this.rv.setHasFixedSize(true);
            this.rv.setLayoutManager(this.gridLayout);
            this.txtnofav = (TextView) this.view.findViewById(R.id.txtnofav);
            bg_profile = view.findViewById(R.id.bg_profile);
            this.rv.setItemAnimator(new DefaultItemAnimator());
            this.rv.setHasFixedSize(true);
            this.myList = this.dbh.loadplaylist();
            this.adapter = new PlaylistAdapter(getActivity(), this.myList);
            this.rv.setAdapter(this.adapter);
            Glide.with(getActivity()).load(myList.get(0).getImageurl()).into(bg_profile);
            Log.e("SHIVA", "onActivityCreated: "+myList.get(0).getImageurl());
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        try {
            this.myList.clear();
            this.myList.addAll(this.dbh.loadplaylist());
            this.adapter = new PlaylistAdapter(getActivity(), this.myList);
            this.rv.setAdapter(this.adapter);
            this.adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public void showChangeLangDialog() {
        final Dialog dialog = new Dialog(this.con);
        dialog.setContentView(R.layout.dialog_playlist);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCancelable(false);
        TextView textView = (TextView) dialog.findViewById(R.id.title);
        final EditText editText = (EditText) dialog.findViewById(R.id.edit1);
        ((TextView) dialog.findViewById(R.id.txttitle)).setText("Please input Playlist " +
                "name\t\t\t\t\t\t\t");
        ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    String obj = editText.getText().toString();
                    if (!obj.isEmpty() && obj.trim().length() != 0 && !obj.equals("")) {
                        if (obj != null) {
                            if (PlaylistFragment.this.dbh.addplaylist(obj) != 0) {
                                PlaylistFragment.this.myList.add(PlaylistFragment.this.myList.size(), new Downloads(obj, "", "", 0, PlaylistFragment.this.dbh.getpid(obj), 0));
                                PlaylistFragment.this.adapter.notifyDataSetChanged();
                                Toast.makeText(PlaylistFragment.this.con, "Added to playlist", 0).show();
                            }
                            if (dialog == null) {
                                dialog.dismiss();
                                return;
                            }
                            dialog.dismiss();
                            return;
                        }
                    }
                    Toast.makeText(PlaylistFragment.this.con, "Enter playlist name", 0).show();
                    if (dialog == null) {
                    }
                } catch (Throwable e) {
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
        dialog.show();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();
    }
}
