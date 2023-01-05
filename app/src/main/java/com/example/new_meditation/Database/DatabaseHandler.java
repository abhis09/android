package com.example.new_meditation.Database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


import com.example.new_meditation.Model.Downloads;

import java.io.File;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_meditation";
    private static final int DATABASE_VERSION = 4;
    private static final String KEY_DID = "did";
    public static final String KEY_NEW_SONG = "songname";
    public static final String KEY_OLD_SONG = "dname";
    private static final String KEY_PLAYLIST_ID = "playlistid";
    public static final String KEY_PLAYLIST_NAME = "playlistname";
    private static final String KEY_RESPONSE3 = "response";
    private static final String KEY_URL3 = "url";
    private static final String TABLE_CONTACTS3 = "offlinestore";
    private static final String TABLE_DOWNLOADS = "downloads";
    private static final String TABLE_PLAYLIST = "playlist";
    private static final String TABLE_PLAYLIST_CHILD = "playlistchild";

    public DatabaseHandler(Context context) {
        super(new DatabaseContext(context), DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 4);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS offlinestore(url TEXT,response TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS downloads(did INTEGER PRIMARY KEY  AUTOINCREMENT,dname TEXT,songname TEXT,imageurl TEXT,favourite INTEGER)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS playlist(playlistid INTEGER PRIMARY KEY AUTOINCREMENT,playlistname TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS playlistchild(pid INTEGER PRIMARY KEY AUTOINCREMENT,playlistid INTEGER,did INTEGER,FOREIGN KEY (did) REFERENCES downloads(did) ON DELETE CASCADE,FOREIGN KEY (playlistid) REFERENCES playlist(playlistid) ON DELETE CASCADE );");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onCreate(sQLiteDatabase);
    }

    public void addoffline(Contact contact) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", contact.getURL3());
            contentValues.put(KEY_RESPONSE3, contact.getRESPONSE3());
            writableDatabase.insert(TABLE_CONTACTS3, (String) null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            writableDatabase.close();
            throw th;
        }
        writableDatabase.close();
    }

    /* JADX INFO: finally extract failed */
    public long addplaylist(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_PLAYLIST_NAME, str);
            long insert = writableDatabase.insert(TABLE_PLAYLIST, (String) null, contentValues);
            writableDatabase.close();
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
            writableDatabase.close();
            return 0;
        } catch (Throwable th) {
            writableDatabase.close();
            throw th;
        }
    }

    public int delplay(int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.delete(TABLE_PLAYLIST, "playlistid = " + i, (String[]) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0050  */
    public int getplaylistid() throws Throwable {
        Cursor cursor;
        Exception e;
        Exception exc;
        int i;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        int i2 = 0;
        try {
            cursor = writableDatabase.rawQuery("SELECT playlistid FROM playlist order by playlistid DESC limit 1", (String[]) null);
            try {
                if (cursor.moveToFirst()) {
                    while (true) {
                        i = cursor.getInt(cursor.getColumnIndex(KEY_PLAYLIST_ID));
                        try {
                            if (!cursor.moveToNext()) {
                                break;
                            }
                            int i3 = i;
                        } catch (Exception e2) {
                            exc = e2;
                            i2 = i;
                            e = exc;
                            try {
                                e.printStackTrace();
                                writableDatabase.close();
                                if (cursor != null) {
                                }
                                return i2;
                            } catch (Throwable th) {
                                th = th;
                                writableDatabase.close();
                                if (cursor != null) {
                                }
                                throw th;
                            }
                        }
                    }
                } else {
                    i = 0;
                }
                writableDatabase.close();
                if (cursor == null) {
                    return i;
                }
                cursor.close();
                return i;
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                writableDatabase.close();
                if (cursor != null) {
                }
                return i2;
            }
        } catch (Exception e4) {
            exc = e4;
            cursor = null;
            e = exc;
            e.printStackTrace();
            writableDatabase.close();
            if (cursor != null) {
                cursor.close();
            }
            return i2;
        } catch (Throwable th2) {
            cursor = null;
            writableDatabase.close();
            if (cursor != null) {
                cursor.close();
            }
            throw th2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0074  */
    public long savePlaylist(int i, int i2) throws Throwable {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = null;
        try {
            Cursor rawQuery = writableDatabase.rawQuery("SELECT * FROM playlistchild WHERE did = " + i2 + " AND playlistid = " + i, (String[]) null);
            try {
                if (rawQuery.getCount() > 0) {
                    writableDatabase.close();
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return 0;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put(KEY_DID, Integer.valueOf(i2));
                contentValues.put(KEY_PLAYLIST_ID, Integer.valueOf(i));
                long insert = writableDatabase.insert(TABLE_PLAYLIST_CHILD, (String) null, contentValues);
                writableDatabase.close();
                if (rawQuery == null) {
                    return insert;
                }
                rawQuery.close();
                return insert;
            } catch (Exception e) {
                e = e;
                cursor = rawQuery;
                try {
                    e.printStackTrace();
                    writableDatabase.close();
                    if (cursor != null) {
                    }
                    return 0;
                } catch (Throwable th) {
                    th = th;
                    writableDatabase.close();
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                cursor = rawQuery;
                writableDatabase.close();
                if (cursor != null) {
                    cursor.close();
                }
                throw th2;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            writableDatabase.close();
            if (cursor != null) {
                cursor.close();
            }
            return 0;
        }
    }

    public int playlistchildcount(int i) throws Throwable {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM playlistchild where playlistchild.playlistid = " + i, (String[]) null);
            try {
                int count = rawQuery.getCount();
                readableDatabase.close();
                if (rawQuery == null) {
                    return count;
                }
                rawQuery.close();
                return count;
            } catch (Exception e) {
                Exception exc = e;
                cursor = rawQuery;
                e = exc;
                try {
                    e.printStackTrace();
                    readableDatabase.close();
                    if (cursor != null) {
                        cursor.close();
                    }
                    return 0;
                } catch (Throwable th) {
                    th = th;
                    readableDatabase.close();
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                Throwable th3 = th2;
                cursor = rawQuery;
                readableDatabase.close();
                if (cursor != null) {
                }
                throw th3;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            readableDatabase.close();
            if (cursor != null) {
            }
            return 0;
        }
    }

    public ArrayList<Downloads> selectPlaylist(Activity activity, String str) throws Throwable {
        Cursor cursor;
        Cursor cursor2;
        Cursor cursor3;
        Cursor cursor4;
        Cursor cursor5;
        ArrayList<Downloads> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor6 = null;
        try {
            cursor = readableDatabase.rawQuery("SELECT playlistid FROM playlist WHERE playlistname = '" + str + "'", (String[]) null);
            try {
                if (cursor.moveToFirst()) {
                    Cursor cursor7 = null;
                    cursor5 = null;
                    while (true) {
                        try {
                            int i = cursor.getInt(cursor.getColumnIndex(KEY_PLAYLIST_ID));
                            cursor2 = readableDatabase.rawQuery("SELECT * FROM playlistchild where playlistchild.playlistid = " + i, (String[]) null);
                            try {
                                if (cursor2.moveToFirst()) {
                                    while (true) {
                                        try {
                                            cursor3 = readableDatabase.rawQuery("SELECT * FROM downloads where did = " + cursor2.getInt(cursor2.getColumnIndex(KEY_DID)), (String[]) null);
                                            do {
                                                try {
                                                    if (cursor3.moveToFirst()) {
                                                        File externalFilesDir = activity.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                                                        String string = cursor3.getString(cursor3.getColumnIndex(KEY_OLD_SONG));
                                                        int i2 = cursor3.getInt(cursor3.getColumnIndex(KEY_DID));
                                                        int i3 = cursor3.getInt(cursor3.getColumnIndex("favourite"));
                                                        arrayList.add(new Downloads(new File(externalFilesDir.getAbsolutePath() + "/sleep_music") + "/" + string, cursor3.getString(cursor3.getColumnIndex(KEY_NEW_SONG)), cursor3.getString(cursor3.getColumnIndex("imageurl")), i2, i, i3));
                                                    } else {
                                                        Activity activity2 = activity;
                                                    }
                                                } catch (Exception e) {
                                                    e = e;
                                                    cursor6 = cursor2;
                                                    try {
                                                        e.printStackTrace();
                                                        readableDatabase.close();
                                                        if (cursor6 != null) {
                                                        }
                                                        if (cursor != null) {
                                                        }
                                                        if (cursor3 != null) {
                                                        }
                                                        return arrayList;
                                                    } catch (Throwable th) {
                                                        th = th;
                                                        cursor2 = cursor6;
                                                        readableDatabase.close();
                                                        if (cursor2 != null) {
                                                            cursor2.close();
                                                        }
                                                        if (cursor != null) {
                                                            cursor.close();
                                                        }
                                                        if (cursor3 != null) {
                                                            cursor3.close();
                                                        }
                                                        throw th;
                                                    }
                                                } catch (Throwable th2) {
                                                    readableDatabase.close();
                                                    if (cursor2 != null) {
                                                    }
                                                    if (cursor != null) {
                                                    }
                                                    if (cursor3 != null) {
                                                    }
                                                    throw th2;
                                                }
                                            } while (cursor3.moveToNext());
                                            if (!cursor2.moveToNext()) {
                                                break;
                                            }
                                            cursor5 = cursor3;
                                        } catch (Exception e2) {
                                            cursor6 = cursor2;
                                            cursor3 = cursor5;
                                            e2.printStackTrace();
                                            readableDatabase.close();
                                            if (cursor6 != null) {
                                            }
                                            if (cursor != null) {
                                            }
                                            if (cursor3 != null) {
                                            }
                                            return arrayList;
                                        } catch (Throwable th3) {
                                            cursor3 = cursor5;
                                            readableDatabase.close();
                                            if (cursor2 != null) {
                                            }
                                            if (cursor != null) {
                                            }
                                            if (cursor3 != null) {
                                            }
                                            throw th3;
                                        }
                                    }
                                    cursor5 = cursor3;
                                    cursor4 = cursor2;
                                } else {
                                    Activity activity3 = activity;
                                    cursor4 = cursor2;
                                    try {
                                        Downloads downloads = new Downloads(str, "", "", 0, i, 0);
                                        arrayList.add(downloads);
                                    } catch (Exception e3) {
                                        cursor3 = cursor5;
                                        cursor6 = cursor4;
                                        e3.printStackTrace();
                                        readableDatabase.close();
                                        if (cursor6 != null) {
                                        }
                                        if (cursor != null) {
                                        }
                                        if (cursor3 != null) {
                                        }
                                        return arrayList;
                                    } catch (Throwable th4) {
                                        cursor3 = cursor5;
                                        cursor2 = cursor4;
                                        readableDatabase.close();
                                        if (cursor2 != null) {
                                        }
                                        if (cursor != null) {
                                        }
                                        if (cursor3 != null) {
                                        }
                                        throw th4;
                                    }
                                }
                                if (!cursor.moveToNext()) {
                                    break;
                                }
                                cursor7 = cursor4;
                            } catch (Exception e4) {
                                cursor4 = cursor2;
                                cursor3 = cursor5;
                                cursor6 = cursor4;
                                e4.printStackTrace();
                                readableDatabase.close();
                                if (cursor6 != null) {
                                }
                                if (cursor != null) {
                                }
                                if (cursor3 != null) {
                                }
                                return arrayList;
                            } catch (Throwable th5) {
                                Cursor cursor8 = cursor2;
                                cursor3 = cursor5;
                                readableDatabase.close();
                                if (cursor2 != null) {
                                }
                                if (cursor != null) {
                                }
                                if (cursor3 != null) {
                                }
                                throw th5;
                            }
                        } catch (Exception e5) {
                            cursor6 = cursor7;
                            cursor3 = cursor5;
                            e5.printStackTrace();
                            readableDatabase.close();
                            if (cursor6 != null) {
                            }
                            if (cursor != null) {
                            }
                            if (cursor3 != null) {
                            }
                            return arrayList;
                        } catch (Throwable th6) {
                            cursor2 = cursor7;
                            cursor3 = cursor5;
                            readableDatabase.close();
                            if (cursor2 != null) {
                            }
                            if (cursor != null) {
                            }
                            if (cursor3 != null) {
                            }
                            throw th6;
                        }
                    }
                } else {
                    cursor5 = null;
                    cursor4 = null;
                }
                readableDatabase.close();
                if (cursor4 != null) {
                    cursor4.close();
                }
                if (cursor != null) {
                    cursor.close();
                }
                if (cursor5 != null) {
                    cursor5.close();
                }
            } catch (Exception e6) {
                cursor3 = null;
                e6.printStackTrace();
                readableDatabase.close();
                if (cursor6 != null) {
                }
                if (cursor != null) {
                }
                if (cursor3 != null) {
                }
                return arrayList;
            } catch (Throwable th7) {
                cursor3 = null;
                cursor2 = null;
                readableDatabase.close();
                if (cursor2 != null) {
                }
                if (cursor != null) {
                }
                if (cursor3 != null) {
                }
                throw th7;
            }
        } catch (Exception e7) {
            cursor3 = null;
            cursor = null;
            e7.printStackTrace();
            readableDatabase.close();
            if (cursor6 != null) {
                cursor6.close();
            }
            if (cursor != null) {
                cursor.close();
            }
            if (cursor3 != null) {
                cursor3.close();
            }
            return arrayList;
        } catch (Throwable th8) {
            cursor3 = null;
            cursor2 = null;
            cursor = null;
            readableDatabase.close();
            if (cursor2 != null) {
            }
            if (cursor != null) {
            }
            if (cursor3 != null) {
            }
            throw th8;
        }
        return arrayList;
    }
    public ArrayList<Downloads> loadplaylist() throws Throwable {
        Cursor cursor;
        Exception e;
        ArrayList<Downloads> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            cursor = readableDatabase.rawQuery("SELECT * FROM playlist", (String[]) null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        arrayList.add(new Downloads(cursor.getString(cursor.getColumnIndex(KEY_PLAYLIST_NAME)), "", "", 0, cursor.getInt(cursor.getColumnIndex(KEY_PLAYLIST_ID)), 0));
                    } while (cursor.moveToNext());
                }
                readableDatabase.close();
            } catch (Exception e2) {
                e = e2;
                try {
                    e.printStackTrace();
                    readableDatabase.close();
                } catch (Throwable th) {
                    th = th;
                    readableDatabase.close();
                    if (cursor != null) {
                    }
                    throw th;
                }
            }
        } catch (Exception e3) {
            Exception exc = e3;
            cursor = null;
            e = exc;
            e.printStackTrace();
            readableDatabase.close();
        } catch (Throwable th2) {
            cursor = null;
            readableDatabase.close();
            if (cursor != null) {
                cursor.close();
            }
            throw th2;
        }
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006d, code lost:
        if (r3 != null) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006f, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0072, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x005b, code lost:
        if (r3 != null) goto L_0x006f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0079  */
    public ArrayList<Downloads> loadfav() throws Throwable {
        Cursor cursor;
        Exception e;
        ArrayList<Downloads> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            cursor = readableDatabase.rawQuery("SELECT * FROM downloads where favourite = 1", (String[]) null);
            try {
                if (cursor.moveToFirst()) {
                    do {
                        arrayList.add(new Downloads(cursor.getString(cursor.getColumnIndex(KEY_OLD_SONG)), cursor.getString(cursor.getColumnIndex(KEY_NEW_SONG)), cursor.getString(cursor.getColumnIndex("imageurl")), cursor.getInt(cursor.getColumnIndex(KEY_DID)), 0, cursor.getInt(cursor.getColumnIndex("favourite"))));
                    } while (cursor.moveToNext());
                }
                readableDatabase.close();
            } catch (Exception e2) {
                e = e2;
                try {
                    e.printStackTrace();
                    readableDatabase.close();
                } catch (Throwable th) {
                    th = th;
                    readableDatabase.close();
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        } catch (Exception e3) {
            Exception exc = e3;
            cursor = null;
            e = exc;
            e.printStackTrace();
            readableDatabase.close();
        } catch (Throwable th2) {
            cursor = null;
            readableDatabase.close();
            if (cursor != null) {
            }
            throw th2;
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x009b  */
    public ArrayList<String> loadplaylistchild() throws Throwable {
        Cursor cursor;
        Cursor cursor2;
        Exception e;
        Cursor rawQuery;
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor3 = null;
        try {
            cursor2 = readableDatabase.rawQuery("SELECT * FROM playlistchild", (String[]) null);
            try {
                if (cursor2.moveToFirst()) {
                    cursor = null;
                    while (true) {
                        try {
                            rawQuery = readableDatabase.rawQuery("SELECT * FROM downloads,playlistchild where playlistchild.did = " + cursor2.getInt(cursor2.getColumnIndex(KEY_DID)), (String[]) null);
                            try {
                                if (rawQuery.moveToFirst()) {
                                    do {
                                        arrayList.add(rawQuery.getString(rawQuery.getColumnIndex(KEY_OLD_SONG)));
                                    } while (rawQuery.moveToNext());
                                }
                                if (!cursor2.moveToNext()) {
                                    break;
                                }
                                cursor = rawQuery;
                            } catch (Exception e2) {
                                e = e2;
                                cursor = rawQuery;
                                try {
                                    e.printStackTrace();
                                    readableDatabase.close();
                                    if (cursor2 != null) {
                                    }
                                    if (cursor != null) {
                                    }
                                    return arrayList;
                                } catch (Throwable th) {
                                    th = th;
                                    readableDatabase.close();
                                    if (cursor2 != null) {
                                        cursor2.close();
                                    }
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                cursor = rawQuery;
                                readableDatabase.close();
                                if (cursor2 != null) {
                                }
                                if (cursor != null) {
                                }
                                throw th2;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            e.printStackTrace();
                            readableDatabase.close();
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            if (cursor != null) {
                                cursor.close();
                            }
                            return arrayList;
                        }
                    }
                    cursor3 = rawQuery;
                }
                readableDatabase.close();
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (cursor3 != null) {
                    cursor3.close();
                }
            } catch (Exception e4) {
                Exception exc = e4;
                cursor = null;
                e = exc;
                e.printStackTrace();
                readableDatabase.close();
                if (cursor2 != null) {
                }
                if (cursor != null) {
                }
                return arrayList;
            } catch (Throwable th3) {
                cursor = null;
                readableDatabase.close();
                if (cursor2 != null) {
                }
                if (cursor != null) {
                }
                throw th3;
            }
        } catch (Exception e5) {
            cursor = null;
            e = e5;
            cursor2 = null;
            e.printStackTrace();
            readableDatabase.close();
            if (cursor2 != null) {
            }
            if (cursor != null) {
            }
            return arrayList;
        } catch (Throwable th4) {
            cursor2 = null;
            cursor = null;
            readableDatabase.close();
            if (cursor2 != null) {
            }
            if (cursor != null) {
            }
            throw th4;
        }
        return arrayList;
    }

    /* JADX INFO: finally extract failed */
    public long addSong(Downloads downloads) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_OLD_SONG, downloads.getDname());
            contentValues.put(KEY_NEW_SONG, downloads.getSname());
            contentValues.put("imageurl", downloads.getImageurl());
            contentValues.put("favourite", 0);
            long insert = writableDatabase.insert(TABLE_DOWNLOADS, (String) null, contentValues);
            writableDatabase.close();
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
            writableDatabase.close();
            return 0;
        } catch (Throwable th) {
            writableDatabase.close();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public int updatepname(int i, String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_PLAYLIST_NAME, str);
            int update = writableDatabase.update(TABLE_PLAYLIST, contentValues, "playlistid = " + i, (String[]) null);
            writableDatabase.close();
            return update;
        } catch (Exception e) {
            e.printStackTrace();
            writableDatabase.close();
            return 0;
        } catch (Throwable th) {
            writableDatabase.close();
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0097  */
    public int updateRecord(String str, int i) throws Throwable {
        int i2;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = null;
        try {
            Cursor cursor2 = writableDatabase.rawQuery("SELECT * FROM downloads WHERE dname = '" + str + "'", (String[]) null);
            try {
                if (cursor2.moveToFirst()) {
                    do {
                        i2 = cursor2.getInt(cursor2.getColumnIndex(KEY_DID));
                    } while (cursor2.moveToNext());
                } else {
                    i2 = 0;
                }
                Log.i("ContentValues", "updateRecord: " + i2);
                ContentValues contentValues = new ContentValues();
                contentValues.put("favourite", Integer.valueOf(i));
                int update = writableDatabase.update(TABLE_DOWNLOADS, contentValues, "did = " + i2, (String[]) null);
                writableDatabase.close();
                if (cursor2 == null) {
                    return update;
                }
                cursor2.close();
                return update;
            } catch (Exception e) {
                e = e;
                cursor = cursor2;
                try {
                    e.printStackTrace();
                    writableDatabase.close();
                    if (cursor != null) {
                        cursor.close();
                    }
                    return 0;
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    writableDatabase.close();
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                writableDatabase.close();
                if (cursor2 != null) {
                }
                throw th2;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            writableDatabase.close();
            if (cursor != null) {
            }
            return 0;
        }
    }

    public int updateRecordsname(String str, int i) throws Throwable {

        int i2;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor cursor = null;
        try {
            Log.e("SHIVA", "updateRecordsname: "+123);
            Cursor cursor2 = writableDatabase.rawQuery("SELECT * FROM downloads WHERE songname = '" + str + "'", (String[]) null);
            try {
                if (cursor2.moveToFirst()) {
                    do {
                        i2 = cursor2.getInt(cursor2.getColumnIndex(KEY_DID));
                    } while (cursor2.moveToNext());
                } else {
                    i2 = 0;
                    Log.e("SHIVA", "updateRecordsname__else: "+123);
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("favourite", Integer.valueOf(i));
                int update = writableDatabase.update(TABLE_DOWNLOADS, contentValues, "did = " + i2, (String[]) null);
                writableDatabase.close();
                if (cursor2 == null) {
                    return update;
                }
                cursor2.close();
                return update;
            } catch (Exception e) {
                Log.e("SHIV", "updateRecordsname: "+e.getMessage());
                e = e;
                cursor = cursor2;
                try {
                    e.printStackTrace();
                    writableDatabase.close();
                    if (cursor != null) {
                    }
                    return 0;
                } catch (Throwable th) {
                    cursor2 = cursor;
                    writableDatabase.close();
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                writableDatabase.close();
                if (cursor2 != null) {
                }
                throw th2;
            }
        } catch (Exception e2) {
            Log.e("SHIV", "updateRecordsname___excep2: "+e2.getMessage());
            e2.printStackTrace();
            writableDatabase.close();
            if (cursor != null) {
                cursor.close();
            }
            return 0;
        }
    }

    public int getdname(String str) throws Throwable {
        int i = 0;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        int i2 = 0;
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT did  FROM downloads WHERE TRIM(dname) = '" + str.trim() + "'", (String[]) null);
            try {
                if (rawQuery.moveToFirst()) {
                    while (true) {
                        i = rawQuery.getInt(rawQuery.getColumnIndex(KEY_DID));
                        if (!rawQuery.moveToNext()) {
                            break;
                        }
                        int i3 = i;
                    }
                } else {
                    i = 0;
                }
                readableDatabase.close();
                if (rawQuery == null) {
                    return i;
                }
                rawQuery.close();
                return i;
            } catch (Exception e) {
                int i4 = i;
                cursor = rawQuery;
                e = e;
                i2 = i4;
                try {
                    e.printStackTrace();
                    readableDatabase.close();
                    if (cursor != null) {
                    }
                    return i2;
                } catch (Throwable th) {
                    th = th;
                    readableDatabase.close();
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            readableDatabase.close();
            if (cursor != null) {
            }
            return i2;
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008f  */
    public int getsnameid(String str) throws Throwable {
        Cursor cursor;
        Exception e;
        int i;
        new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        int i2 = 0;
        try {
            cursor = readableDatabase.rawQuery("SELECT did  FROM downloads WHERE TRIM(songname) = '" + str.trim() + "'", (String[]) null);
            try {
                if (cursor.moveToFirst()) {
                    while (true) {
                        Log.i("ContentValues", "getsnameid: " + cursor.getInt(cursor.getColumnIndex(KEY_DID)));
                        i = cursor.getInt(cursor.getColumnIndex(KEY_DID));
                        try {
                            if (!cursor.moveToNext()) {
                                break;
                            }
                            int i3 = i;
                        } catch (Exception e2) {
                            Exception exc = e2;
                            i2 = i;
                            e = exc;
                            try {
                                e.printStackTrace();
                                readableDatabase.close();
                                if (cursor != null) {
                                    cursor.close();
                                }
                                return i2;
                            } catch (Throwable th) {
                                th = th;
                                readableDatabase.close();
                                if (cursor != null) {
                                }
                                throw th;
                            }
                        }
                    }
                } else {
                    i = 0;
                }
                readableDatabase.close();
                if (cursor == null) {
                    return i;
                }
                cursor.close();
                return i;
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                readableDatabase.close();
                if (cursor != null) {
                }
                return i2;
            }
        } catch (Exception e4) {
            e = e4;
            cursor = null;
            e.printStackTrace();
            readableDatabase.close();
            if (cursor != null) {
            }
            return i2;
        } catch (Throwable th2) {
            cursor = null;
            readableDatabase.close();
            if (cursor != null) {
                cursor.close();
            }
            throw th2;
        }
    }

    public String getDname(String str) throws Throwable {
        String str2 = null;
        new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT dname FROM downloads where TRIM(songname) = '" + str.trim() + "'", (String[]) null);
            try {
                if (rawQuery.moveToFirst()) {
                    while (true) {
                        str2 = rawQuery.getString(rawQuery.getColumnIndex(KEY_OLD_SONG));
                        if (!rawQuery.moveToNext()) {
                            break;
                        }
                        String str3 = str2;
                    }
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                readableDatabase.close();
                return str2 ;
            } catch (Exception e) {
                Exception exc = e;
//                r1 = rawQuery;
                e = exc;
                try {
                    e.printStackTrace();
//                    if (r1 != 0) {
//                    }
                    readableDatabase.close();
                    return str2;
                } catch (Throwable th) {
                    th = th;
                    Cursor cursor = null;
                    if (cursor != null) {
                    }
                    readableDatabase.close();
                    throw th;
                }
            } catch (Throwable th2) {
            }
        } catch (Exception e2) {
            str2 = null;
            e2.printStackTrace();
//            if (r1 != 0) {
//                r1.close();
//            }
            readableDatabase.close();
            return str2;
        }
        return str2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004d, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
        r5 = r1;
        r1 = r7;
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0052, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        r5 = r1;
        r1 = r7;
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005f, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0069, code lost:
        r1.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004d A[ExcHandler: all (r1v6 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:3:0x0024] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0069  */
    public int getId(String str) throws Throwable {
        int i = 0;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        int i2 = 0;
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT did FROM downloads where TRIM(songname) = '" + str.trim() + "'", (String[]) null);
            try {
                if (rawQuery.moveToFirst()) {
                    while (true) {
                        i = rawQuery.getInt(rawQuery.getColumnIndex(KEY_DID));
                        if (!rawQuery.moveToNext()) {
                            break;
                        }
                        int i3 = i;
                    }
                } else {
                    i = 0;
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                readableDatabase.close();
                return i;
            } catch (Exception e) {
                int i4 = i;
                cursor = rawQuery;
                e = e;
                i2 = i4;
                try {
                    e.printStackTrace();
                    if (cursor != null) {
                    }
                    readableDatabase.close();
                    return i2;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    readableDatabase.close();
                    throw th;
                }
            } catch (Throwable th2) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (cursor != null) {
            }
            readableDatabase.close();
            return i2;
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004d, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004e, code lost:
        r5 = r1;
        r1 = r7;
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0052, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        r5 = r1;
        r1 = r7;
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005f, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0069, code lost:
        r1.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x004d A[ExcHandler: all (r1v6 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:3:0x0024] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0069  */
    public int getpid(String str) throws Throwable {
        int i = 0;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        int i2 = 0;
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT playlistid FROM playlist where TRIM(playlistname) = '" + str.trim() + "'", (String[]) null);
            try {
                if (rawQuery.moveToFirst()) {
                    while (true) {
                        i = rawQuery.getInt(rawQuery.getColumnIndex(KEY_PLAYLIST_ID));
                        if (!rawQuery.moveToNext()) {
                            break;
                        }
                        int i3 = i;
                    }
                } else {
                    i = 0;
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                readableDatabase.close();
                return i;
            } catch (Exception e) {
                int i4 = i;
                cursor = rawQuery;
                e = e;
                i2 = i4;
                try {
                    e.printStackTrace();
                    if (cursor != null) {
                    }
                    readableDatabase.close();
                    return i2;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                    }
                    readableDatabase.close();
                    throw th;
                }
            } catch (Throwable th2) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (cursor != null) {
            }
            readableDatabase.close();
            return i2;
        }
        return i;
    }

    /* JADX INFO: finally extract failed */
    public int delplaylist(int i) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        try {
            int delete = readableDatabase.delete(TABLE_PLAYLIST, "playlistid = " + i, (String[]) null);
            readableDatabase.close();
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            readableDatabase.close();
            return 0;
        } catch (Throwable th) {
            readableDatabase.close();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b8, code lost:
        if (r8 != null) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c9, code lost:
        if (r8 != null) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00cb, code lost:
        r8.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ce, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d1, code lost:
        return r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00d5  */
    public int getstatus(String str) throws Throwable {
        Cursor cursor;
        Exception e;
        int i = 0;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        int i2 = 0;
        try {
            Log.i("ContentValues", "getstatus: 1" + str);
            cursor = readableDatabase.rawQuery("SELECT * FROM downloads where dname = '" + str + "' ", (String[]) null);
            try {
                if (cursor.moveToFirst()) {
                    int i3 = 0;
                    while (true) {
                        try {
                            Log.i("ContentValues", "getstatus: 2" + cursor.getString(cursor.getColumnIndex(KEY_OLD_SONG)));
                            i = cursor.getInt(cursor.getColumnIndex("favourite"));
                        } catch (Exception e2) {
                            Exception exc = e2;
                            i2 = i3;
                            e = exc;
                            try {
                                e.printStackTrace();
                            } catch (Throwable th) {
                                th = th;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                readableDatabase.close();
                                throw th;
                            }
                        }
                        try {
                            Log.i("ContentValues", "getstatus: 2" + cursor.getInt(cursor.getColumnIndex("favourite")));
                            if (!cursor.moveToNext()) {
                                break;
                            }
                            i3 = i;
                        } catch (Exception e3) {
                            e = e3;
                            i2 = i;
                            e.printStackTrace();
                        }
                    }
                } else {
                    i = 0;
                }
                if (i != 0) {
                    i2 = 1;
                }
                Log.i("ContentValues", "getstatus: " + cursor.getCount());
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
            }
        } catch (Exception e5) {
            e = e5;
            cursor = null;
            e.printStackTrace();
        } catch (Throwable th2) {
            cursor = null;
            if (cursor != null) {
            }
            readableDatabase.close();
            throw th2;
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003a, code lost:
        r1 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003b, code lost:
        r2 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003e, code lost:
        r5 = r1;
        r1 = r7;
        r7 = r2;
        r2 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0051, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0052, code lost:
        r5 = r1;
        r1 = r7;
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0066, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x006f, code lost:
        r1.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0051 A[ExcHandler: all (r1v6 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:3:0x0020] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x006f  */
    public int getstatusSongname(String str) throws Throwable {
        int i;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        int i2 = 0;
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT favourite FROM downloads where songname = '" + str + "' ", (String[]) null);
            try {
                if (rawQuery.moveToFirst()) {
                    int i3 = 0;
                    while (true) {
                        i = rawQuery.getInt(rawQuery.getColumnIndex("favourite"));
                        if (!rawQuery.moveToNext()) {
                            break;
                        }
                        i3 = i;
                    }
                } else {
                    i = 0;
                }
                if (i != 0) {
                    i2 = 1;
                }
                readableDatabase.close();
                if (rawQuery != null) {
                    rawQuery.close();
                }
            } catch (Exception e) {
                e = e;
                Exception exc = e;
                cursor = rawQuery;
                e = exc;
                try {
                    e.printStackTrace();
                    readableDatabase.close();
                    if (cursor != null) {
                    }
                    return i2;
                } catch (Throwable th) {
                    th = th;
                    readableDatabase.close();
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            readableDatabase.close();
            if (cursor != null) {
            }
            return i2;
        }
        return i2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x00b6  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00bf  */
    public ArrayList<Downloads> all(Context context) throws Throwable {
        ArrayList<Downloads> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            Cursor cursor2 = readableDatabase.rawQuery("SELECT * FROM downloads ", (String[]) null);
            try {
                if (cursor2.moveToFirst()) {
                    do {
                        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                        File file = new File(externalFilesDir.getAbsolutePath() + "/sleep_music");
                        String string = cursor2.getString(cursor2.getColumnIndex(KEY_OLD_SONG));
                        int i = cursor2.getInt(cursor2.getColumnIndex("favourite"));
                        int i2 = cursor2.getInt(cursor2.getColumnIndex(KEY_DID));
                        Log.i("ContentValues", "all: " + string);
                        arrayList.add(new Downloads(file + "/" + string, cursor2.getString(cursor2.getColumnIndex(KEY_NEW_SONG)), cursor2.getString(cursor2.getColumnIndex("imageurl")), i2, 0, i));
                    } while (cursor2.moveToNext());
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
            } catch (Exception e) {
                e = e;
                cursor = cursor2;
                try {
                    e.printStackTrace();
                    if (cursor != null) {
                    }
                    readableDatabase.close();
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                    }
                    readableDatabase.close();
                    throw th;
                }
            } catch (Throwable th2) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                readableDatabase.close();
                throw th2;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            readableDatabase.close();
            return arrayList;
        }
        readableDatabase.close();
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a9  */
    public ArrayList<Downloads> allFav(Context context) throws Throwable {
        ArrayList<Downloads> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        try {
            Cursor cursor2 = readableDatabase.rawQuery("SELECT * FROM downloads where favourite = 1", (String[]) null);
            try {
                if (cursor2.moveToFirst()) {
                    do {
                        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                        arrayList.add(new Downloads(new File(externalFilesDir.getAbsolutePath() + "/sleep_music") + "/" + cursor2.getString(cursor2.getColumnIndex(KEY_OLD_SONG)), cursor2.getString(cursor2.getColumnIndex(KEY_NEW_SONG)), cursor2.getString(cursor2.getColumnIndex("imageurl")), cursor2.getInt(cursor2.getColumnIndex(KEY_DID)), 0, cursor2.getInt(cursor2.getColumnIndex("favourite"))));
                    } while (cursor2.moveToNext());
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
            } catch (Exception e) {
                e = e;
                cursor = cursor2;
                try {
                    e.printStackTrace();
                    if (cursor != null) {
                    }
                    readableDatabase.close();
                    return arrayList;
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    readableDatabase.close();
                    throw th;
                }
            } catch (Throwable th2) {
                if (cursor2 != null) {
                }
                readableDatabase.close();
                throw th2;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            readableDatabase.close();
            return arrayList;
        }
        readableDatabase.close();
        return arrayList;
    }

    public int delete(String str) {
        int i;
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            i = writableDatabase.delete(TABLE_PLAYLIST_CHILD, "did = (SELECT did from downloads where dname = '" + str + "')", (String[]) null);
            try {
                writableDatabase.delete(TABLE_DOWNLOADS, "dname = '" + str + "'", (String[]) null);
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            i = 0;
            try {
                e2.printStackTrace();
                writableDatabase.close();
                return i;
            } catch (Throwable th) {
                writableDatabase.close();
                throw th;
            }
        }
        writableDatabase.close();
        return i;
    }

    /* JADX INFO: finally extract failed */
    public int deletePlaylist(int i, int i2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            int delete = writableDatabase.delete(TABLE_PLAYLIST_CHILD, "did = " + i + " AND playlistid = " + i2, (String[]) null);
            if (delete > 0) {
                writableDatabase.close();
                return delete;
            }
            writableDatabase.close();
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            writableDatabase.close();
            return 0;
        } catch (Throwable th) {
            writableDatabase.close();
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0052  */
    public boolean isExist(String str) throws Throwable {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor = null;
        boolean z = false;
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM downloads WHERE dname = '" + str + "'", (String[]) null);
            try {
                if (rawQuery.getCount() > 0) {
                    z = true;
                }
                rawQuery.close();
                readableDatabase.close();
                if (rawQuery != null) {
                    rawQuery.close();
                }
            } catch (Exception e) {
                Exception exc = e;
                cursor = rawQuery;
                e = exc;
                try {
                    e.printStackTrace();
                    readableDatabase.close();
                    if (cursor != null) {
                    }
                    return z;
                } catch (Throwable th) {
                    th = th;
                    readableDatabase.close();
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                Throwable th3 = th2;
                cursor = rawQuery;
                readableDatabase.close();
                if (cursor != null) {
                    cursor.close();
                }
                throw th3;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            readableDatabase.close();
            if (cursor != null) {
                cursor.close();
            }
            return z;
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0048  */
    public String getResponse(String str) throws Throwable {
        Cursor cursor;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor cursor2 = null;
        try {
            cursor = readableDatabase.query(TABLE_CONTACTS3, new String[]{KEY_RESPONSE3}, "url=?", new String[]{str}, (String) null, (String) null, (String) null, (String) null);
            if (cursor != null) {
                try {
                    cursor.moveToFirst();
                } catch (Exception e) {
                    e = e;
                    try {
                        e.printStackTrace();
                        if (cursor != null) {
                        }
                        readableDatabase.close();
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        readableDatabase.close();
                        throw th;
                    }
                }
            }
            String string = cursor.getString(0);
            if (cursor != null) {
                cursor.close();
            }
            readableDatabase.close();
            return string;
        } catch (Exception e2) {
            cursor = null;
            e2.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
            readableDatabase.close();
            return null;
        } catch (Throwable th2) {
            if (cursor2 != null) {
            }
            readableDatabase.close();
            throw th2;
        }
    }

    public void deleteResponse(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        try {
            writableDatabase.delete(TABLE_CONTACTS3, "url = ?", new String[]{str});
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            writableDatabase.close();
            throw th;
        }
        writableDatabase.close();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        r5 = r2;
        r2 = r7;
        r7 = r1;
        r1 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
        r2 = r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0028 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0011] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    public int getResponseCount(String str) throws Throwable {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        int i = 0;
        Cursor cursor = null;
        try {
            Cursor rawQuery = readableDatabase.rawQuery("SELECT  * FROM offlinestore WHERE url = ?", new String[]{str});
            try {
                int count = rawQuery.getCount();
                rawQuery.close();
                readableDatabase.close();
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return count;
            } catch (Exception e) {
                Exception exc = e;
                cursor = rawQuery;
                e = exc;
                try {
                    e.printStackTrace();
                    readableDatabase.close();
                    if (cursor == null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    readableDatabase.close();
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            readableDatabase.close();
            if (cursor == null) {
                return i;
            }
            cursor.close();
            return i;
        }
        return i;
    }
}
