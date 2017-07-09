package ir.kivee.gushiyab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingsActivity extends AppCompatActivity {


    private TextView textViewMessage;
    private Button buttonAccept;
    private Button buttonCancle;


    private String appMessage;


    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        textViewMessage = (TextView) findViewById(R.id.activity_settings_set_message);

        buttonCancle = (Button) findViewById(R.id.activity_settings_cancle);
        buttonAccept = (Button) findViewById(R.id.activity_settings_accept);
        load();


        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewMessage.getText().toString().isEmpty())
                    new SweetAlertDialog(SettingsActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("خطا")
                            .setContentText("لطفا متنی را وارد نمایید")
                            .setConfirmText("قبول")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();

                else if (textViewMessage.getText().toString().length() != 0) {
                    editor.putString("message", textViewMessage.getText().toString());
                    int temp = preferences.getInt("isRunning", -1);
                    if (temp == -1)
                        editor.putInt("isRunning", 0);
                    editor.commit();

                    new SweetAlertDialog(SettingsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("موفق")
                            .setContentText("عملیات با موفقیت انجام شد")
                            .setConfirmText("قبول")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    finish();
                                }
                            }).show();

                }
            }
        });
    }


    private void load() {
        preferences = getSharedPreferences("myPrefrences", Context.MODE_PRIVATE);
        editor = preferences.edit();
        appMessage = preferences.getString("message", null);

        textViewMessage.setText(appMessage);
        editor.commit();
    }


}
