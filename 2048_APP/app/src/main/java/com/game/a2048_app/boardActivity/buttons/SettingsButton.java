package com.game.a2048_app.boardActivity.buttons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.game.a2048_app.R;
import com.game.a2048_app.helpers.PreferencesHelper;
import com.game.a2048_app.helpers.Preloader;
import com.game.a2048_app.helpers.SoundPlayer;

// TODO: 09.08.2020 dodać xml
public class SettingsButton extends androidx.appcompat.widget.AppCompatButton {

    Preloader preloader = Preloader.getInstance();
    PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();

    public final static boolean[] chosenSensors = new boolean[]{false, false, false, false};
    private Context context;

    private void setupButton(Context context) {
        this.context = context;
        this.setOnClickListener(this.onClickListener);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            settingsButtonOnClick(v);
        }
    };

    public SettingsButton(Context context) {
        super(context);
        this.setupButton(context);
    }

    public SettingsButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setupButton(context);
    }

    public SettingsButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setupButton(context);
    }

    /**
     * Creates button on click listener to open settings dialog.
     * Play sound after click and change button's image.
     * Creates dialog to allow the user to turn sensors on or off.
     */
    public void settingsButtonOnClick(View v) {
        this.setBackground(preloader.getSettingsClicked());
        SoundPlayer soundPlayer = SoundPlayer.getInstance();
        soundPlayer.playSound(soundPlayer.getAsset(this.context, R.raw.button_no_reverb));
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMultiChoiceItems(R.array.sensors, chosenSensors, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                chosenSensors[which] = isChecked;
            }
        });
        builder.setCancelable(false);
        builder.setTitle(R.string.settings_menu_title);
        builder.setPositiveButton(getResources().getString(R.string.dialog_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preferencesHelper.setChoosenSensors(chosenSensors);
                SettingsButton.this.setBackground(preloader.getSettings());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(disableUnavailableSensorsInDialog);
        dialog.show();
    }

    private DialogInterface.OnShowListener disableUnavailableSensorsInDialog = new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            SensorManager mSensorManager = (SensorManager) context.getSystemService(
                    Context.SENSOR_SERVICE);
            assert mSensorManager != null;
            Sensor mSensorAccelerometer = mSensorManager.getDefaultSensor(
                    Sensor.TYPE_ACCELEROMETER);
            Sensor mSensorMagnetometer = mSensorManager.getDefaultSensor(
                    Sensor.TYPE_MAGNETIC_FIELD);
            Sensor mSensorLight = mSensorManager.getDefaultSensor(
                    Sensor.TYPE_LIGHT);
            Sensor mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            final ListView alertDialogList = ((AlertDialog) dialog).getListView();
            for (int position = 0; position < alertDialogList.getChildCount(); position++) {
                // TODO: 23.07.2020 Zobaczyć czy dziala
                if ((mSensorAccelerometer == null && getResources().getStringArray(R.array.sensors)[position]
                        .equals(getResources().getString(R.string.Gyroscope_And_Accelerometer_Settings))
                        || (mSensorMagnetometer == null && getResources().getStringArray(R.array.sensors)[position]
                        .equals(getResources().getString(R.string.Magnetometer_Settings))
                        || mSensorLight == null && getResources().getStringArray(R.array.sensors)[position]
                        .equals(getResources().getString(R.string.Light_Sensor_Settings))
                        || mSensorProximity == null && getResources().getStringArray(R.array.sensors)[position]
                        .equals(getResources().getString(R.string.Proximity_Sensor_Settings))))) {
                    alertDialogList.getChildAt(position).setEnabled(false);
                    alertDialogList.getChildAt(position).setOnClickListener(null);
                }
            }
        }
    };
}