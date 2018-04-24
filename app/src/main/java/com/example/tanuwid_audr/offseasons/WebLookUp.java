package com.example.tanuwid_audr.offseasons;

/**
 * Created by JERVEY_SAMU on 4/21/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class WebLookUp extends Activity {
    private String imports;
    private EditText urlText;
    private Button goButton;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // ...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        Bundle bundleObject = getIntent().getExtras();
        imports = (String) bundleObject.getSerializable("website");

        //Extract data from bundle
        final String website = imports;

        // Get a handle to all user interface elements
        urlText = (EditText) findViewById(R.id.EditTextURL);
        urlText.setText(website);
        goButton = (Button) findViewById(R.id.URLSearchButton);
        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(website);

        //intercept URL loading and load in widget
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

        });

        // Set button to open browser
        goButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                webView.loadUrl(urlText.getText().toString());
            }
        });
        urlText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    webView.loadUrl(urlText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }


    //the back key navigates back to the previous web page
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void onPause() {
        super.onPause();
        Toast toast = Toast.makeText(this, "WebLookUp Finished", Toast.LENGTH_SHORT);
        toast.show();
    }
}


