package com.nichtemna.Overlay;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MyActivity extends Activity implements View.OnClickListener {
    private Button button;
    private FrameLayout mFrame;
    private LayoutWithHole layoutWithHole;
    private View overlay;
    private boolean overlayVisible = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFrame = new FrameLayout(this);

        mFrame.addView(LayoutInflater.from(getBaseContext()).inflate(R.layout.main, null));
        setContentView(mFrame);

        overlay = LayoutInflater.from(getBaseContext()).inflate(R.layout.overlay, null);
        mFrame.addView(overlay);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        layoutWithHole = (LayoutWithHole) findViewById(R.id.layout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                layoutWithHole.drawCircle(button);
            } else {
                DisplayMetrics dm = new DisplayMetrics();
                this.getWindowManager().getDefaultDisplay().getMetrics(dm);
                int topOffset = dm.heightPixels - mFrame.getMeasuredHeight();
                layoutWithHole.drawCircle(button, topOffset);
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                toggleOverlay();
                break;
        }
    }

    /**
     * Shows and hide overlay view
     */
    private void toggleOverlay() {
        if (overlayVisible) {
            overlayVisible = false;
            mFrame.removeView(overlay);
        } else {
            overlayVisible = true;
            mFrame.addView(overlay);
        }
    }
}