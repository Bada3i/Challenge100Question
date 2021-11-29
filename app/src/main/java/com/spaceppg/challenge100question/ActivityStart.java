package com.spaceppg.challenge100question;


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.Locale;

public class ActivityStart extends AppCompatActivity {

    Button playgame,bu_share, bu_mail, bu_rate;
    Animation trans;
    ImageView img_challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        StartAppSDK.init(this, "211970603", true);

        playgame = (Button) findViewById(R.id.play_game);
        bu_share = (Button) findViewById(R.id.bu_share);
        bu_mail = (Button) findViewById(R.id.bu_email);
        bu_rate = (Button) findViewById(R.id.bu_rate);
        img_challenge =  (ImageView) findViewById(R.id.img_Challenge);

        trans= AnimationUtils.loadAnimation(this, R.anim.trans);
        img_challenge.startAnimation(trans);

        if (Locale.getDefault().getLanguage().equals(new Locale("ar").getLanguage())) {
            img_challenge.setImageResource(R.drawable.title_ar);
        }
        else {
            img_challenge.setImageResource(R.drawable.title_en2);
        }

    }

    public void start(View view) {
        Intent intent = new Intent(ActivityStart.this, MainActivity.class);
        startActivity(intent);
    }


    public void shareApp(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.spaceppg.challenge100question");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Choose App To share Link"));
    }

    public void mailUs(View view) {
        Intent sendemial = new Intent(Intent.ACTION_SEND);
        sendemial.putExtra(Intent.EXTRA_EMAIL, new String[]{"space.ppg@gmail.com"});
        sendemial.putExtra(Intent.EXTRA_SUBJECT,"Your challenge100question Apps");
       // sendemial.setData(Uri.parse("mailto:"));
        sendemial.setType("message/rfc822");
        startActivity(Intent.createChooser(sendemial,"Choose Email Client..."));
    }

    public void rateApp(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.spaceppg.challenge100question")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.spaceppg.challenge100question")));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setMessage(R.string.exit)
                .setIcon(R.drawable.ic_sentiment_dissatisfied_black_24dp)
                .setTitle(R.string.title)
                .setPositiveButton(R.string.yah, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);

                    }
                })
                .setNegativeButton(R.string.na, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

    }


}