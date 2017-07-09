package ir.kivee.gushiyab;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_CAMERA_REQUEST_CODE = 2;
    private TextView textViewStatus;
    private ImageView imageViewOnOff;
    private ImageView imageViewAboutMe;
    private static final int PERMISSION_SMS_REQUEST_CODE = 1;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public int isWorking = -1;
    private String appMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStatus = (TextView) findViewById(R.id.activity_main_appStatus);
        imageViewOnOff = (ImageView) findViewById(R.id.activity_main_on_off);
        imageViewAboutMe = (ImageView) findViewById(R.id.activity_main_about_me);
        onLoad();
        showPermissionDialog();
        imageViewOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWorking == -1) {
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("خطا")
                            .setContentText("لطفا ابتدا تنظیمات برنامه را انجام دهید")
                            .setConfirmText("صفحه ی تنظیمات")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                    DisableBroadcast();
                } else if (isWorking == 0) {
                    isWorking = 1;
                    EnableBroadcast();
                } else if (isWorking == 1) {
                    isWorking = 0;
                    DisableBroadcast();
                }
                ChangeImageState(isWorking);
                editor.putInt("isRunning", isWorking);
                editor.commit();
            }
        });

        imageViewAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactUs = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(contactUs);


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        onLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void onLoad() {
        preferences = getSharedPreferences("myPrefrences", Context.MODE_PRIVATE);
        editor = preferences.edit();
        isWorking = preferences.getInt("isRunning", -1);
        appMessage = preferences.getString("message", null);
        editor.commit();
        ChangeImageState(isWorking);
    }

    private void showPermissionDialog() {

        if (!SmsReceiver.checkPermission(this)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.READ_SMS},
                    PERMISSION_SMS_REQUEST_CODE);
        }
        if (!OnCallActivity.CheckCameraPermission(this)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA_REQUEST_CODE);
        }
    }


    private void EnableBroadcast() {
        ComponentName receiver = new ComponentName(this, SmsReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void DisableBroadcast() {
        ComponentName receiver = new ComponentName(this, SmsReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }


    private void ChangeImageState(int appState) {
        if (appState == -1) {
            imageViewOnOff.setImageResource(R.drawable.button_disabled);
            textViewStatus.setText("غیر فعال");
            textViewStatus.setTextColor(Color.parseColor("#7f8c8d"));
        }

        if (appState == 0) {
            imageViewOnOff.setImageResource(R.drawable.button_off);
            textViewStatus.setText("خاموش");
            textViewStatus.setTextColor(Color.parseColor("#c0392b"));
        }
        if (appState == 1) {
            imageViewOnOff.setImageResource(R.drawable.button_on);
            textViewStatus.setText("روشن");
            textViewStatus.setTextColor(Color.parseColor("#27ae60"));
        }
    }

}
