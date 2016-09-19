package com.example.vineet.news;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //   private Button tap ;
    private ListView list ;
    private String fileContents ;
    Xmlparse xmlparse = new Xmlparse(fileContents);
    private ProgressDialog progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //     tap = (Button) findViewById(R.id.tapbutton);
        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = ((TextView) view.findViewById(R.id.textview)).getText().toString();
                String upp = selected.substring(selected.indexOf("http://"), selected.lastIndexOf(".cms") + 4);
                //     String upp =
                //  Toast toast = Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_LONG);
                //    toast.show();
                //   selected.startsWith("http://");
                Toast toa = Toast.makeText(getApplicationContext(), upp, Toast.LENGTH_LONG);
                toa.show();
                Intent i = new Intent(MainActivity.this, WebpageDisplay.class);
                i.putExtra("URL", upp);
                startActivity(i);

            }
        });


        //    tap.setOnClickListener(new View.OnClickListener() {
        //       @Override
        //        public void onClick(View v) {
        //          Xmlparse xmlparse = new Xmlparse(fileContents);
//                xmlparse.process();
//                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>
//                        (MainActivity.this,R.layout.list_item,xmlparse.getApplications());
//                list.setAdapter(arrayAdapter);

        //     }
        //   });






        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://economictimes.indiatimes.com/rssfeedstopstories.cms");
        //  http://feeds.feedburner.com/ndtvnews-latest");
                //"http://www.thehindu.com/news/?service=rss");
        //"http://zeenews.india.com/rss/india-national-news.xml");
    }


    private class DownloadData extends AsyncTask<String , Void , String > {

        @Override
        protected void onPreExecute() {
            progressbar = (ProgressDialog.show(MainActivity.this, "", " Loading.."));
        }

        @Override
        protected String doInBackground(String... params) {
            fileContents = downloadXML (params[0]);
           if(fileContents == null) {
                Log.d("DownloadData" , "Error in download xml" +fileContents);
            }
          return fileContents;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressbar.dismiss();
            Xmlparse xmlparse = new Xmlparse(fileContents);
            xmlparse.process();
            ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>
                    (MainActivity.this, R.layout.list_item, xmlparse.getApplications());
            list.setAdapter(arrayAdapter);

        }

        private String downloadXML(String urlPath) {
            StringBuilder build = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int responceCode = connection.getResponseCode();
                Log.d("DownloadData"," Responce code is " +responceCode);
                InputStream inp = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inp);
                int charRead;
                char [] inputBuffer = new  char[400];
                while (true) {
                    charRead = reader.read(inputBuffer);
                    if(charRead <= 0) {
                        break;
                    }
                    build.append(String.copyValueOf(inputBuffer , 0 , charRead));
                }
                return build.toString();

            }
            catch (Exception e ) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
