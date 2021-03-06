package comalexpolyanskyi.github.englishtest;

/**
 * Created by Алексей on 26.11.2014.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import comalexpolyanskyi.github.englishtest.utils.VkOAuthHelper;


public class VkLoginActivity extends Activity implements VkOAuthHelper.Callbacks {

    private static final String TAG = VkLoginActivity.class.getSimpleName();

    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vk_login);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebViewClient(new VkWebViewClient());
        mWebView.loadUrl(VkOAuthHelper.AUTORIZATION_URL);
    }

    @Override
    public void onError(Exception e) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(e.getMessage())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    private class VkWebViewClient extends WebViewClient {

        public VkWebViewClient() {
            super();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showProgress();
            view.setVisibility(View.INVISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (VkOAuthHelper.proceedRedirectURL(VkLoginActivity.this, url, VkLoginActivity.this)) {
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.setVisibility(View.VISIBLE);
            dismissProgress();
            Log.d(TAG, "error " + failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            view.setVisibility(View.VISIBLE);
            dismissProgress();
        }

    }

    private void dismissProgress() {
        findViewById(android.R.id.progress).setVisibility(View.GONE);
    }

    private void showProgress() {
        findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
    }

}