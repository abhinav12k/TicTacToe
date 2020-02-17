package com.example.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 0:yellow 1:red 2: empty

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
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationY(12).rotationBy(3600).setDuration(200);

            for(int[] winningPosition : winningPositions){

                TextView winnerText = (TextView) findViewById(R.id.winnerTextView);
                Button playAgain = (Button) findViewById(R.id.PlayAgainButton);

                if(gameState[winningPosition[0]]==gameState[winningPosition[1]]&&gameState[winningPosition[1]]==gameState[winningPosition[2]]&&gameState[winningPosition[0]]!=2) {   //someone has won

                    //someone has won
                    gameCurrentState=false;

                    String winner="";
                    if (activePlayer == 1)
                        winner = "Yellow";
                    else
                        winner = "Red";

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
    }
}
