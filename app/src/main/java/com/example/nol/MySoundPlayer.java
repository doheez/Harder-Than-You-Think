package com.example.nol;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import java.util.HashMap;

import static android.content.Context.AUDIO_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class MySoundPlayer {
   // public static final int BACKGROUNDMUSIC = R.raw.backgroundmusic;
    public static final int COOKIE = R.raw.cookie;
    public static final int CRACK = R.raw.crack;
    public static final int BROKEN = R.raw.broken;
    public static final int CHICKEN = R.raw.chicken;
    public static final int CORRECT = R.raw.correct;
    public static final int BUTTON_SOUND = R.raw.button_sound;
    public static final int CAKE = R.raw.cake;
    public static final int POUR_SOUND = R.raw.pour_sound;
    public static final int TIMEOVER = R.raw.timeover;

    private static int ret;
    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    // sound media initialize
    public static void initSounds(Context context) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).build();

        soundPoolMap = new HashMap(2);
        soundPoolMap.put(CRACK, soundPool.load(context, CRACK, 1));
       // soundPoolMap.put(BACKGROUNDMUSIC, soundPool.load(context, BACKGROUNDMUSIC, 1));
        soundPoolMap.put(BROKEN, soundPool.load(context, BROKEN, 2));
        soundPoolMap.put(CHICKEN, soundPool.load(context, CHICKEN, 3));
        soundPoolMap.put(CORRECT, soundPool.load(context, CORRECT, 1));
        soundPoolMap.put(BUTTON_SOUND, soundPool.load(context, BUTTON_SOUND, 1));
        soundPoolMap.put(COOKIE, soundPool.load(context, COOKIE, 1));
        soundPoolMap.put(CAKE, soundPool.load(context, CAKE, 1));
        soundPoolMap.put(POUR_SOUND, soundPool.load(context, POUR_SOUND, 1));
        soundPoolMap.put(TIMEOVER, soundPool.load(context, TIMEOVER, 1));
    }

    public static void play(int raw_id) {
        if (raw_id == MySoundPlayer.CORRECT){
            ret = soundPool.play(soundPoolMap.get(raw_id), 0.7f, 0.7f, 1, 0, 1.0f);
        }
        else if(raw_id==MySoundPlayer.COOKIE) {
            ret = soundPool.play(soundPoolMap.get(raw_id), 1f, 1f, 1, 0, 1.0f);
        }
       // else if(raw_id==MySoundPlayer.BACKGROUNDMUSIC) {
           // ret = soundPool.play(soundPoolMap.get(raw_id), 1f, 1f, 1, -1, 1.0f);
       // }
        else {
            ret = soundPool.play(soundPoolMap.get(raw_id), 1f, 1f, 1, 0, 1.8f);
        }
    }

    public static void stop(int raw_id) {
        soundPool.stop(ret);
    }
}
