package com.example.new_meditation.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.new_meditation.MyReceiver;
import com.example.new_meditation.R;
import com.example.new_meditation.Utils;
import com.example.new_meditation.fragment.FavouriteFragment;
import com.example.new_meditation.fragment.HomeFragment;
import com.example.new_meditation.fragment.PlaylistFragment;

import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint("WrongConstant")
public class MainActivity extends AppCompatActivity {

    RelativeLayout fvrt_rl, share_rl, rate_rl, privacy_rl;
    public static int downlodItemPosition = 0;
    public static DrawerLayout drawer = null;
    public static Fragment fragment1 = null;
    public static String itemid = "0";
    public static int navItemIndex = 0;
    public static String pgid = null;
    public static RelativeLayout relProgress = null;
    public static Toolbar toolbar;
    public static TextView toolbarText;
    static FragmentTransaction fragmentTransaction = null;
    public SmoothActionBarDrawerToggle mDrawerToggle;
    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission" + ".WRITE_EXTERNAL_STORAGE"};
    Context context = this;
    Typeface face;
    AlertDialog mMyDialog;
    MainActivity mainActivity;
    ArrayList<String> navigation_items;
    String pingMsg = "";
    String versionInfo;
    private SharedPreferences appSettings;
    private DrawerLayout mDrawerLayout;

    private static boolean hasPermissions(Context context2, String... strArr) {
        if (Build.VERSION.SDK_INT < 23 || context2 == null || strArr == null) {
            return true;
        }
        for (String checkSelfPermission : strArr) {
            if (ActivityCompat.checkSelfPermission(context2, checkSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }


    public void onDestroy() {
        super.onDestroy();
    }


    public void onPause() {
        super.onPause();
    }


    public void onStop() {
        super.onStop();
    }


    @SuppressLint({"ResourceType"})
    public void onCreate(Bundle bundle) {
        PackageInfo packageInfo;
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        fvrt_rl = findViewById(R.id.rl5);
        share_rl = findViewById(R.id.rl2);
        rate_rl = findViewById(R.id.rl3);
        privacy_rl = findViewById(R.id.rl4);

        fvrt_rl.setOnClickListener(v -> {
            fragment1 = new FavouriteFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(fragment1.getClass().getName());
            fragmentTransaction.replace(R.id.frame_container, fragment1);
            fragmentTransaction.commit();
            this.mDrawerLayout.closeDrawers();
        });

        share_rl.setOnClickListener(v -> {
            share();
        });

        rate_rl.setOnClickListener(v -> {

        });
        privacy_rl.setOnClickListener(v -> {
        });

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        }
        this.navigation_items = new ArrayList<>();
        this.navigation_items.add("Tools");
        this.navigation_items.add("Home");
        this.navigation_items.add("Playlist");
        this.navigation_items.add("Downloads");
        this.navigation_items.add("Favourites");
        this.navigation_items.add("Reminder");
        this.navigation_items.add("Others");
        this.navigation_items.add("Settings");
        this.navigation_items.add("More Apps");
        this.navigation_items.add("Rate App");
        this.navigation_items.add("Share");
        this.navigation_items.add("Privacy Policy");
        this.navigation_items.add("Terms Of Service");
        {
            this.navigation_items.remove("Settings");
        }
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.relative_layout);
        setStaticTimeNotification();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle((CharSequence) "");
        toolbarText = (TextView) findViewById(R.id.toolbarText);
        toolbarText.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        this.face = Typeface.createFromAsset(getAssets(), "fonts/Pathway Gothic One Regular.ttf");
        toolbarText.setTypeface(this.face);
        this.appSettings = getSharedPreferences("APP_NAME", 0);
        drawer = (DrawerLayout) findViewById(R.id.relative_layout);
        this.mDrawerToggle = new SmoothActionBarDrawerToggle(this, this.mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        this.mDrawerLayout.setDrawerListener((DrawerLayout.DrawerListener) this.mDrawerToggle);
        toolbar.setEnabled(true);
        toolbar.showContextMenu();
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbarText.setTextColor(getResources().getColor(R.color.black));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.mDrawerToggle.setDrawerIndicatorEnabled(true);
        this.mDrawerToggle.setToolbarNavigationClickListener((View.OnClickListener) null);
        this.mDrawerToggle.syncState();
        if (Build.VERSION.SDK_INT < 23) {
            displaySelectedScreen("Home");
        } else if (!hasPermissions(this.context, this.PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) this.context, this.PERMISSIONS, 112);
        } else {
            displaySelectedScreen("Home");
        }
        this.mainActivity = this;
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });
                    return;
                }
