package com.broko.flappybird;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;




import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//GameView Class
//added no reply EMail - Test#2


class GameView extends SurfaceView implements android.view.SurfaceHolder.Callback {

    private MainThread thread;
    private CharacterSprite characterSprite;
    public static int GAPHEIGHT = 500;
    public static int VELOCITY = 10;
    public static int SCREENHEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static int SCREENWIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;

    List<PipeSprite> pipes;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        setFocusable(true);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
        Bitmap upflap = BitmapFactory.decodeResource(getResources(), R.drawable.redbird_upflap);
        Bitmap midflap = BitmapFactory.decodeResource(getResources(),R.drawable.redbird_midflap);
        Bitmap downflap = BitmapFactory.decodeResource(getResources(),R.drawable.redbird_downflap);
        characterSprite = new CharacterSprite();
        characterSprite.addBitmap(upflap);
        characterSprite.addBitmap(midflap);
        characterSprite.addBitmap(downflap);
        makeLevel();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            characterSprite.draw(canvas);
            for (int i = 0; i < pipes.size(); i++) {
                pipes.get(i).draw(canvas);

            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (characterSprite.y > 0) {
            characterSprite.y = (characterSprite.y - (characterSprite.yVelocity * 15));

        }
        return super.onTouchEvent(event);
    }

    public void update() {
        characterSprite.update();

        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).update();
        }
        logic();

        if(thread.getFrameCount() == 60){
            characterSprite.flap();}

    }

    public void makeLevel() {
        Bitmap bmp;
        Bitmap bmp2;

        bmp = getResizedBitmap(BitmapFactory.decodeResource
                        (getResources(), R.drawable.pipe_green), 500,
                Resources.getSystem().getDisplayMetrics().heightPixels / 2);
        bmp2 = getResizedBitmap(BitmapFactory.decodeResource
                        (getResources(), R.drawable.pipe_green_up), 500,
                Resources.getSystem().getDisplayMetrics().heightPixels / 2);

        PipeSprite pipe1 = new PipeSprite(bmp, bmp2, 2000, 0);
        PipeSprite pipe2 = new PipeSprite(bmp, bmp2, 3200, -250);
        PipeSprite pipe3 = new PipeSprite(bmp, bmp2, 4500, 250);

        pipes = new ArrayList<>();
        pipes.add(pipe1);
        pipes.add(pipe2);
        pipes.add(pipe3);
        System.out.println("MAKE LEVEL complete");
    }


    public void logic() {



        for (int i = 0; i < pipes.size(); i++) {
            //Detect if the character is touching one of the pipes
            if (characterSprite.y < pipes.get(i).yY + (SCREENHEIGHT / 2)
                    - (GAPHEIGHT / 2) && characterSprite.x + 300 > pipes.get(i).xX
                    && characterSprite.x < pipes.get(i).xX + 500) {
                resetLevel();
            } else if (characterSprite.y + 240 > (SCREENHEIGHT / 2) +
                    (GAPHEIGHT / 2) + pipes.get(i).yY
                    && characterSprite.x + 300 > pipes.get(i).xX
                    && characterSprite.x < pipes.get(i).xX + 500) {
                resetLevel();
            }

            //Detect if the pipe has gone off the left of the
            //screen and regenerate further ahead
            if (pipes.get(i).xX + 500 < 0) {
                Random r = new Random();
                int value1 = r.nextInt(500);
                int value2 = r.nextInt(500);
                pipes.get(i).xX = SCREENWIDTH + value1 + 1000;
                pipes.get(i).yY = value2 - 250;
            }
        }

        //Detect if the character has gone off the
        //bottom or top of the screen
        if (characterSprite.y + 240 < 0) {
            resetLevel(); }
        if (characterSprite.y > SCREENHEIGHT) {
            resetLevel(); }
    }

    public void resetLevel() {
        characterSprite.y = 100;
        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).resetXY();
            System.out.println("RESET");

        }}

    //Helper Methods

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap =
                Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }


}
