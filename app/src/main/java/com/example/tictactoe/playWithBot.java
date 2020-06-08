package com.example.tictactoe;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class playWithBot extends AppCompatActivity {

    private ImageView back_arrow, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    private boolean backTouch;
    private Context mContext;
    // 0:yellow; 1:red(Bot); 2: empty; 3: computer

    //0--> human
    //1 --> bot

    //first turn human
    int activePlayer = 0;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    boolean gameCurrentState = true;

    public void dropin(ImageView image) {

        //first turn is of human
        Log.i("Image clicked", image.getTag().toString());
        int tappedCounter = Integer.parseInt(image.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameCurrentState) {
            gameState[tappedCounter] = activePlayer;      //human

            image.setTranslationY(-1000);

            image.setImageResource(R.drawable.ic_o);
            activePlayer = 1;   //computer turn
            image.animate().translationY(12).rotationBy(3600).setDuration(200);
            image.setClickable(false);
            //function to obtain answer from computer and put in the right place

            checkWin();
            if (gameCurrentState)
                getBotMove(tappedCounter);      //bot


        }

//        if (!gameCurrentState) {
//            boolean isFinished = false;
//            int count = 1;
//            for (int i = 0; i < 9; i++) {
//                if (gameState[i] != 2) {
//                    System.out.println(gameState[i]);
//                    isFinished = true;
//                    count++;
//                }
//            }
//            if (isFinished && count == 9) {
//                //check again for win!!
//
//                if (gameState[0] == gameState[1] && gameState[1] == gameState[2] || gameState[3] == gameState[4] && gameState[4] == gameState[5] || gameState[6] == gameState[7] && gameState[7] == gameState[8] || gameState[0] == gameState[3] && gameState[3] == gameState[6] || gameState[1] == gameState[4] && gameState[4] == gameState[7] || gameState[2] == gameState[5] && gameState[5] == gameState[8] || gameState[0] == gameState[4] && gameState[4] == gameState[8] || gameState[2] == gameState[4] && gameState[4] == gameState[6]) {
//                    Toast.makeText(mContext, "Somebody wins!!" + activePlayer, Toast.LENGTH_SHORT).show();
//                }
//                checkWin();
//            }

//        }
    }

    private void getBotMove(int tappedCounter) {
        if (activePlayer == 1 && gameCurrentState) {
            Log.d("Bot Activity", "getting bot move");
            Random rand = new Random();

            int randomTag = rand.nextInt(9);
            Log.d("Bot Activity", String.valueOf(randomTag));

            while (randomTag == tappedCounter) {
                randomTag = rand.nextInt(9);
                Log.d("Bot Activity", "inside while loop for same tapped counter: " + randomTag);
            }

            //getting an empty cell
            while (gameState[randomTag] != 2) {
                randomTag = rand.nextInt(9);
                Log.d("Bot Activity", "inside while loop for empty location: " + randomTag);
            }


            int pos = randomTag + 1;
            Log.d("Play with bot", "random tag: " + pos);
            String id = "R.id.imageView" + pos;
            Log.d("Play with bot", "random pos id: " + id);

            ImageView currentPos = getImage(id);
            gameState[randomTag] = activePlayer;      //bot

            Log.d("Play with bot", " inside bot move: " + currentPos);

            currentPos.setTranslationY(-1000);
            currentPos.setImageResource(R.drawable.ic_x);
            activePlayer = 0;   //human
            currentPos.animate().translationY(12).rotationBy(3600).setDuration(200);
            currentPos.setClickable(false);
            checkWin();

        }
    }

    public ImageView getImage(String imagePos) {

        switch (imagePos) {

            case "R.id.imageView1":
                return imageView1;

            case "R.id.imageView2":
                return imageView2;

            case "R.id.imageView3":
                return imageView3;

            case "R.id.imageView4":
                return imageView4;

            case "R.id.imageView5":
                return imageView5;

            case "R.id.imageView6":
                return imageView6;

            case "R.id.imageView7":
                return imageView7;

            case "R.id.imageView8":
                return imageView8;

            case "R.id.imageView9":
                return imageView9;

            default:
                return null;
        }

    }


    public void checkWin() {

        for (int[] winningPosition : winningPositions) {

            TextView winnerText = (TextView) findViewById(R.id.winnerTextView);
            Button playAgain = (Button) findViewById(R.id.PlayAgainButton);

            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                //someone has won
                gameCurrentState = false;

                String winner = "";
                if (activePlayer == 1)
                    winner = "Human";
                else
                    winner = "Bot";

                winnerText.setText(winner + " has won");
                winnerText.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);
            } else if (gameState[0] != 2 && gameState[1] != 2 && gameState[2] != 2 && gameState[3] != 2 && gameState[4] != 2 && gameState[5] != 2 && gameState[6] != 2 && gameState[7] != 2 && gameState[8] != 2) {

                gameCurrentState = false;

                winnerText.setText("Draw");
                winnerText.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);

                if (gameState[0] == gameState[1] && gameState[1] == gameState[2] || gameState[3] == gameState[4] && gameState[4] == gameState[5] || gameState[6] == gameState[7] && gameState[7] == gameState[8] || gameState[0] == gameState[3] && gameState[3] == gameState[6] || gameState[1] == gameState[4] && gameState[4] == gameState[7] || gameState[2] == gameState[5] && gameState[5] == gameState[8] || gameState[0] == gameState[4] && gameState[4] == gameState[8] || gameState[2] == gameState[4] && gameState[4] == gameState[6]) {
//                    Toast.makeText(playWithBot.this, "Somebody wins!!" + activePlayer, Toast.LENGTH_SHORT).show();

                    String winner = "";
                    if (activePlayer == 1)
                        winner = "Human";
                    else
                        winner = "Bot";

                    winnerText.setText(winner + " has won");
                    winnerText.setVisibility(View.VISIBLE);
                    playAgain.setVisibility(View.VISIBLE);


                }
            }

        }
    }


    public void playAgain(View view) {

        TextView winnerText = (TextView) findViewById(R.id.winnerTextView);
        Button playAgain = (Button) findViewById(R.id.PlayAgainButton);
        winnerText.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setClickable(true);
            counter.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++) {

            gameState[i] = 2;
        }

        activePlayer = 0;
        gameCurrentState = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_with_bot);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);


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

        backTouch = false;
        back_arrow = findViewById(R.id.back_arrow);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backTouch) {
                    finish();
                } else {
                    backTouch = true;
                    Toast.makeText(playWithBot.this, "Are you sure, you want to Quit!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView1);
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView2);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView3);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView4);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView5);
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView6);
            }
        });


        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView7);
            }
        });
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView8);
            }
        });

        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropin(imageView9);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }
}