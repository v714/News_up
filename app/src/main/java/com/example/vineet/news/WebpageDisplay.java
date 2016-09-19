package com.example.vineet.news;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebpageDisplay extends Activity {

    private static final String TAG = "Main";
    private WebView webview;
    private ProgressDialog progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.webview);

        this.webview = (WebView) findViewById(R.id.webView);

        //   WebSettings settings = webview.getSettings();
        //   settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(WebpageDisplay.this, "WebView Example", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Log.e(TAG, "Error: " + description);
//                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
//                alertDialog.setTitle("Error");
//                alertDialog.setMessage(description);
//                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        return;
//                    }
//                });
//                alertDialog.show();
//            }
        });
        Uri uri = getIntent().getData();
        Uri url = Uri.parse(getIntent().getStringExtra("URL"));
        //    Toast.makeText(getBaseContext(), "UUUUUUURRRRLLL" + url, Toast.LENGTH_LONG).show();
        webview.loadUrl(String.valueOf(url));
    }
}

//        Uri uri = this.getIntent().getData();
//        try {
//  URL url = new URL(uri.getScheme(), uri.getHost(), uri.getPath());
//            webView.loadUrl(uri.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
