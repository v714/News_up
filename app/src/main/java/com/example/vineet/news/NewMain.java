package com.example.vineet.news;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vineet on 8/1/2016.
 */
public class NewMain extends Activity {

    ProgressDialog progressbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String fileContents;
    Xmlparse xmlparse = new Xmlparse(fileContents);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_frontpage);

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://zeenews.india.com/rss/india-national-news.xml");
    }


    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected String doInBackground(String... params) {
            fileContents = downloadXML(params[0]);
            if (fileContents == null) {
                Log.d("DownloadData", "Error in download xml" + fileContents);
            }
            return fileContents;
        }

        @Override
        protected void onPostExecute(String s) {
            progressbar.dismiss();
            Xmlparse xmlparse = new Xmlparse(fileContents);
            xmlparse.process();
            //    ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(NewMain.this,xmlparse.getApplications());
        }


        private String downloadXML(String urlPath) {
            StringBuilder build = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int responceCode = connection.getResponseCode();
                Log.d("DownloadData", " Responce code is " + responceCode);
                InputStream inp = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inp);
                int charRead;
                char[] inputBuffer = new char[400];
                while (true) {
                    charRead = reader.read(inputBuffer);
                    if (charRead <= 0) {
                        break;
                    }
                    build.append(String.copyValueOf(inputBuffer, 0, charRead));
                }
                return build.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
