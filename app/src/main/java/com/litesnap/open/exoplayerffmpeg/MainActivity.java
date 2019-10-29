package com.litesnap.open.exoplayerffmpeg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION = 1;
    private static final int FILTER_FILE = 2;
    private SimpleExoPlayer mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayer = ExoPlayerUtil.getPlayer(this);

        final String[] peris = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AndroidSystem.checkPermissionAllGranted(MainActivity.this, peris)) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            peris,
                            PERMISSION
                    );
                }else {
                    AndroidSystem.categoryOpenableo(MainActivity.this, FILTER_FILE);
                }
            }
        });
    }

    private void play(Uri uri){
        MediaSource source = ExoPlayerUtil.newMediaSource(this, uri);
        mPlayer.prepare(source);
        mPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILTER_FILE && resultCode == Activity.RESULT_OK && data != null){
            Uri uri = data.getData();
            play(uri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION){
            boolean isAllGranted = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted){
                AndroidSystem.categoryOpenableo(MainActivity.this, FILTER_FILE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }
}
