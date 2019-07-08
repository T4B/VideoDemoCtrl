package mx.t4b.videodemo.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by erikcruz on 8/7/17.
 */

public class VideoPreferences {
    private static final String NAME_PREFERENCES = "video_pref";
    private static final String PREF_VIDEO1_SEEN = "pref_video1_seen";
    private static final String PREF_VIDEO2_SEEN = "pref_video2_seen";
    private static final String PREF_VIDEO3_SEEN = "pref_video3_seen";
    private SharedPreferences sharedPref;

    private boolean video1_seen;
    private boolean video2_seen;
    private boolean video3_seen;

    public VideoPreferences(Context context) {
        sharedPref = context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE);
    }

    public boolean isVideo1_seen() {
        return sharedPref.getBoolean(PREF_VIDEO1_SEEN, false);
    }

    public void setVideo1_seen(boolean video1_seen) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PREF_VIDEO1_SEEN, video1_seen);
        editor.apply();
    }

    public boolean isVideo2_seen() {
        return sharedPref.getBoolean(PREF_VIDEO2_SEEN, false);
    }

    public void setVideo2_seen(boolean video2_seen) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PREF_VIDEO2_SEEN, video2_seen);
        editor.apply();
    }

    public boolean isVideo3_seen() {
        return sharedPref.getBoolean(PREF_VIDEO3_SEEN, false);
    }

    public void setVideo3_seen(boolean video3_seen) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(PREF_VIDEO3_SEEN, video3_seen);
        editor.apply();
    }

    // TODO: add when they are 3 videos
    public boolean isAllVideosSeen() {
        if (isVideo1_seen() && isVideo2_seen() /*&& isVideo3_seen()*/) {
            return true;
        }
        return false;
    }
}
