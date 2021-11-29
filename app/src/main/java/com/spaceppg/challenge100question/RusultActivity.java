package com.spaceppg.challenge100question;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.Random;

public class RusultActivity extends AppCompatActivity {


    LinearLayout ly_1;
    Button reStart;
    Animation rotat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rusult);

        StartAppSDK.init(this, "211970603", true);

        ly_1 =(LinearLayout)findViewById(R.id.ly_1);
        TextView resultLabel = (TextView)findViewById(R.id.resultLable);
        TextView highestScoreLable =(TextView)findViewById(R.id.highestScoreLable);
        TextView timeOver =(TextView)findViewById(R.id.timeOver);
        reStart = (Button)findViewById(R.id.reStart);

        reStart = (Button) findViewById(R.id.reStart);
        rotat= AnimationUtils.loadAnimation(this, R.anim.rotat);
        reStart.startAnimation(rotat);
        reStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), ActivityStart.class);
               startActivity(intent);
            }
        });
/*
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1813751562280462/5066602695");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        RusultActivity.this.mInterstitialAd.isLoaded();
                        RusultActivity.this.mInterstitialAd.show();
                    }
                }, 0);
            }}  );
*/


        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT",0);

        Intent intent = getIntent();
        String over = intent.getStringExtra("over");
        timeOver.setText(over);

        SharedPreferences settings = getSharedPreferences("quizApp", Context.MODE_PRIVATE);
        int highestScore =settings.getInt("highestScore",0);

        if(score > highestScore){
            highestScoreLable.setText("");
            highestScoreLable.setText(""+score);
        }else {
            highestScoreLable.setText("");
            highestScoreLable.setText(""+highestScore);
        }
        resultLabel.setText(score + " / 100");

        //Update total score
        SharedPreferences.Editor editor = settings.edit();
        if(score > highestScore){
            editor.putInt("highestScore", score);
        }else {
            editor.putInt("highestScore",highestScore);
        }
        editor.putString("over",over);
        editor.commit();
    }

    protected void onResume() {
        super.onResume();
        Random rn = new Random();
        int bg = rn.nextInt(3);

        int red = rn.nextInt(255);
        int green = rn.nextInt(255);
        int bule = rn.nextInt(255);
        ly_1.setBackgroundColor(Color.argb(50, red, green, bule));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ActivityStart.class);
        startActivity(intent);
    }
}
