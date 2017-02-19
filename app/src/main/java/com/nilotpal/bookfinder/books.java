package com.nilotpal.bookfinder;

public class books {
    private String mBookName;

    private String mUrl;

    private String mAuthor;

    public books(String bookName, String author, String url) {
        mBookName = bookName;
        mAuthor = author;
        mUrl = url;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getBookName() {
        return mBookName;
    }

    public String getUrl() {
        return mUrl;
    }
}


