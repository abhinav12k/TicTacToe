package com.example.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button load_game;
    private Button with_bot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getWindow().setFlags(

                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

        );

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        load_game = findViewById(R.id.load_game_btn);

        load_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        with_bot = findViewById(R.id.real_time_game_btn);
        with_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent botIntent = new Intent(StartActivity.this,playWithBot.class);
                startActivity(botIntent);
            }
        });

    }
}