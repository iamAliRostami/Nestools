package com.leon.nestools.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.leon.nestools.R;
import com.leon.nestools.activities.MainActivity;
import com.leon.nestools.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends BaseFragment {

    View root;
    ProgressDialog progressDialog;
    Context context;
    FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        context = getActivity();
        initialize();
        return root;
    }

    @SuppressLint("SetJavaScriptEnabled")
    void initialize() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(getString(R.string.waiting));
        progressDialog.setCancelable(false);
        progressDialog.show();
        binding.webView.setWebChromeClient(new WebChromeClient());
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setBuiltInZoomControls(true);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.loadUrl(getString(R.string.nestools_favorites_url));
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
            ((MainActivity) context).setProgress(progress * 100);

            Log.e("progress ", String.valueOf(progress));
            if (progress >= 70) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }
    }
}