//                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                toolbar.setNavigationIcon(R.drawable.menu);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                mDrawerToggle.setToolbarNavigationClickListener((View.OnClickListener) null);
                mDrawerToggle.syncState();
                MainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        MainActivity.drawer.openDrawer((int) GravityCompat.START);
                    }
                });
            }
        });
        if (bundle == null) {
            navItemIndex = 0;
            Utils.CURRENT_TAG = Utils.TAG_HOME;
        }
        try {
            packageInfo = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            packageInfo = null;
        }
        this.versionInfo = packageInfo.versionName;
        this.appSettings.getBoolean("shortcut", false);
    }

    public void share() {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", "Sleep Music - Get instant sleep after " + "listening Sleep Music Meditation & Relax Melodies\n★ High quality soothing " + "sounds\n★ Smart, simple and beautiful design\n★ Smart timer and app turns " + "off automatically\n★ Better uninterrupted your deep sleep and " + "relax\n\nhttps://play.google.com/store/apps/details?id=" + getPackageName());
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            Log.d("", e.toString());
        }
    }

    private void setStaticTimeNotification() {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Calendar instance = Calendar.getInstance();
            instance.set(11, 6);
            instance.set(12, 0);
            instance.set(13, 0);
            if (currentTimeMillis <= instance.getTimeInMillis()) {
                ((AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM)).setRepeating(0, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(this, 0, new Intent(this, MyReceiver.class), PendingIntent.FLAG_MUTABLE));
                return;
            }
            instance.add(5, 1);
            ((AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM)).setRepeating(0, instance.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(this, 1, new Intent(this, MyReceiver.class), PendingIntent.FLAG_MUTABLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"ResourceType"})
    public boolean onCreateOptionsMenu(Menu menu2) {
        getMenuInflater().inflate(R.menu.menu_home, menu2);
        return super.onCreateOptionsMenu(menu2);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != R.id.action_playlist) {
            return super.onOptionsItemSelected(menuItem);
        }
        try {
            fragment1 = new PlaylistFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(fragment1.getClass().getName());
            fragmentTransaction.replace(R.id.frame_container, fragment1);
            fragmentTransaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public void clearStack() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            for (int i = 0; i < backStackEntryCount; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            for (int i2 = 0; i2 < getSupportFragmentManager().getFragments().size(); i2++) {
                Fragment fragment = getSupportFragmentManager().getFragments().get(i2);
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        }
    }


    public void displaySelectedScreen(String str) {
        char c = 65535;
        try {
            switch (str.hashCode()) {
                case -1715449590:
                    if (str.equals("Favourites")) {
                        c = 4;
                        break;
                    }
                    break;
                case -1069410038:
                    if (str.equals("Privacy Policy")) {
                        c = 10;
                        break;
                    }
                    break;
                case -978294581:
                    if (str.equals("Downloads")) {
                        c = 3;
                        break;
                    }
                    break;
                case -537272227:
                    if (str.equals("More Apps")) {
                        c = 7;
                        break;
                    }
                    break;
                case -453958510:
                    if (str.equals("Reminder")) {
                        c = 5;
                        break;
                    }
                    break;
                case -152107579:
                    if (str.equals("Terms Of Service")) {
                        c = 11;
                        break;
                    }
                    break;
                case 0:
                    if (str.equals("")) {
                        c = 0;
                        break;
                    }
                    break;
                case 2255103:
                    if (str.equals("Home")) {
                        c = 1;
                        break;
                    }
                    break;
                case 79847359:
                    if (str.equals("Share")) {
                        c = 9;
                        break;
                    }
                    break;
                case 485347041:
                    if (str.equals("Rate App")) {
                        c = 8;
                        break;
                    }
                    break;
                case 1499275331:
                    if (str.equals("Settings")) {
                        c = 6;
                        break;
                    }
                    break;
                case 1944118770:
                    if (str.equals("Playlist")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    break;
                case 1:
//                    navItemIndex = 0;
//                    Utils.CURRENT_TAG = Utils.TAG_HOME;
//                    clearStack();
//                    fragment1 = new HomeFragment();
//                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.frame_container, fragment1);
//                    fragmentTransaction.commit();
//                    this.mDrawerLayout.closeDrawers();
                    navItemIndex = 0;
                    Utils.CURRENT_TAG = Utils.TAG_HOME;
                    clearStack();
                    Intent intent = new Intent(MainActivity.this,MeditationMusicActivity.class);
                    startActivity(intent);
                    this.mDrawerLayout.closeDrawers();
                    break;
                case 2:
                    fragment1 = new PlaylistFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(fragment1.getClass().getName());
                    fragmentTransaction.replace(R.id.frame_container, fragment1);
                    fragmentTransaction.commit();
                    this.mDrawerLayout.closeDrawers();
                    break;
                case 3:
                    fragment1 = new FavouriteFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.addToBackStack(fragment1.getClass().getName());
                    fragmentTransaction.replace(R.id.frame_container, fragment1);
                    fragmentTransaction.commit();
                    this.mDrawerLayout.closeDrawers();
                    break;
                case 4:
                    try {
                        navItemIndex = 0;
                        Utils.CURRENT_TAG = Utils.TAG_HOME;
//                        startActivity(new Intent(getApplicationContext(), AlarmMe.class));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    this.mDrawerLayout.closeDrawers();
                    break;
                case 5:
                    startActivity(new Intent(this, SettingActivity.class));
                    break;
                case 6:
                    share();
                    break;
            }
            ((DrawerLayout) findViewById(R.id.relative_layout)).closeDrawer((int) GravityCompat.START);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 112) {
            int length = strArr.length;
            int i2 = 0;
            char c = 65535;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                String str = strArr[i2];
                if (iArr[i2] == -1) {
                    boolean shouldShowRequestPermissionRationale = Build.VERSION.SDK_INT >= 23 ? shouldShowRequestPermissionRationale(str) : false;
                    Log.i("onPermission", "onRequestPermissionsResult: " + shouldShowRequestPermissionRationale);
                    if (!shouldShowRequestPermissionRationale) {
                        show_alert();
                        break;
                    }
                    c = 1;
                } else {
                    c = 0;
                }
                i2++;
            }
            if (c == 1) {
                ActivityCompat.requestPermissions(this, strArr, 112);
            }
            if (c == 0) {
                displaySelectedScreen("Home");
            }
        }
    }

    public void show_alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage((CharSequence) "This application requires permission. Please ensure " + "that this is enabled in settings, then press the back button to continue ");
        builder.setCancelable(false);
        builder.setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", getPackageName(), (String) null));
                startActivityForResult(intent, 1005);
                mMyDialog.dismiss();
            }
        });
        this.mMyDialog = builder.show();
    }


    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 1005) {
            return;
        }
        if (!hasPermissions(this.context, this.PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) this.context, this.PERMISSIONS, 112);
        } else {
            displaySelectedScreen("Home");
        }
    }

    private class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {
        private Runnable runnable;

        public SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int i, int i2) {
            super(activity, drawerLayout, toolbar, i, i2);
        }



        public void onDrawerStateChanged(int i) {
            super.onDrawerStateChanged(i);
            if (this.runnable != null && i == 0) {
                this.runnable.run();
                this.runnable = null;
            }
        }

        public void runWhenIdle(Runnable runnable2) {
            this.runnable = runnable2;
        }
    }
}
