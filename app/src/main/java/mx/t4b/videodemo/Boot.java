package mx.t4b.videodemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by erikcruz on 8/4/17.
 */

public class Boot extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, VideoGalleryActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}