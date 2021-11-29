package com.spaceppg.challenge100question;


import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout ly_1;

    private TextView countlabel, questionLabel , timeDown , timeDownName;
    private Button answerBth1, answerBth2, answerBth3, answerBth4;
    private ImageButton bu_audio;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QuIZ_COUNT = 100;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    MediaPlayer mediaPlayer;
    CountDownTimer yourCountDownTimer;

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String appKey = "94ce4ce9a4ed2972cc3439fcd901eeed316a4bdc8ba3127c";
        Appodeal.disableLocationPermissionCheck();
        //Appodeal.setTesting(true);
        Appodeal.initialize(this, appKey,  Appodeal.BANNER );
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.show(this, Appodeal.BANNER_VIEW);

        mediaPlayer  = MediaPlayer.create(MainActivity.this,R.raw.music);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        ly_1 =(LinearLayout)findViewById(R.id.ly_1);

        countlabel = (TextView) findViewById(R.id.countLabel);
        questionLabel = (TextView) findViewById(R.id.questionLabel);
        timeDownName = (TextView) findViewById(R.id.timeDownName);
        answerBth1 = (Button) findViewById(R.id.answerBth1);
        answerBth2 = (Button) findViewById(R.id.answerBth2);
        answerBth3 = (Button) findViewById(R.id.answerBth3);
        answerBth4 = (Button) findViewById(R.id.answerBth4);
        bu_audio = (ImageButton) findViewById(R.id.audio);

        timeDown = (TextView) findViewById(R.id.timeDown);
        yourCountDownTimer= new CountDownTimer(1202000, 1000) {
            @Override
            public void onTick(long l) {
                int seconds = (int) (l / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                timeDown.setText("" + String.format("%02d", minutes)+ ":" + String.format("%02d", seconds));
                if( minutes < 2)
                {
                    timeDown.setTextColor(Color.parseColor("#FF0000"));
                    timeDownName.setTextColor(Color.parseColor("#FF0000"));
                }
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), RusultActivity.class);
                intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                intent.putExtra("over","Time is over ...");
                startActivity(intent);
            }
        }.start();

        for (int i = 0; i < quizDataAr.quizData.length; i++) {
                ArrayList<String> tmpArray = new ArrayList<>();
                tmpArray.add(quizDataAr.quizData[i][0]); // question
                tmpArray.add(quizDataAr.quizData[i][1]); // Right Answer
                tmpArray.add(quizDataAr.quizData[i][2]); // Choice1
                tmpArray.add(quizDataAr.quizData[i][3]); // Choice2
                tmpArray.add(quizDataAr.quizData[i][4]); // Choice3
                quizArray.add(tmpArray);
        }showNextQuiz();


    }

    public void playStop(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            bu_audio.setImageResource(R.drawable.ic_volume_off_black_24dp);

        } else {
            mediaPlayer.start();
            bu_audio.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }
    }

    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }

    public void onPause(){
        mediaPlayer.pause();
        super.onPause();
    }

    private void showNextQuiz() {
        countlabel.setText( "" + quizCount);

        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randomNum);

        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        quiz.remove(0);
        Collections.shuffle(quiz);

        answerBth1.setText(quiz.get(0));
        answerBth2.setText(quiz.get(1));
        answerBth3.setText(quiz.get(2));
        answerBth4.setText(quiz.get(3));

        quizArray.remove(randomNum);
    }

     public void checkAnswer (View view) {
         Button answerBtu = (Button) findViewById(view.getId());
         String btnText = answerBtu.getText().toString();
         final Button b = (Button) view;

         if (btnText.equals(rightAnswer)) {
             rightAnswerCount++;

             b.setBackgroundResource(R.drawable.button_green);

             handler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     b.setBackgroundResource(R.drawable.button_animation);
                     checkQuizCount();
                 }
             }, 500);
             return;
         }else if (btnText !=rightAnswer){
             b.setBackgroundResource(R.drawable.button_red);

             handler.postDelayed(new Runnable() {
                 @Override
                 public void run() {
                     b.setBackgroundResource(R.drawable.button_animation);
                     checkQuizCount();
                 }
             }, 500);
             return;
         }
     }
     
     public void checkQuizCount(){
         if (quizCount == QuIZ_COUNT) {
             yourCountDownTimer.cancel();
             Intent intent = new Intent(getApplicationContext(), RusultActivity.class);
             intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
             startActivity(intent);
         } else {
             quizCount++;
             showNextQuiz();
         }
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
}
