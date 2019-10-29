package com.litesnap.open.exoplayerffmpeg;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ffmpeg.FfmpegLibrary;
import com.google.android.exoplayer2.ext.opus.OpusLibrary;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;

public class ExoPlayerUtil {
    private static SimpleExoPlayer mPlayer;

    public static SimpleExoPlayer getPlayer(Context context){
        if (mPlayer == null){
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(context);
            renderersFactory.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON);

            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    context
                    , renderersFactory
                    , new DefaultTrackSelector()
                    , new DefaultLoadControl()
            );
        }
        return mPlayer;
    }

    public static MediaSource newMediaSource(Context context, Uri uri){
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, context.getPackageName());
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        return videoSource;
    }
}
