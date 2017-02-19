package com.nilotpal.bookfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class queryBooks extends AppCompatActivity {
    private static final String LOG_TAG = queryBooks.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookslist);
    }

    public static URL createUrl(String stringUrl, String keyword) throws MalformedURLException {
        Log.e(LOG_TAG, "Program reaches createUrl");
        String finalLink = stringUrl + keyword;
        URL url = null;
        url = new URL(finalLink);
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        Log.e(LOG_TAG, "Program reaches makeHttpRequest");
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamRead = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader read = new BufferedReader(inputStreamRead);
            String line = read.readLine();
            while (line != null) {
                output.append(line);
                line = read.readLine();
            }
        }
        Log.e(LOG_TAG, "Program reaches readFromStream");
        return output.toString();
    }

    private static List<books> extractInfoFromJson(String booksJson) {
        if (TextUtils.isEmpty(booksJson)) {
            return null;
        }
        Log.e(LOG_TAG, "extractFromJson started");

        List<books> books = new ArrayList<>();

        try {
            String finalAuthorString="";
            JSONObject rootDoc = new JSONObject(booksJson);
            JSONArray elementArray = rootDoc.getJSONArray("items");
            for (int i = 0; i < elementArray.length(); i++) {
                JSONObject currentElement = elementArray.getJSONObject(i);
                JSONObject volumeInfo = currentElement.getJSONObject("volumeInfo");
                String element = volumeInfo.getString("title");

                if (volumeInfo.has("authors")) {
                    JSONArray authorsList = volumeInfo.getJSONArray("authors");
                    Log.e(LOG_TAG, String.valueOf(authorsList.length()));
                    final int authorLength=authorsList.length();
                    for(int j=0;j<authorLength;j++){
                        Log.e(LOG_TAG,authorsList.getString(j));
                        if(authorLength==1){
                            finalAuthorString = finalAuthorString + authorsList.getString(j);
                        }
                        else {
                            finalAuthorString = finalAuthorString + authorsList.getString(j) + ","+" ";
                        }
                    }
//                    finalAuthorString=formatFinalString(finalAuthorString);
                }

                String linkURL=volumeInfo.getString("previewLink");
                Log.e(LOG_TAG,linkURL);
                books book=new books(element,finalAuthorString,linkURL);
                books.add(book);
                finalAuthorString="";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(LOG_TAG, "extractFromJson finished");
        return books;
    }

    public static List<books> fetchBooksData(String requestUrl, String keyword) throws IOException {
        URL url = createUrl(requestUrl, keyword);
        String jsonResponse = null;
        jsonResponse = makeHttpRequest(url);

        List<books> books = extractInfoFromJson(jsonResponse);
        Log.e(LOG_TAG, "Program reaches fetchBooksData");
        return books;

    }

    private static  String formatFinalString(String text){
        if(text.charAt(text.length())==','){
            text=text.substring(0,(text.length()-1));
        }
        Log.e(LOG_TAG,text);
        return text;
    }
}
