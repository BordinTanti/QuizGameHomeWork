package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizgame.model.WordItem;
import com.google.gson.Gson;

public class WordDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        Intent i = getIntent();
        String itemJson = i.getStringExtra("item");
        WordItem item = new Gson().fromJson(itemJson, WordItem.class);

       /* String word = i.getStringExtra("word");
        int image = i.getIntExtra("image",0);*/

        ImageView Iv = findViewById(R.id.image_view);
        TextView Tv = findViewById(R.id.word_text_view);

        Iv.setImageResource(item.imageResId);
        Tv.setText(item.word);
    }
}