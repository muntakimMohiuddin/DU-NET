package com.example.aniomi.myapplication;

/**
 * Created by aniomi on 11/2/17.
 */
/**
 * Created by Rafi on 28-Oct-17.
 */

public class Document {
    String title;
    String url;

    public Document(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Document() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
