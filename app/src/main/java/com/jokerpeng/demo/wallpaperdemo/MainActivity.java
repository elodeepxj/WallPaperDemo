package com.jokerpeng.demo.wallpaperdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jokerpeng.demo.wallpaperdemo.service.VideoLiveWallpaper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoLiveWallpaper.setToWallPaper(MainActivity.this);
            }
        });
    }
}
