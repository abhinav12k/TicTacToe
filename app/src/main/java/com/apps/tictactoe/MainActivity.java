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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private String player1, player2,exit="";
    private EditText player1EditText, player2EditText;
    private TextView playerInst;
    private LinearLayout back_arrow_layout, inst_layout;
    private boolean backTouch;
    // 0:yellow; 1:red; 2: empty; 3: computer

    int activePlayer = 0;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    boolean gameCurrentState = true;

    public void dropin(View view) {

        ImageView counter = (ImageView) view;

        Log.i("Image clicked", counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameCurrentState) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1000);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.ic_o);
                playerInst.setText(player2);
                playerInst.setTextColor(getResources().getColor(R.color.hint_color));
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.ic_x);
                playerInst.setText(player1);
                playerInst.setTextColor(getResources().getColor(R.color.load_btn_border));
                activePlayer = 0;
            }

            counter.animate().translationY(12).rotationBy(3600).setDuration(200);

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
                        winner = player2;


                    //show dialog of win
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.win_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView winnerTv = dialog.findViewById(R.id.winner_dialog_text);
                    winnerTv.setText(winner + " wins!!");
                    dialog.show();

//                    winnerText.setText(winner + " has won");
//                    winnerText.setVisibility(View.VISIBLE);
                    playAgain.setVisibility(View.VISIBLE);
                } else if (gameState[0] != 2 && gameState[1] != 2 && gameState[2] != 2 && gameState[3] != 2 && gameState[4] != 2 && gameState[5] != 2 && gameState[6] != 2 && gameState[7] != 2 && gameState[8] != 2) {

                    inst_layout.setVisibility(View.GONE);
                    gameCurrentState = false;

                    if (gameState[0] == gameState[1] && gameState[1] == gameState[2] || gameState[3] == gameState[4] && gameState[4] == gameState[5] || gameState[6] == gameState[7] && gameState[7] == gameState[8] || gameState[0] == gameState[3] && gameState[3] == gameState[6] || gameState[1] == gameState[4] && gameState[4] == gameState[7] || gameState[2] == gameState[5] && gameState[5] == gameState[8] || gameState[0] == gameState[4] && gameState[4] == gameState[8] || gameState[2] == gameState[4] && gameState[4] == gameState[6]) {
//                    Toast.makeText(playWithBot.this, "Somebody wins!!" + activePlayer, Toast.LENGTH_SHORT).show();

                        String winner = "";
                        if (activePlayer == 1)
                            winner = player1;
                        else
                            winner = player2;



                        //show dialog of win
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.win_dialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        TextView winnerTv = dialog.findViewById(R.id.winner_dialog_text);
                        winnerTv.setText(winner+" wins!!");
                        dialog.show();

//                        winnerText.setText(winner + " has won");
//                        winnerText.setVisibility(View.VISIBLE);
                        playAgain.setVisibility(View.VISIBLE);


                    }else{
                        winnerText.setText("Draw");
                        winnerText.setVisibility(View.VISIBLE);
                        playAgain.setVisibility(View.VISIBLE);
                    }

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
            counter.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++) {

            gameState[i] = 2;
        }

        activePlayer = 0;
        gameCurrentState = true;

    }
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adView = findViewById(R.id.adView);

        MobileAds.initialize(this,"ca-app-pub-3940256099942544/6300978111");

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        inst_layout = findViewById(R.id.inst_layout);
        playerInst = findViewById(R.id.player_inst);

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
                    Toast.makeText(MainActivity.this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getInputDialog();

    }

    public void getInputDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.custom_dialog, null);
        player1EditText = (EditText) dialogLayout.findViewById(R.id.player1);
        player2EditText = (EditText) dialogLayout.findViewById(R.id.player2);
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
                if (TextUtils.isEmpty(player2EditText.getText().toString())) {
                    player2EditText.setError("Please enter a name");
                    return;
                }

                dialog.dismiss();
                player1 = player1EditText.getText().toString();
                player2 = player2EditText.getText().toString();
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

        Toast.makeText(MainActivity.this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        exit = "finish";
    }
}
