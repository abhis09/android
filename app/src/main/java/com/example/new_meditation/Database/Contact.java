package com.example.new_meditation.Database;

public class Contact {
    String _response3;
    String _url3;

    public Contact(String str, String str2) {
        this._url3 = str;
        this._response3 = str2;
    }

    public String getURL3() {
        return this._url3;
    }

    public void setURL3(String str) {
        this._url3 = str;
    }

    public String getRESPONSE3() {
        return this._response3;
    }

    public void setRESPONSE3(String str) {
        this._response3 = str;
    }
}
