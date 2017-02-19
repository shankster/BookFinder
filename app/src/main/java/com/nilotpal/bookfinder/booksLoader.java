package com.nilotpal.bookfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class booksLoader extends AsyncTaskLoader<List<books>> {

    private static final String LOG_TAG = booksLoader.class.getName();
    private String mUrl;
    private String mKeyword;

    public booksLoader(Context context, String url, String keyword) {
        super(context);
        mUrl = url;
        mKeyword = keyword;
    }

    protected void onStartLoading() {
        Log.e(LOG_TAG, "Program reaches onStartLoading in booksLoader");
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<books> loadInBackground() {
        Log.e(LOG_TAG, "url and keyword in load in background is " + mUrl + mKeyword);
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<books> books = null;
        try {
            books = queryBooks.fetchBooksData(mUrl, mKeyword);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}