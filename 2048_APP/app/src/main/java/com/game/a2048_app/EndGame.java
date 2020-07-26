package com.game.a2048_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndGame extends AppCompatActivity {

    // TODO: 23.07.2020  Julia work list:
    //  - animacja klockow
    //  - po uzyskaniu 2048 gra dalej
    //  - javaDoc
    //  - https://stackoverflow.com/a/151940
    //  - undo = 0 - unclickable
    //  - wyciszanie apki
    //  - ten przycisk undo - lepiej ustawić cyfrę w środek
    //  - testy testy testy (jeden jest prawdkopodobnie zepsuty, dodać testy do tego co dzisiaj zrobiłem)
    //  - wyczyścić to co zrobiłem w board i boardActivity, zoptymalizować, usunac nie potrzebny kod, podzielić na funkcje, uładnić kod
    //  - zrobić animacje pojawiania się nowych klocków, no przejście z naszej animacji do po animacji
    //  - zablokować możliwość ruchu po planszy w trakcie animacji
    //  - naprawić mediaPlayer tak jak podałem w linku https://stackoverflow.com/a/15189123 - każdy mediaplayer musi być w ten sposób zakończony po odtworzeniu swojego dźwięku.


    private String score;
    private String highScore;
    private Boolean authentication;
    private EndGame endgame = this;
    private Button homePageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        score = intent.getStringExtra(getResources().getString(R.string.score));
        highScore = intent.getStringExtra(getResources().getString(R.string.high_score));
        authentication = Boolean.parseBoolean(intent.getStringExtra(String.valueOf(R.string.authentication)));
        authentication = Boolean.parseBoolean(intent.getStringExtra(getResources().getString(R.string.authentication)));
        setContentView(R.layout.activity_end_game);
        homePageButton = findViewById(R.id.homePage);
        homePageButton.setOnClickListener(initializeBoardActivity);
        loadData();
    }

    private void loadData() {
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.settings), MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean(getResources().getString(R.string.dark_theme), false);
        setTheme(isDarkTheme);
        setTextScoreText();
        setTextHighScoreText();
    }

    private void setTheme(boolean isDarkTheme) {
        ImageView darkThemeView = (ImageView) findViewById(R.id.darkThemeView);
        if (isDarkTheme) {
            darkThemeView.setImageResource(R.drawable.dark_theme_on);
        } else {
            darkThemeView.setImageResource(R.drawable.dark_theme_off);
        }
    }

    void setTextScoreText() {
        TextView textScore = (TextView) findViewById(R.id.textScore);
        textScore.setText(String.format("%s:\n%s", getResources().getString(R.string.score), score));
    }

    void setTextHighScoreText() {
        if (authentication) {
            TextView textScore = (TextView) findViewById(R.id.textHighScore);
            textScore.setText(String.format("%s:\n%s", getResources().getString(R.string.high_score), highScore));
        }
    }

    private View.OnClickListener initializeBoardActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            homePageButton.setBackgroundResource(R.drawable.main_activity_button_clicked);
            MediaPlayer mediaPlayerStart = MediaPlayer.create(endgame, R.raw.slide_activities);
            mediaPlayerStart.start();
            mediaPlayerStart.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    homePageButton.setBackgroundResource(R.drawable.main_activity_button);
                }

            });
            startActivity(new Intent(EndGame.this, MainActivity.class));
        }
    };

    @Override
    public void onBackPressed() {
    }

}
