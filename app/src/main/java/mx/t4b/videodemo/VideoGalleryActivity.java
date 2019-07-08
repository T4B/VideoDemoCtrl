package mx.t4b.videodemo;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import mx.t4b.videodemo.model.VideoPreferences;

public class VideoGalleryActivity extends AppCompatActivity implements View.OnClickListener {
    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private Button btnExit;
    private ImageView ivVideo01, ivVideo02, ivVideo03;
    private ImageView ivPlayVideo01, ivPlayVideo02;
    private VideoPreferences preferences;
    private ActivityManager activityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the app into full screen mode
        getWindow().getDecorView().setSystemUiVisibility(flags);

        setContentView(R.layout.activity_video_gallery);

        setAdminDevice();
        setUI();

        setPreviewVideo(ivVideo01, R.raw.grupo_map);
        setPreviewVideo(ivVideo02, R.raw.ctrl_plus_01);
//        setPreviewVideo(ivVideo03, R.raw.jellyfish);

        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);


        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (preferences.isAllVideosSeen()) {
                    if (activityManager.isInLockTaskMode()) {
                        stopLockTask();
                    }
                    finish();
//                    stopLockTask();
                } else {
                    Toast.makeText(getApplicationContext(), "Es necesario ver todos los videos",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        preferences = new VideoPreferences(getApplicationContext());

        // Delete owner app
        /*DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        if (dpm.isDeviceOwnerApp(getPackageName())) {
            dpm.clearDeviceOwnerApp(getPackageName());
        }*/

        if (preferences.isAllVideosSeen()) {
            stopLockTask();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(flags);
    }

    private void setPreviewVideo(ImageView imageView, final int videoResourceId) {
        String videoPath = "android.resource://" + getPackageName() + "/" + videoResourceId;
        Uri videoUri = Uri.parse(videoPath);

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, videoUri);
        Bitmap thumbnail = retriever.getFrameAtTime(5000);
        imageView.setImageBitmap(thumbnail);
    }


    private void setAdminDevice() {
        // get policy manager
        DevicePolicyManager myDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        // get this app package name
        ComponentName mDPM = new ComponentName(this, MyAdmin.class);

        if (myDevicePolicyManager.isDeviceOwnerApp(this.getPackageName())) {
            // get this app package name
            String[] packages = {this.getPackageName()};
            // mDPM is the admin package, and allow the specified packages to lock task
            myDevicePolicyManager.setLockTaskPackages(mDPM, packages);
            startLockTask();
        } else {
            Toast.makeText(getApplicationContext(), "Not owner", Toast.LENGTH_LONG).show();
        }
    }

    private void setUI() {
        ivVideo01 = (ImageView) findViewById(R.id.iv_video01);
        ivVideo02 = (ImageView) findViewById(R.id.iv_video02);
        ivVideo03 = (ImageView) findViewById(R.id.iv_video03);
        ivPlayVideo01 = (ImageView) findViewById(R.id.iv_play_video01);
        ivPlayVideo02 = (ImageView) findViewById(R.id.iv_play_video02);
        btnExit = (Button) findViewById(R.id.btnExit);

        ivPlayVideo01.setOnClickListener(this);
        ivPlayVideo02.setOnClickListener(this);
    }


    public void showVideoFullscreen(int resourceVideoID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FullscreenDialogFragment newFragment = new FullscreenDialogFragment().newInstance(resourceVideoID);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play_video01:
                showVideoFullscreen(R.raw.grupo_map);
                break;
            case R.id.iv_play_video02:
                showVideoFullscreen(R.raw.ctrl_plus_01);
                break;
        }
    }
}
