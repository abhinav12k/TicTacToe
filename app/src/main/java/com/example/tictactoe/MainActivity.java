package com.example.tictactoe;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AlertDialog inputDialog;
//    private ImageView back_arrow;
    private LinearLayout back_arrow_layout;
    private boolean backTouch;
    // 0:yellow; 1:red; 2: empty; 3: computer

    int activePlayer=0;

    int[] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    boolean gameCurrentState=true;
    public void dropin(View view){


        ImageView counter = (ImageView) view;

        Log.i("Image clicked",counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
       if (gameState[tappedCounter]==2&&gameCurrentState) {

           gameState[tappedCounter]=activePlayer;

           counter.setTranslationY(-1000);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.ic_o);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.ic_x);
                activePlayer = 0;
            }

            counter.animate().translationY(12).rotationBy(3600).setDuration(200);

            for(int[] winningPosition : winningPositions){

                TextView winnerText = (TextView) findViewById(R.id.winnerTextView);
                Button playAgain = (Button) findViewById(R.id.PlayAgainButton);

                if(gameState[winningPosition[0]]==gameState[winningPosition[1]]&&gameState[winningPosition[1]]==gameState[winningPosition[2]]&&gameState[winningPosition[0]]!=2) {

                    //someone has won
                    gameCurrentState=false;

                    String winner="";
                    if (activePlayer == 1)
                        winner = "Player 1";
                    else
                        winner = "Player 2";

                    winnerText.setText(winner+" has won");
                    winnerText.setVisibility(View.VISIBLE);
                    playAgain.setVisibility(View.VISIBLE);
                }else if(gameState[0]!=2&&gameState[1]!=2&&gameState[2]!=2&&gameState[3]!=2&&gameState[4]!=2&&gameState[5]!=2&&gameState[6]!=2&&gameState[7]!=2&&gameState[8]!=2){
                    winnerText.setText("Draw");
                    winnerText.setVisibility(View.VISIBLE);
                    playAgain.setVisibility(View.VISIBLE);
                }

            }
        }

    }

    public void playAgain(View view){

        TextView winnerText = (TextView) findViewById(R.id.winnerTextView);
        Button playAgain = (Button) findViewById(R.id.PlayAgainButton);
        winnerText.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0;i<gridLayout.getChildCount();i++){

            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for(int i=0;i<gameState.length;i++){

            gameState[i]=2;
        }

        activePlayer=0;
        gameCurrentState=true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        backTouch=false;
        back_arrow_layout = findViewById(R.id.back_arrow_layout);
        back_arrow_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(backTouch){
                    finish();
                }
                backTouch = true;
                Toast.makeText(MainActivity.this,"Are you sure, you want to Quit!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();



    }
}
