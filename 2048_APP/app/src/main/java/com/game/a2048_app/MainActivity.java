
package com.game.a2048_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.fingerprint.FingerprintManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.game.a2048_app.boardActivity.BoardActivity;
import com.game.a2048_app.databinding.ActivityMainBinding;
import com.game.a2048_app.helpers.DarkModeHelper;
import com.game.a2048_app.helpers.PreferencesHelper;
import com.game.a2048_app.helpers.Preloader;
import com.game.a2048_app.helpers.SoundPlayer;

import me.aflak.libraries.callback.FingerprintDialogCallback;
import me.aflak.libraries.dialog.FingerprintDialog;

// TODO: 19.07.2020  powiązanie musi zawierać załączniki w postaci wszystkich dokumentów powiązanych
//  (not katalogowych, dokumentów RFC, aktów prawnych, itp.,
//  a także wszystkich programów źródłowych (także bibliotek open source'owych), a także programów wykonywalnych
//  do czego zmierzam, pamiętać żeby to dodać jako biblioteka opensourcowa
//  https://github.com/OmarAflak/Fingerprint

public class MainActivity extends AppCompatActivity implements FingerprintDialogCallback {

    private static Boolean isAuthenticated = false;
    private Button startGameButton;
    private Preloader preloader = Preloader.getInstance();
    private ActivityMainBinding binding;

    private static boolean isFirstRun = true;

    /**
     * Called when the activity is starting.
     * Calls methods to load data and initialize buttons.
     *
     * @param savedInstanceState if the activity is being re-initialized after previously being shut down
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise is null.
     */
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        PreferencesHelper.initContext(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initButtons();
        loadData();
        ((SwipeUpCreditsButton) findViewById(R.id.authors)).setupSwipeTopListener(this, findViewById(R.id.constraintLayoutMainActivity));
        if (binding.getIsFingerprintSensorAvailable() && isFirstRun) {
            this.authenticationButtonOnClick(findViewById(R.id.authenticateButton));
        }
        isFirstRun = false;
    }

    /**
     * @param context current context.
     * @return if fingerprint sensor is available.
     */
    private boolean isFingerprintSensorAvailable(Context context) {
        FingerprintManager manager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        return (manager != null && manager.isHardwareDetected() && manager.hasEnrolledFingerprints());
    }

    /**
     * Call method to set theme.
     * Loads volume and current theme.
     */
    private void loadData() {
        DarkModeHelper.setTheme((ImageView) findViewById(R.id.darkThemeView));
    }


    /**
     * Calls method to initialize buttons.
     */
    private void initButtons() {
        startGameButton = (Button) findViewById(R.id.startGameButton);
        binding.setIsFingerprintSensorAvailable(isFingerprintSensorAvailable(this));
    }

    /**
     * Creates button on click listener to start game.
     * Play sound after click and change button's image.
     */

    private MediaPlayer.OnCompletionListener setStartGameBackgroundListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            startGameButton.setBackground(preloader.getMainButton());
        }
    };


    public void mainActivityMainButtonOnClick(View v) {
        SoundPlayer soundPlayer = SoundPlayer.getInstance();
        soundPlayer.playSound(soundPlayer.getAsset(getApplicationContext(), R.raw.slide_activities), setStartGameBackgroundListener);
        this.startBoardActivity();
    }

    /**
     * Starts new activity - board activity.
     * Pass
     */
    private void startBoardActivity() {
        Intent i = new Intent(MainActivity.this, BoardActivity.class);
        i.putExtra(getResources().getString(R.string.authentication), isAuthenticated);
        startActivity(i);
    }

    /**
     * Init fingerprint dialog to authenticate user.
     */
    public void authenticationButtonOnClick(View v) {
        if (FingerprintDialog.isAvailable(this)) {
            FingerprintDialog.initialize(this)
                    .title(R.string.fingerprint_title)
                    .message(R.string.fingerprint_message)
                    .circleScanningColor(R.color.defaultApplicationTextColour)
                    .callback(this)
                    .show();
        }
    }

    /**
     * {@inheritDoc}
     * {@link #isAuthenticated} is set to true
     */
    @Override
    public void onAuthenticationSucceeded() {
        this.isAuthenticated = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAuthenticationCancel() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

}

