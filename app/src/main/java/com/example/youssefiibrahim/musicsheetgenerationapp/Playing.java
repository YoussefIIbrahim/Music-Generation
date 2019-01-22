package com.example.youssefiibrahim.musicsheetgenerationapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.youssefiibrahim.musicsheetgenerationapp.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Playing extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL = 1000;  // 1 sec
    final static long TIMEOUT = 30000;   // 7 secs
    int progressValue = 0;

    CountDownTimer countDownTimer;

    int index=0, score=0, thisQuestion=0, totalQuestion, correctAnswer;

    ProgressBar progressBar;
    ImageView question_image;
    List<Button> choices = new ArrayList<>();
    TextView txtScore, txtQuestionNum, question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        //Views
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtQuestionNum = (TextView) findViewById(R.id.txtTotalQuestion);
        question_text = (TextView) findViewById(R.id.question_text);
        question_image = (ImageView) findViewById(R.id.question_image);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        choices.add((Button)findViewById(R.id.btnAnswerA));
        choices.add((Button)findViewById(R.id.btnAnswerB));
        choices.add((Button)findViewById(R.id.btnAnswerC));
        choices.add((Button)findViewById(R.id.btnAnswerD));

        for (int i=0; i<choices.size(); i++){
            choices.get(i).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        countDownTimer.cancel();
        if(index < totalQuestion) //still have questions left
        {
            Button clickedButton = (Button)v;
            if(clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {
                //Change correct answer
                score += 10;
                correctAnswer ++;
                showQuestion(++index); //next question
            }
            else {
//                //Chose wrong answer
//                Intent intent = new Intent(this, Done.class);
//                Bundle dataSend = new Bundle();
//                dataSend.putInt("SCORE", score);
//                dataSend.putInt("TOTAL", totalQuestion);
//                dataSend.putInt("CORRECT", correctAnswer);
//                intent.putExtras(dataSend);
//                startActivity(intent);
//                finish();
                showQuestion(++index);
            }

            txtScore.setText(String.format("%d", score));
        }
    }

    private void showQuestion(int index) {
        if(index < totalQuestion)
        {
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d / %d", thisQuestion, totalQuestion));
            progressBar.setProgress(0);
            progressValue=0;
            if(Common.questionList.get(index).getIsImageQuestion().equals("true"))
            {
                //If it's in image
                Picasso.get()
                        .load(Common.questionList.get(index).getQuestion())
                        .into(question_image);
                question_image.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);
            }
            else {
                question_text.setText(Common.questionList.get(index).getQuestion());
                question_image.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);
            }

            for (int j=0; j<Common.questionList.get(index).getAnswers().size(); j++){
                choices.get(j).setText(Common.questionList.get(index).getAnswers().get(j));
            }
            countDownTimer.start();
        }
        else {
            //If it is final question
            Intent intent = new Intent(this, Done.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE", score);
            dataSend.putInt("TOTAL", totalQuestion);
            dataSend.putInt("CORRECT", correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();
        countDownTimer = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long milli) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }
}
