package com.example.new_meditation.Model;

public class Downloads {
    private int did;
    private String dname;
    private int fav;
    private String imageurl;
    private int playlistid;
    private int sid;
    private String sname;

    public Downloads() {
    }

    public Downloads(String str, String str2, String str3, int i, int i2, int i3) {
        this.dname = str;
        this.sname = str2;
        this.imageurl = str3;
        this.did = i;
        this.playlistid = i2;
        this.fav = i3;
    }

    public int getSid() {
        return this.sid;
    }

    public void setSid(int i) {
        this.sid = i;
    }

    public String getDname() {
        return this.dname;
    }

    public void setDname(String str) {
        this.dname = str;
    }

    public String getSname() {
        return this.sname;
    }

    public void setSname(String str) {
        this.sname = str;
    }

    public String getImageurl() {
        return this.imageurl;
    }

    public void setImageurl(String str) {
        this.imageurl = str;
    }

    public int getDid() {
        return this.did;
    }

    public void setDid(int i) {
        this.did = i;
    }

    public int getPlaylistid() {
        return this.playlistid;
    }

    public void setPlaylistid(int i) {
        this.playlistid = i;
    }

    public int getFav() {
        return this.fav;
    }

    public void setFav(int i) {
        this.fav = i;
    }
}
