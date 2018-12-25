package com.example.yaswanth.newsapp;

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

public class NewsUtils {
    private static final String LOG_TAG = "QueryUtils";
    private static final int READ_TIME_OUT = 10000;
    private static final int CONNECT_TIME_OUT = 15000;
    private static final int RESPONSE_CODE = 200;

    private NewsUtils() {

    }

    //for Converting given specified url(String) to url
    private static URL createURL(String newsUrl) {
        URL url = null;
        try {
            url = new URL(newsUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "URL NOT VALID", e);
        }
        return url;
    }

    //for making HTTP request to the server with the given url

    private static String makeHTTPrequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIME_OUT);
            urlConnection.setConnectTimeout(CONNECT_TIME_OUT);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "data Not Found" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IO Exception", e);
        }

        return jsonResponse;
    }

    //for reading the input stream which came from web
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return stringBuilder.toString();
    }

    //for fetching JSON
    public static List<NewsItems> fetchJSon(String requestedURL) {
        URL url = createURL(requestedURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPrequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "NO JSON", e);
        }

        return extractJSonData(jsonResponse);
    }

    //for extracting required info from entire JSon
    private static List<NewsItems> extractJSonData(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<NewsItems> newsItems = new ArrayList<>();
        String authorName;
        try {

            JSONObject rootJsonObject = new JSONObject(jsonResponse);
            JSONObject rootObject = rootJsonObject.getJSONObject("response");
            JSONArray resultArray = rootObject.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject currentJSonObject = resultArray.getJSONObject(i);
                String mainNews = currentJSonObject.getString("webTitle");
                String category = currentJSonObject.getString("sectionName");
                String timeDate = currentJSonObject.getString("webPublicationDate");
                String webUrl = currentJSonObject.getString("webUrl");
                timeDate = timeDate.replaceAll("[a-zA-Z]", " ");
                try {
                    JSONArray tagsArray = currentJSonObject.getJSONArray("tags");
                    JSONObject firstTag = tagsArray.getJSONObject(0);
                    authorName = firstTag.getString("webTitle");
                } catch (Exception e) {
                    authorName = "Anonymous";
                }
                newsItems.add(new NewsItems(mainNews, authorName, category, timeDate, webUrl));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Json Exception", e);
        }
        return newsItems;
    }


}

