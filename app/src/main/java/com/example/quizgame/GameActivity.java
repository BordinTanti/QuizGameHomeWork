package com.example.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizgame.model.WordItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mQuestionImageView;
    private Button[] mButtons = new Button[4];
    private int round , count ;
    private String mAnswerWord ;
    private Random mRandom ;
    private List<WordItem> mItemsList ;
    private TextView point ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        point = findViewById(R.id.PointTextView);
        mItemsList = new ArrayList<>(Arrays.asList(WordListActivity.items));
        mQuestionImageView = findViewById(R.id.question_image_View);
        mButtons[0] = findViewById(R.id.choice_1_button);
        mButtons[1] = findViewById(R.id.choice_2_button);
        mButtons[2] = findViewById(R.id.choice_3_button);
        mButtons[3] = findViewById(R.id.choice_4_button);

        //MyButtonListener listener = new MyButtonListener();
        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);
        //สุ่ม index คำศัพท์
        mRandom = new Random();
        /*String score = String.format(
                Locale.getDefault(),"%d คะแนน ",count
        );
        point.setText(score);*/
        setPoint();
        newQuiz();

    }

    private void newQuiz() {
        int answerIndex = mRandom.nextInt(mItemsList.size());
        List<WordItem> newList = mItemsList;
        //เข้าถึง worditem ตาม index ที่สุ่มได้
        WordItem item = mItemsList.get(answerIndex);
        //แสดงรูปคำถาม
        mQuestionImageView.setImageResource(item.imageResId);

        mAnswerWord = item.word;
        //สุ่มตำแหน่งปุ่มที่แสดงคำตอบ
        int randomButton = mRandom.nextInt(4);
        mButtons[randomButton].setText(item.word);
        //ลบ wordItem ที่เป็นคำตอบออกจากlist
        mItemsList.remove(item);
        //เอา List ที่เหลือมา shuffle
        Collections.shuffle(mItemsList);

        for(int i = 0 ; i < 4 ; i++){
            if(i == randomButton){
                continue;
            }
            mButtons[i].setText(mItemsList.get(i).word);

        }
        mItemsList = new ArrayList<>(Arrays.asList(WordListActivity.items));
        //newList = mItemsList;
    }
    public void finishGame(){
        if(round == 5){
            new AlertDialog.Builder(GameActivity.this)
                    .setTitle("สรุปผล")
                    .setMessage("คุณได้ " + count +"คะแนน\n\nต้องการเล่นเกมใหม่หรือไม่")
                    .setNegativeButton("No",(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            round = 0;
                            count = 0;
                            finish();
                        }
                    }))
                    .setPositiveButton("yes",(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            count = 0;
                            round = 0 ;
                            /*String score = String.format(
                                    Locale.getDefault(),"%d คะแนน ",count
                            );
                            point.setText(score);*/
                            setPoint();
                            newQuiz();
                        }
                    })).show();
        }
        else{
            newQuiz();
        }
    }
    public void setPoint (){
        String score = String.format(
                Locale.getDefault(),"%d คะแนน ",count
        );
        point.setText(score);
    }
    @Override
    public void onClick(View view) {
        Button b = findViewById(view.getId());
        String buttonText = b.getText().toString();

        if(buttonText.equals(mAnswerWord)){
            Toast.makeText(GameActivity.this,"correct",Toast.LENGTH_SHORT).show();

            round++;
            count++;
            /*String score = String.format(
                    Locale.getDefault(),"%d คะแนน ",count
            );
            point.setText(score);*/
            setPoint();
        }else{
            Toast.makeText(GameActivity.this,"wrong",Toast.LENGTH_SHORT).show();
            round++ ;
        }
        finishGame();
    }

    /*private static class MyButtonListener implements  View.OnClickListener{
        @Override
        public void onClick(View view) {
            int id = view.getId();
        }
    }*/
}