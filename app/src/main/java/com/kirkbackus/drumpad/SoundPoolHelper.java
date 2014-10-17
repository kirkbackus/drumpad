package com.kirkbackus.drumpad;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kirk on 10/16/2014.
 */
public class SoundPoolHelper extends SoundPool {
    private Set<Integer> mLoaded;
    private Context mContext;

    public SoundPoolHelper(int maxStreams, Context context) {
        this(maxStreams, AudioManager.STREAM_MUSIC, 0, context);
    }

    public SoundPoolHelper(int maxStreams, int streamType, int sourceQuality, Context context) {
        super(maxStreams, streamType, sourceQuality);

        mContext = context;
        mLoaded = new HashSet<Integer>();

        setOnLoadCompleteListener(new HandleOnLoadComplete());
    }

    private class HandleOnLoadComplete implements OnLoadCompleteListener {
        @Override public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
            mLoaded.add(sampleId);
            System.out.println("LOADED SOUND: "+sampleId);
        }

    }

    public void play(int soundId) {
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;

        if (mLoaded.contains(soundId)) {
            Log.e("Test", "Played Sound: "+soundId);
            play(soundId, volume, volume, 1, 0, 1f);
        } else {
            Log.e("Test", "FAILURE: "+soundId);
        }
    }
}
