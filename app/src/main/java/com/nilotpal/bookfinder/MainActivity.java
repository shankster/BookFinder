package com.nilotpal.bookfinder;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<books>> {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static String link = " https://www.googleapis.com/books/v1/volumes?q=";
    private static String keyword = "";
    private booksAdapter mAdapter;
    private static final int BOOKS_LOADER_ID = 1;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button=(Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editText=(EditText) findViewById(R.id.searchText);
                String replace= String.valueOf(editText.getText());
                keyword=replace;
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(BOOKS_LOADER_ID, null, MainActivity.this);

            }
        });
        ListView booksListView = (ListView) findViewById(R.id.list);
        mAdapter = new booksAdapter(this, new ArrayList<books>());
        booksListView.setAdapter(mAdapter);

        Log.e(LOG_TAG, "List is populated in " + LOG_TAG);
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                books currentBook = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<books>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        Log.e(LOG_TAG, "Loader created");
        return new booksLoader(this, link, keyword);
    }

    @Override
    public void onLoadFinished(Loader<List<books>> loader, List<books> data) {
        Log.e(LOG_TAG, "Loader finished");
        mAdapter.addAll(data);
//        mAdapter.clear();
    }

    @Override
    public void onLoaderReset(Loader<List<books>> loader) {
        Log.e(LOG_TAG, "Loader Reset");
        mAdapter.clear();
    }
}
