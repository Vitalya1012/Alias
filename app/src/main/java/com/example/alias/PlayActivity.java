package com.example.alias;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class PlayActivity extends AppCompatActivity {
    TextView textViewTeam;
    TextView textViewTeam1;
    TextView textViewTeam2;
    TextView textViewWord;
    TextView textViewTime;
    TextView textViewGuessedCountTeam1;
    TextView textViewGuessedCountTeam2;
    String team1Text;
    String team2Text;
    int wordsCount;
    int guessedCounterTeam1 = 0;
    int guessedCounterTeam2 = 0;
    int seconds;
    Button buttonOk;
    Button buttonSkip;
    Button buttonStartTimer;
    Button buttonRestart;
    Boolean team1Playing;
    Boolean team2Playing;
    Boolean switchBoolean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        seconds = intent.getIntExtra("seconds", 10);
        wordsCount = intent.getIntExtra("words", 10);
        team1Text = intent.getStringExtra("team1Text");
        team2Text = intent.getStringExtra("team2Text");
        switchBoolean = intent.getBooleanExtra("switchBoolean",false);
        textViewTeam1 = findViewById(R.id.textViewTeam1);
        textViewTeam2 = findViewById(R.id.textViewTeam2);
        if (team1Text.isEmpty()) {
            team1Text = getString(R.string.default_team1_name);
        }
        textViewTeam1.setText(team1Text);
        if (team2Text.isEmpty()) {
            team2Text = getString(R.string.default_team2_name);
        }
        textViewTeam2.setText(team2Text);
        textViewTeam = findViewById(R.id.textViewTeam);
        textViewWord = findViewById(R.id.textViewWord);
        textViewTime = findViewById(R.id.textViewTime);
        textViewGuessedCountTeam1 = findViewById(R.id.textViewGuessedCountTeam1);
        textViewGuessedCountTeam2 = findViewById(R.id.textViewGuessedCountTeam2);
        buttonOk = findViewById(R.id.buttonOk);
        buttonSkip = findViewById(R.id.buttonSkip);
        buttonStartTimer = findViewById(R.id.buttonStartTimer);
        buttonRestart = findViewById(R.id.buttonRestart);
        textViewTeam.setText(team1Text);
        team1Playing = false;
        team2Playing = true;
    }

    public void nextYes(View view) {
        changeWord();
        if (team1Playing) {
            guessedCounterTeam1++;
            textViewGuessedCountTeam1.setText("" + guessedCounterTeam1);
        }
        if (team2Playing) {
            guessedCounterTeam2++;
            textViewGuessedCountTeam2.setText("" + guessedCounterTeam2);
        }
    }

    public void nextNo(View view) {
        changeWord();
        if (team1Playing) {
            guessedCounterTeam1--;
            textViewGuessedCountTeam1.setText("" + guessedCounterTeam1);
        }
        if (team2Playing) {
            guessedCounterTeam2--;
            textViewGuessedCountTeam2.setText("" + guessedCounterTeam2);
        }
    }


    void changeWord() {
        String[] wordsArray;
        if (switchBoolean) {
            wordsArray = getResources().getStringArray(R.array.words18);
        } else
            wordsArray = getResources().getStringArray(R.array.words);

        int wordNumber = (int) (Math.random() * wordsArray.length);
        String word = wordsArray[wordNumber];
        textViewWord.setText(word);

    }

    public void startTimer(View view) {
        textViewGuessedCountTeam1.setVisibility(View.VISIBLE);
        textViewGuessedCountTeam2.setVisibility(View.VISIBLE);
        if (!team1Playing) {
            team1Playing = true;
        } else team1Playing = false;

        if (!team2Playing) {
            team2Playing = true;
        } else team2Playing = false;

        runTimer();
        changeWord();
        buttonStartTimer.setVisibility(View.INVISIBLE);
        buttonOk.setVisibility(View.VISIBLE);
        buttonSkip.setVisibility(View.VISIBLE);
        textViewWord.setVisibility(View.VISIBLE);
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            int sec = seconds;

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                int minutes = sec / 60;
                int secs = (sec % 60);
                String time = String.format(Locale.getDefault(), "%d:%02d", minutes, secs);
                textViewTime.setText(time);
                sec--;
                handler.postDelayed(this, 1000);
                if (sec <= -1) {
                    textViewTime.setText("время вышло");
                    if (team1Playing) {
                        textViewTeam.setText(team2Text);
                        textViewTeam.setTextColor(getColor(R.color.red));
                    }
                    if (team2Playing) {
                        textViewTeam.setText(team1Text);
                        textViewTeam.setTextColor(getColor(R.color.white));

                    }
                    buttonOk.setVisibility(View.INVISIBLE);
                    buttonSkip.setVisibility(View.INVISIBLE);
                    buttonStartTimer.setVisibility(View.VISIBLE);
                    textViewWord.setVisibility(View.INVISIBLE);
                    if (team2Playing) {
                        if (guessedCounterTeam1 >= wordsCount && guessedCounterTeam1 > guessedCounterTeam2) {
                            textViewTeam.setText("победили\n" + team1Text);
                            textViewTeam.setTextColor(getColor(R.color.white));
                            buttonRestart.setVisibility(View.VISIBLE);
                            buttonStartTimer.setVisibility(View.INVISIBLE);
                        } else if (guessedCounterTeam2 >= wordsCount && guessedCounterTeam2 > guessedCounterTeam1) {
                            textViewTeam.setText("победили\n" + team2Text);
                            textViewTeam.setTextColor(getColor(R.color.red));
                            buttonRestart.setVisibility(View.VISIBLE);
                            buttonStartTimer.setVisibility(View.INVISIBLE);
                        }  else if (guessedCounterTeam1 >= wordsCount && guessedCounterTeam2 == guessedCounterTeam1) {
                            textViewTeam.setText("ничья");
                            textViewTeam.setTextColor(getColor(R.color.red));
                            buttonRestart.setVisibility(View.VISIBLE);
                            buttonStartTimer.setVisibility(View.INVISIBLE);
                        }
                    }
                    handler.removeCallbacks(this);
                }
            }
        });
    }

    public void restart(View view) {
        Intent intent = new Intent(this, SettingsActivity2.class);
        startActivity(intent);
    }
}