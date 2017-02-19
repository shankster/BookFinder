package com.nilotpal.bookfinder;

public class books {
    private String mBookName;

    private String mUrl;

    private String mAuthor;
    private String mThumbNail;

    public books(String bookName) {
        mBookName = bookName;
    }
    public books(String bookName, String author, String url, String thumbnail) {
        mBookName = bookName;
        mAuthor = author;
        mUrl = url;
        mThumbNail = thumbnail;
    }
    public books(String bookName, String author) {
        mBookName = bookName;
        mAuthor = author;
    }



    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getBookName() {
        return mBookName;
    }

    public void setBookName(String mBookName) {
        this.mBookName = mBookName;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getThumbNail() {
        return mThumbNail;
    }

    public void setThumbNail(String mThumbNail) {
        this.mThumbNail = mThumbNail;
    }
}


