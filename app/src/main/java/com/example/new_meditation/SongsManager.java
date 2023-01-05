package com.example.new_meditation;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

public class SongsManager {
    String MEDIA_PATH;
    private Context context;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();

    public SongsManager(Context context2) {
        try {
            this.context = context2;
            File externalFilesDir = this.context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
            this.MEDIA_PATH = String.valueOf(new File(externalFilesDir.getAbsolutePath() + "/sleep_music"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String, String>> getPlayList() {
        File file = new File(this.MEDIA_PATH);
        if (file.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file2 : file.listFiles(new FileExtensionFilter())) {
                HashMap hashMap = new HashMap();
                hashMap.put("songTitle", file2.getName().substring(0, file2.getName().length() - 4));
                hashMap.put("songPath", file2.getPath());
                this.songsList.add(hashMap);
            }
        }
        return this.songsList;
    }

    class FileExtensionFilter implements FilenameFilter {
        FileExtensionFilter() {
        }

        public boolean accept(File file, String str) {
            return str.endsWith(".mp3") || str.endsWith(".MP3");
        }
    }
}
