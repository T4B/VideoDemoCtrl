package mx.t4b.videodemo;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

import mx.t4b.videodemo.model.VideoPreferences;

/**
 * Created by erikcruz on 8/7/17.
 */

public class FullscreenDialogFragment extends DialogFragment {
    private static final String ARG_VIDEO_ID = "arg_video_id";

    private VideoView videoView;
    private VideoPreferences preferences;
    private int resourceVideoID;

    public FullscreenDialogFragment newInstance(int resourceVideoID) {
        FullscreenDialogFragment frag = new FullscreenDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_VIDEO_ID, resourceVideoID);
        frag.setArguments(args);
        return frag;
    }

    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment
        View view = inflater.inflate(R.layout.dialog_fragment_fullscreen, container, false);
        videoView = (VideoView) view.findViewById(R.id.videoView);
//        MediaController mediaController = new MediaController(getActivity());
//        mediaController.setPrevNextListeners(null, null);
//        mediaController.setOnDragListener(null);
//        videoView.setMediaController(mediaController);
        preferences = new VideoPreferences(getActivity().getApplicationContext());
        setVideo();
        return view;
    }

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void setVideo() {
        resourceVideoID = getArguments().getInt(ARG_VIDEO_ID);

        // Get variables
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + resourceVideoID;
        Uri videoUri = Uri.parse(videoPath);

        videoView.setVideoURI(videoUri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.setBackground(null);
                mediaPlayer.start();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switch (resourceVideoID) {
                    case R.raw.ctrl_plus_01:
                        preferences.setVideo1_seen(true);
                        break;
                    case R.raw.grupo_map:
                        preferences.setVideo2_seen(true);
                        break;
                }
                dismiss();
            }
        });
    }
}
