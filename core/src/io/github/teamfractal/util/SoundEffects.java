package io.github.teamfractal.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by user on 28/02/2017.
 */
public class SoundEffects {

    public static void click() {
        Gdx.audio.newSound(Gdx.files.internal("audio/click.ogg")).play();
    }

    public static void chime() {
        Gdx.audio.newSound(Gdx.files.internal("audio/chime.ogg")).play();
    }

    public static void bad() {
        Gdx.audio.newSound(Gdx.files.internal("audio/notificationbad.ogg")).play();
    }

    public static void pulse() {
        Gdx.audio.newSound(Gdx.files.internal("audio/pulse.ogg")).play();
    }
}
