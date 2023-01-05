package com.example.new_meditation.fragment;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.new_meditation.Adapter.FavouriteAdapter;
import com.example.new_meditation.Adapter.RecyclerItemClickListener;
import com.example.new_meditation.Adapter.RecyclerViewAdapter;
import com.example.new_meditation.Database.DatabaseHandler;
import com.example.new_meditation.Model.Downloads;
import com.example.new_meditation.R;
import com.example.new_meditation.activity.FavouriteMusicPlayerActivity;
import com.example.new_meditation.activity.MainActivity;

import java.util.ArrayList;

@SuppressLint("WrongConstant")
public class FavouriteFragment extends Fragment {
    public static ActionMode mActionMode;
    FavouriteAdapter adapter;
    Context con;
    Menu context_menu;
    SQLiteDatabase db;
    DatabaseHandler dbh;
    int flag = 0;
    private GridLayoutManager gridLayout;
    boolean isMultiSelect = false;
    
    public ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.menu_fav, menu);
            FavouriteFragment.this.context_menu = menu;
            return true;
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.action_add_playlist) {
                FavouriteFragment.this.addPlaylist();
                return true;
            } else if (itemId == R.id.action_cancle) {
                actionMode.finish();
                return true;
            } else if (itemId != R.id.action_selectall) {
                return false;
            } else {
                FavouriteFragment.this.SelectAll();
                return true;
            }
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            FavouriteFragment.this.adapter.notifyDataSetChanged();
            FavouriteFragment.mActionMode = null;
            FavouriteFragment.this.isMultiSelect = false;
            FavouriteFragment.this.multiselect_list = new ArrayList<>();
            FavouriteFragment.this.flag = 0;
            FavouriteFragment.this.refreshAdapter();
        }
    };
    ArrayList<Integer> multiselect_list = new ArrayList<>();
    ArrayList<Downloads> myList;
    RelativeLayout relativeLayout;
    RecyclerView rv;
    TextView txtnofav;
    Typeface typeFace;
    View view;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.favourite_fragment, viewGroup, false);
        try {
            this.typeFace = Typeface.createFromAsset(this.con.getAssets(), "fonts/Pathway Gothic One Regular.ttf");
            MainActivity.toolbarText.setText("Favourites");
            MainActivity.toolbarText.setTypeface(this.typeFace);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.view;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        try {
            this.myList = new ArrayList<>();
            this.dbh = new DatabaseHandler(getActivity());
            this.db = this.dbh.getReadableDatabase();
            this.rv = (RecyclerView) this.view.findViewById(R.id.favouriteListrv);
            this.rv.setLayoutManager(new LinearLayoutManager(this.con));
            this.txtnofav = (TextView) this.view.findViewById(R.id.txtnofav);
            this.rv.setItemAnimator(new DefaultItemAnimator());
            this.rv.setHasFixedSize(true);
            this.myList = this.dbh.loadfav();
            this.adapter = new FavouriteAdapter(getActivity(), this.myList, this.multiselect_list);
            this.rv.setAdapter(this.adapter);
            if (this.myList.isEmpty()) {
                this.rv.setVisibility(8);
                this.txtnofav.setVisibility(0);
            } else {
                this.txtnofav.setVisibility(8);
                this.rv.setVisibility(0);
            }
            this.adapter.setonFavClickListner(new FavouriteAdapter.onFavClickListner() {
                public void playFavSongClick(int i) {
                    String dname = FavouriteFragment.this.myList.get(i).getDname();
                    Intent intent = new Intent(FavouriteFragment.this.getActivity(), FavouriteMusicPlayerActivity.class);
                    intent.putExtra("item_selected_key", i);
                    intent.putExtra("image_key",myList.get(i).getImageurl());
                    intent.putExtra(DatabaseHandler.KEY_OLD_SONG, dname);
                    intent.putExtra(DatabaseHandler.KEY_NEW_SONG, FavouriteFragment.this.myList.get(i).getSname());
                    FavouriteFragment.this.getActivity().startActivity(intent);
                }
            });
            this.rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), this.rv, new RecyclerItemClickListener.OnItemClickListener() {
                public void onItemClick(View view, int i) {
                    try {
                        if (FavouriteFragment.this.flag == 1 && FavouriteFragment.this.isMultiSelect) {
                            FavouriteFragment.this.multi_select(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void onItemLongClick(View view, int i) {
                    try {
                        FavouriteFragment.this.flag = 1;
                        if (!FavouriteFragment.this.isMultiSelect) {
                            FavouriteFragment.this.multiselect_list = new ArrayList<>();
                            FavouriteFragment.this.isMultiSelect = true;
                            if (FavouriteFragment.mActionMode == null) {
                                FavouriteFragment.mActionMode = FavouriteFragment.this.getActivity().startActionMode(FavouriteFragment.this.mActionModeCallback);
                            }
                        }
                        FavouriteFragment.this.multi_select(i);
                    } catch (Exception unused) {
                    }
                }
            }));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void onAttach(Context context) {
        try {
            this.con = context;
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onAttach(context);
    }

    public void multi_select(int i) {
        if (mActionMode != null) {
            if (this.multiselect_list.contains(Integer.valueOf(i))) {
                this.multiselect_list.remove(this.multiselect_list.indexOf(Integer.valueOf(i)));
            } else {
                this.multiselect_list.add(Integer.valueOf(i));
            }
            if (this.multiselect_list.size() > 0) {
                ActionMode actionMode = mActionMode;
                actionMode.setTitle(this.multiselect_list.size() + " Selected");
            } else {
                mActionMode.finish();
            }
            refreshAdapter();
        }
    }

    public void refreshAdapter() {
        this.adapter.selected_usersList = this.multiselect_list;
        this.adapter.notifyDataSetChanged();
    }

    
    public void SelectAll() {
        this.multiselect_list.clear();
        for (int i = 0; i < this.adapter.getItemCount(); i++) {
            if (mActionMode != null && !this.multiselect_list.contains(Integer.valueOf(i))) {
                this.multiselect_list.add(Integer.valueOf(i));
            }
        }
        if (this.multiselect_list.size() > 0) {
            ActionMode actionMode = mActionMode;
            actionMode.setTitle(this.multiselect_list.size() + " Selected");
        } else {
            mActionMode.finish();
        }
        refreshAdapter();
    }

    public void addPlaylist() {
        try {
            new ArrayList();
            final ArrayList<Downloads> loadplaylist = this.dbh.loadplaylist();
            loadplaylist.add(loadplaylist.size(), new Downloads("Create Playlist", "", "", 0, 0, 0));
            if (loadplaylist.size() > 0) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.con);
                    LayoutInflater layoutInflater = (LayoutInflater) this.con.getSystemService("layout_inflater");
                    View inflate = layoutInflater.inflate(R.layout.listview, (ViewGroup) null);
                    if (inflate == null) {
                        inflate = layoutInflater.inflate(R.layout.listview, (ViewGroup) null);
                    }
                    builder.setView(inflate);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    ListView listView = (ListView) inflate.findViewById(R.id.mylistview);
                    final AlertDialog create = builder.create();
                    listView.setAdapter(new RecyclerViewAdapter.MyAdapter(this.con, R.layout.listview_item, loadplaylist));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                            if (((Downloads) loadplaylist.get(i)).getDname().equals("Create Playlist")) {
                                FavouriteFragment.this.showChangeLangDialog();
                            } else {
                                int i2 = 0;
                                while (i2 < FavouriteFragment.this.multiselect_list.size()) {
                                    try {
                                        ((Downloads) loadplaylist.get(i)).getDname();
                                        FavouriteFragment.this.dbh.getplaylistid();
                                        FavouriteFragment.this.adapter.addplaylist(((Downloads) loadplaylist.get(i)).getPlaylistid(), FavouriteFragment.this.multiselect_list.get(i2).intValue());
                                        FavouriteFragment.this.adapter.notifyDataSetChanged();
                                        Toast.makeText(FavouriteFragment.this.con, "Added to Playlist", 0).show();
                                        i2++;
                                    } catch (Throwable e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (FavouriteFragment.mActionMode != null) {
                                    FavouriteFragment.mActionMode.finish();
                                }
                            }
                            create.cancel();
                        }
                    });
                    create.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this.con, "Create Playlist first", 0).show();
            }
        } catch (Throwable e2) {
            e2.printStackTrace();

        }
    }

    public void showChangeLangDialog() {
        try {
            final Dialog dialog = new Dialog(this.con);
            dialog.setContentView(R.layout.dialog_playlist);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.setCancelable(false);
            final EditText editText = (EditText) dialog.findViewById(R.id.edit1);
            ((TextView) dialog.findViewById(R.id.txttitle)).setText("Please input Playlist name\t\t\t\t\t\t\t");
            ((Button) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
                /* JADX WARNING: Removed duplicated region for block: B:16:0x0076 A[Catch:{ Exception -> 0x0085 }] */
                /* JADX WARNING: Removed duplicated region for block: B:19:0x007f A[Catch:{ Exception -> 0x0085 }] */
                /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */

                public void onClick(View view) {
                    try {
                        String obj = editText.getText().toString();
                        if (!obj.isEmpty() && obj.trim().length() != 0 && !obj.equals("")) {
                            if (obj != null) {
                                FavouriteFragment.this.dbh.addplaylist(obj);
                                for (int i = 0; i < FavouriteFragment.this.multiselect_list.size(); i++) {
                                    FavouriteFragment.this.adapter.addplaylist(FavouriteFragment.this.dbh.getplaylistid(), FavouriteFragment.this.multiselect_list.get(i).intValue());
                                    Toast.makeText(FavouriteFragment.this.con, "Added to Playlist", 0).show();
                                }
                                if (FavouriteFragment.mActionMode != null) {
                                    FavouriteFragment.mActionMode.finish();
                                }
                                if (dialog == null) {
                                    dialog.dismiss();
                                    return;
                                }
                                return;
                            }
                        }
                        Toast.makeText(FavouriteFragment.this.con, "Enter playlist name", 0).show();
                        if (FavouriteFragment.mActionMode != null) {
                        }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (mActionMode != null) {
                mActionMode.finish();
                mActionMode = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        try {
            if (mActionMode != null) {
                mActionMode.finish();
                mActionMode = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
