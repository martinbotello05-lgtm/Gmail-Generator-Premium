
package com.martin.gmailgenerator;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        web = new WebView(this);
        setContentView(web);

        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        web.setWebViewClient(new WebViewClient());

        web.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void copyText(String text) {
                runOnUiThread(() -> {
                    ClipboardManager clipboard =
                        (ClipboardManager) getSystemService(
                            Context.CLIPBOARD_SERVICE);

                    clipboard.setPrimaryClip(
                        ClipData.newPlainText("Gmail", text));

                    Toast.makeText(
                        MainActivity.this,
                        "Copiado correctamente",
                        Toast.LENGTH_SHORT).show();
                });
            }
        }, "Android");

        web.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
