package com.nilotpal.bookfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class booksAdapter extends ArrayAdapter<books> {
    private static final String LOG_TAG=booksAdapter.class.getName();

    public booksAdapter(Context context, List<books> books) {
        super(context, 0, books);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_bookslist, parent, false);
        }

        books newBooks=getItem(position);

        TextView bookName=(TextView)listItemView.findViewById(R.id.bookListing);
        bookName.setText(newBooks.getBookName());
        TextView authorName=(TextView)listItemView.findViewById(R.id.authorName);
        authorName.setText(newBooks.getAuthor());

        Log.e(LOG_TAG,"Program reaches booksAdapter and list is populated");
        return listItemView;
    }
}
