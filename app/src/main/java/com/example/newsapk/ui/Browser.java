package com.example.newsapk.ui;

import android.nfc.Tag;
import android.os.Handler;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.newsapk.IOnBackPressed;
import com.example.newsapk.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Browser extends Fragment  {

    String link=null;
     public Browser(String link){
         this.link=link;
     }
    public Browser() {
        // Required empty public constructor
    }
    Button b1;

public  static WebView webView;
    EditText editText;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        if (link == null)
            webView.loadUrl("https://google.com");
        else
            webView.loadUrl(link);
        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                // TODO show you progress image
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                // TODO hide your progress image
                super.onPageFinished(view, url);
            }
        });
        setHasOptionsMenu(true);
        return view;

    }
    public static boolean canGoBack(){
        return webView.canGoBack();
    }

    public static void goBack(){
        webView.goBack();
    }
}
