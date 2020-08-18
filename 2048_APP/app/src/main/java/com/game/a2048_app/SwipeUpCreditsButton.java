package com.game.a2048_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.game.a2048_app.credits.Credits;
import com.game.a2048_app.helpers.Preloader;


/**
 * Button used in {@link MainActivity} and {@link EndGame} to open the {@link Credits} activity.
 * Pressing the button takes the user to {@link Credits}. Using the button in an Activity also implements {@link OnSwipeTouchListener#onSwipeTop()}
 * That performs the same action as pressing the button.
 *
 */

public class SwipeUpCreditsButton extends androidx.appcompat.widget.AppCompatButton {
    private Preloader preloader = Preloader.getInstance();
    private Context context;

    public void initContext(Context context) {
        this.context = context;
    }

    public SwipeUpCreditsButton(Context context) {
        super(context);
        initContext(context);
        this.prepareButton();
    }

    public SwipeUpCreditsButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initContext(context);
        this.prepareButton();
    }

    public SwipeUpCreditsButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContext(context);
        this.prepareButton();
    }

    private OnClickListener onClickListener = new OnClickListener() {
        /**
         * {@inheritDoc}
         * Takes the user to {@link Credits} Activity.
         */
        @Override
        public void onClick(View v) {
            swipeUpCreditsButtonOnClick(v);
        }
    };

    private void prepareButton() {
        this.setSwipeUpCreditsImage();
        this.setOnClickListener(this.onClickListener);
    }


    /**
     * Sets the swipe up button's image
     */
    private void setSwipeUpCreditsImage() {
        this.setBackground(preloader.getSwipeUpCredits());
    }

    /**
     * Creates button on click listener to swipe up.
     * Starts new credits activity.
     */
    // TODO: 09.08.2020 chce to ustawić w xml
    public void swipeUpCreditsButtonOnClick(View v) {
        startNewCreditsActivity();
    }

    private void startNewCreditsActivity(){
        Intent intent = new Intent(context, Credits.class).putExtra(Activity.ACTIVITY_SERVICE, ((Activity) context).getClass().getCanonicalName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) this.context).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }
    }

    public void setupSwipeTopListener(final Context context, View view) {
        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(context, view);
        onSwipeTouchListener.onSwipe = new OnSwipeTouchListener.onSwipeListener() {
            @Override
            public void swipeRight() {
            }

            @Override
            public void swipeTop() {
                startNewCreditsActivity();
            }

            @Override
            public void swipeBottom() {
            }

            @Override
            public void swipeLeft() {
            }
        };
    }
}
