package com.example.nol;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import java.util.HashMap;

import static android.content.Context.AUDIO_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class MySoundPlayer {

    public static final int CRACK = R.raw.crack;
    public static final int BROKEN = R.raw.broken;
    public static final int CHICKEN = R.raw.chicken;
    public static final int CORRECT = R.raw.correct;
    public static final int BUTTON_SOUND = R.raw.button_sound;

    private static int ret;
    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    // sound media initialize
    public static void initSounds(Context context) {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        soundPoolMap = new HashMap(2);
        soundPoolMap.put(CRACK, soundPool.load(context, CRACK, 1));
        soundPoolMap.put(BROKEN, soundPool.load(context, BROKEN, 2));
        soundPoolMap.put(CHICKEN, soundPool.load(context, CHICKEN, 3));
        soundPoolMap.put(CORRECT, soundPool.load(context, CORRECT, 1));
        soundPoolMap.put(BUTTON_SOUND, soundPool.load(context, BUTTON_SOUND, 1));


    }

    public static void play(int raw_id) {
        if (soundPoolMap.containsKey(raw_id)) {
            ret = soundPool.play(soundPoolMap.get(raw_id), 1f, 1f, 1, 0, 1.8f);
        }
    }

    public static void play2(int raw_id) {
        if (soundPoolMap.containsKey(raw_id)) {
            ret = soundPool.play(soundPoolMap.get(raw_id), 1f, 1f, 1, 0, 1.0f);
        }
    }
    public static void stop(int raw_id) {
        soundPool.stop(ret);
    }
}
