package com.nichtemna.Overlay;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

public class LayoutWithHole extends LinearLayout {
    private Paint mPaint;
    private Bitmap b;
    private Canvas c;
    private float x, y, r;

    public LayoutWithHole(Context context) {
        super(context);
    }


    public LayoutWithHole(Context context, AttributeSet set) {
        super(context, set);
        setWillNotDraw(false);

        mPaint = new Paint();
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setColor(0xFFFFFFFF);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            c = new Canvas(b);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (b == null) {
            b = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            c = new Canvas(b);
        }
        b.eraseColor(Color.TRANSPARENT);
        c.drawColor(getResources().getColor(R.color.grey));
        c.drawCircle(x, y, r, mPaint);
        canvas.drawBitmap(b, 0, 0, null);
    }

    /**
     * Method to draw transparent circle around view for SDK <= 10
     *
     * @param view to position circle
     */
    public void drawCircle(View view, int topOffset) {
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        int loc_x = loc[0];
        int loc_y = loc[1] - topOffset;

        x = loc_x + view.getWidth() / 2;
        y = loc_y + view.getHeight() / 2;
        r = view.getWidth() > view.getHeight() ? view.getWidth() / 2 + 15 : view.getHeight() / 2 + 15;
        invalidate();
    }

    /**
     * Method to draw transparent circle around view for SDK > 10
     *
     * @param view to position circle
     */
    public void drawCircle(View view) {
        x = view.getX() + view.getWidth() / 2;
        y = view.getY() + view.getHeight() / 2;
        r = view.getWidth() > view.getHeight() ? view.getWidth() / 2 + 15 : view.getHeight() / 2 + 15;
        invalidate();
    }

}
