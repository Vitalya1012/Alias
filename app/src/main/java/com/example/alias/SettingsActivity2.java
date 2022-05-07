package com.example.alias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity2 extends AppCompatActivity {
    SeekBar seekBarWords;
    SeekBar seekBarTime;
    Switch switch18;
    boolean switchBoolean;
    int words;
    int seconds;
    String wordsString;
    String secondsString;
    private TextView numberOfWords;
    private TextView time;
    private EditText team1;
    private EditText team2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        Intent intent = getIntent();
        switch18 = findViewById(R.id.switch18);
        seekBarWords = findViewById(R.id.seekBarNumberOfWords);
        seekBarWords.setMax(17);
        seekBarTime = findViewById(R.id.seekBarSeconds);
        seekBarTime.setMax(10);
        numberOfWords = findViewById(R.id.textViewSetNumberOfWords);
        time = findViewById(R.id.textViewSetTime);
        switch18.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                switchBoolean = b;
            }
        });
        seekBarWords.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                words = i*5+15;
                wordsString = ""+words;
                numberOfWords.setText(wordsString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarWords.setProgress(7);

        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seconds = i*10+20;
                secondsString = ""+seconds;
                time.setText(secondsString);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarTime.setProgress(4);

        team1 = findViewById(R.id.editTextTeam1);
        team2 = findViewById(R.id.editTextTeam2);

    }

    public void startGame(View view) {
        String team1Text = team1.getText().toString();
        String team2Text = team2.getText().toString();
        Intent intent = new Intent(this,PlayActivity.class);
        intent.putExtra("words",words);
        intent.putExtra("seconds", seconds);
        intent.putExtra("team1Text", team1Text);
        intent.putExtra("team2Text", team2Text);
        intent.putExtra("switchBoolean", switchBoolean);
        startActivity(intent);

    }
}