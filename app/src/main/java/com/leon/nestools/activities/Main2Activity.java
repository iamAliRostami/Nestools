package com.leon.nestools.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.nestools.R;
import com.leon.nestools.databinding.Main2ActivityBinding;

public class Main2Activity extends AppCompatActivity {

    Main2ActivityBinding binding;
    ProgressDialog progressDialog;
    Context context;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Main2ActivityBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        initialize();
    }

    @SuppressLint("SetJavaScriptEnabled")
    void initialize() {
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getString(R.string.waiting));
        progressDialog.setCancelable(false);
        progressDialog.show();
        binding.webView.setWebChromeClient(new WebChromeClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.loadUrl(getString(R.string.nestools_url));
    }

    private class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
            Log.e("error", description);
            Toast.makeText(context, getString(R.string.error).concat(" : ")
                    .concat(getString(R.string.error_IO)), Toast.LENGTH_SHORT).show();
        }
    }

    class WebChromeClient extends android.webkit.WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            setTitle(getString(R.string.waiting));
            setProgress(progress * 100);
            Log.e("progress ", String.valueOf(progress));
            if (progress >= 70) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            if (progress == 100)
                setTitle(R.string.app_name);
        }
    }
}