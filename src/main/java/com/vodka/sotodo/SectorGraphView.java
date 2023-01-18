package com.vodka.sotodo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class SectorGraphView extends View {

    private int[] colors;
    private float[] proportions;

    private Paint mPaint;
    private Bitmap bitmap;
    private Canvas mCanvas;
    private RectF mRectf;

    private int startAngle = -90, endAngle = 270;
    private int[] colorStartAngles;

    public SectorGraphView(Context context) {
        super(context);
    }

    public SectorGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SectorGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SectorGraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 设置饼图每一块的颜色
     *
     * @param color 颜色
     */
    public SectorGraphView setColor(int... color) {
        colors = color.clone();
        return this;
    }

    /**
     * 设置饼图每一块的比例
     *
     * @param proportion 比例(0-1)
     */
    public SectorGraphView setProportion(float... proportion) {
        proportions = proportion.clone();
        return this;
    }

    /**
     * 绘制饼图
     */
    public void draw() {
        if (colors.length != proportions.length) {
            throw new IllegalArgumentException("color和proportion的数量不同!");
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    mPaint = new Paint();
                    mPaint.setAntiAlias(true);

                    mRectf = new RectF(0, 0, SectorGraphView.this.getWidth(), SectorGraphView.this.getHeight());

                    colorStartAngles = new int[colors.length];
                    colorStartAngles[0] = startAngle;
                    for (int i = 1; i < colors.length; i++) {
                        colorStartAngles[i] = colorStartAngles[i - 1] + (int) (360 * proportions[i - 1]);
                    }

                    bitmap = Bitmap.createBitmap(SectorGraphView.this.getWidth(), SectorGraphView.this.getHeight(), Bitmap.Config.ARGB_8888);
                    mCanvas = new Canvas(bitmap);

                    SectorGraphView.this.logic();

                }
            });
        }
    }

    private void logic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (startAngle <= endAngle) {
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < colorStartAngles.length; i++) {
                        if (startAngle == colorStartAngles[i]) {
                            mPaint.setColor(colors[i]);
                        }
                    }
                    mCanvas.drawArc(mRectf, startAngle, 1, true, mPaint);
                    SectorGraphView.this.postInvalidate();
                    startAngle++;
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPaint == null) return;
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
    }

}