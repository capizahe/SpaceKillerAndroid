package com.movil.main;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class SoundEffects {

    private static SoundPool soundPool;
    private static int laser_sound,meteor_explote_sound;
    private static Context context_;

    public static  void laserShotSound(){
        soundPool.play(laser_sound,1,1,0,0,1);
    }
    public static  void meteorExplosionSound(){
        soundPool.play(meteor_explote_sound,1,1,0,0,1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void loadSounds(Context context){
        context_ = context;
        if(checkVersion()){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        laser_sound = soundPool.load(context_,R.raw.lasershot,1);
        meteor_explote_sound = soundPool.load(context_, R.raw.meteor_explosion,2);

    }

    private static boolean checkVersion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return true;
        }else{
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);
        }
        return false;
    }

}
