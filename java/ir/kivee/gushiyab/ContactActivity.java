package ir.kivee.gushiyab;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class ContactActivity extends AppCompatActivity {

    private ImageView imageViewClose;
    private LinearLayout rateUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        imageViewClose=(ImageView)findViewById(R.id.activity_content_close);
        rateUs=(LinearLayout)findViewById(R.id.activity_contact_rate);

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                final Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

                if (getPackageManager().queryIntentActivities(rateAppIntent, 0).size() > 0){
                    startActivity(rateAppIntent);
                }
                else{
                }
            }
        });

        imageViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
