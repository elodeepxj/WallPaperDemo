package com.jokerpeng.demo.wallpaperdemo.service;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by PengXiaoJie on 2017/6/12.11 32..
 */

public class VideoLiveWallpaper extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new VideoEngine();
    }

    public class VideoEngine extends Engine{
        private MediaPlayer mediaPlayer;

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {

            super.onSurfaceCreated(holder);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setSurface(holder.getSurface());
            try {
                AssetManager assetManager = getApplicationContext().getAssets();
                AssetFileDescriptor assetFileDescriptor = assetManager.openFd("video.mp4");
                mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
                mediaPlayer.setLooping(true);//设置循环
                mediaPlayer.setVolume(0,0);//设置音量
                mediaPlayer.prepare();//准备
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();//开始
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if(visible){
                mediaPlayer.start();
            }else {
                mediaPlayer.pause();
            }
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void setToWallPaper(Context context){
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(context,VideoLiveWallpaper.class));
        context.startActivity(intent);
    }
}
