package com.example.mysurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    Thread myThread;

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        myThread = new MyThread();

        myThread.start();

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        myThread.interrupt();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class MyThread extends Thread {
        int x = getWidth()/2;
        Paint paint = new Paint();

        public void run() {
            while (!isInterrupted()) {
                Canvas canvas = getHolder().lockCanvas();

                if (canvas != null) {
                    canvas.drawColor(Color.YELLOW);

                    if (x >= getWidth()) {
                        x = 0;
                    }

                    for (int i = 100; i <= getHeight(); i+= 100) {
                        canvas.drawCircle(x, i, 30, paint);
                    }

                    for (int i = 100; i <= getWidth(); i+= 100) {
                        canvas.drawCircle(i, x, 30, paint);
                    }

                    x += 5;
                    getHolder().unlockCanvasAndPost(canvas);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
