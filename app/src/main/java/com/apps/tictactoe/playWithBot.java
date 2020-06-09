package com.apps.tictactoe;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class playWithBot extends AppCompatActivity {

    private String player1, player2 = "Baymax (Bot)", exit="";
    private EditText player1EditText;
    private TextView playerInst;
    private LinearLayout back_arrow_layout, inst_layout;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9;
    private boolean backTouch;

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
            playerInst.setText(player2);
            playerInst.setTextColor(getResources().getColor(R.color.hint_color));
            activePlayer = 1;   //computer turn
            image.animate().translationY(12).rotationBy(3600).setDuration(200);
            image.setClickable(false);
            //function to obtain answer from computer and put in the right place

            checkWin();
            if (gameCurrentState)
                getBotMove(tappedCounter);      //bot


        }

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
            playerInst.setText(player1);
            playerInst.setTextColor(getResources().getColor(R.color.load_btn_border));
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

                inst_layout.setVisibility(View.GONE);
                //someone has won
                gameCurrentState = false;

                String winner = "";
                if (activePlayer == 1)
                    winner = player1;
                else
                    winner = "You have been defeated!";

                //show dialog of win
                Dialog dialog = new Dialog(playWithBot.this);
                dialog.setContentView(R.layout.win_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView winnerTv = dialog.findViewById(R.id.winner_dialog_text);
                winnerTv.setText(winner);
                dialog.show();


//                winnerText.setText(winner + " has won");
//                winnerText.setVisibility(View.VISIBLE);
                playAgain.setVisibility(View.VISIBLE);
            } else if (gameState[0] != 2 && gameState[1] != 2 && gameState[2] != 2 && gameState[3] != 2 && gameState[4] != 2 && gameState[5] != 2 && gameState[6] != 2 && gameState[7] != 2 && gameState[8] != 2) {

                inst_layout.setVisibility(View.GONE);
                gameCurrentState = false;


                if (gameState[0] == gameState[1] && gameState[1] == gameState[2] || gameState[3] == gameState[4] && gameState[4] == gameState[5] || gameState[6] == gameState[7] && gameState[7] == gameState[8] || gameState[0] == gameState[3] && gameState[3] == gameState[6] || gameState[1] == gameState[4] && gameState[4] == gameState[7] || gameState[2] == gameState[5] && gameState[5] == gameState[8] || gameState[0] == gameState[4] && gameState[4] == gameState[8] || gameState[2] == gameState[4] && gameState[4] == gameState[6]) {
//                    Toast.makeText(playWithBot.this, "Somebody wins!!" + activePlayer, Toast.LENGTH_SHORT).show();

                    String winner = "";
                    if (activePlayer == 1)
                        winner = player1 + " wins!!";
                    else
                        winner = "You have been defeated!";

                    //show dialog of win
                    Dialog dialog = new Dialog(playWithBot.this);
                    dialog.setContentView(R.layout.win_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView winnerTv = dialog.findViewById(R.id.winner_dialog_text);
                    winnerTv.setText(winner);
                    dialog.findViewById(R.id.PlayAgainButton);
                    dialog.show();

//                    winnerText.setText(winner + " has won");
//                    winnerText.setVisibility(View.VISIBLE);
                    playAgain.setVisibility(View.VISIBLE);


                }else{
                    winnerText.setText("Draw");
                    winnerText.setVisibility(View.VISIBLE);
                    playAgain.setVisibility(View.VISIBLE);

                }
            }

        }
    }


    public void playAgain(View view) {

        getInputDialog();
        inst_layout.setVisibility(View.VISIBLE);

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

        inst_layout = findViewById(R.id.inst_layout);
        playerInst = findViewById(R.id.player_inst);

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
        back_arrow_layout = findViewById(R.id.back_arrow_layout);
        back_arrow_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (backTouch) {
                    finish();
                } else {
                    backTouch = true;
                    Toast.makeText(playWithBot.this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
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

        getInputDialog();

    }

    public void getInputDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.custom_dialog_bot, null);
        player1EditText = (EditText) dialogLayout.findViewById(R.id.player1);
        builder.setCancelable(false);
        builder.setView(dialogLayout);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //function written later to not allow the user to enter blank names
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                finish();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // write check code
                if (TextUtils.isEmpty(player1EditText.getText().toString())) {
                    player1EditText.setError("Please enter a name");
                    return;
                }

                dialog.dismiss();
                player1 = player1EditText.getText().toString();
                playerInst.setText(player1);
                playerInst.setTextColor(getResources().getColor(R.color.load_btn_border));
//                Toast.makeText(MainActivity.this, "Valid player names", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onBackPressed() {
        if(exit.equals("finish")){
            finish();
            exit = "";
        }

        Toast.makeText(playWithBot.this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        exit = "finish";
    }

}