package ir.kivee.gushiyab;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


@SuppressWarnings("deprecation")
public class OnCallActivity extends AppCompatActivity {


    private ImageView exit;


    private MediaPlayer player;
    private static Camera cam = null;
    Camera.Parameters params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_call);

        exit = (ImageView) findViewById(R.id.activity_oncal_exit);
        player = MediaPlayer.create(OnCallActivity.this, R.raw.makhlut);
        player.setLooping(true);
        player.setVolume(100, 100);
        AudioManager audio = (AudioManager) OnCallActivity.this.getSystemService(OnCallActivity.this.AUDIO_SERVICE);
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        player.start();


        if (OnCallActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            cam = Camera.open();
            params = cam.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            cam.setParameters(params);
            cam.startPreview();
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (OnCallActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    cam.stopPreview();
                    cam.release();
                }
                player.stop();
                finish();
            }
        });

    }

    public static boolean CheckCameraPermission(final Context context) {
        //
        return ActivityCompat.checkSelfPermission
                (context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }


}
