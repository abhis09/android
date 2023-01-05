package com.example.new_meditation.Database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import java.io.File;

public class DatabaseContext extends ContextWrapper {
    private static final String DEBUG_CONTEXT = "DatabaseContext";

    public DatabaseContext(Context context) {
        super(context);
    }

    public File getDatabasePath(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String str2 = externalStorageDirectory.getAbsolutePath() + File.separator + "databases" + File.separator + str;
        if (!str2.endsWith(".db")) {
            str2 = str2 + ".db";
        }
        File file = new File(str2);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (Log.isLoggable(DEBUG_CONTEXT, 5)) {
            Log.w(DEBUG_CONTEXT, "getDatabasePath(" + str + ") = " + file.getAbsolutePath());
        }
        return file;
    }

    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory) {
        SQLiteDatabase openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(str), (SQLiteDatabase.CursorFactory) null);
        if (Log.isLoggable(DEBUG_CONTEXT, 5)) {
            Log.w(DEBUG_CONTEXT, "openOrCreateDatabase(" + str + ",,) = " + openOrCreateDatabase.getPath());
        }
        return openOrCreateDatabase;
    }
}
