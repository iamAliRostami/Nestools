package com.leon.nestools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.leon.nestools.databinding.SplashActivityBinding;

public class SplashActivity extends AppCompatActivity {

    SplashActivityBinding binding;
    View view;
    private boolean splashLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = SplashActivityBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        initializeSplash();
    }

    void initializeSplash() {
        if (!splashLoaded) {
            setContentView(view);
            initialize();
            startSplash();
        } else {
            Intent goToLoginActivity = new Intent(SplashActivity.this, MainActivity.class);
            goToLoginActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToLoginActivity);
            finish();
        }
    }

    void initialize() {
        int splashResourceId = R.drawable.image_splash;
        binding.imageViewSplashScreen.setImageResource(splashResourceId);
        binding.shimmerViewContainer.startShimmer();
    }

    void startSplash() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    splashLoaded = true;
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }

}