package com.broko.flappybird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class PipeSprite {

    private Bitmap image;
    private Bitmap image2;
    public int xX, yY;
    private int xVelocity = 10;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private int startX;
    private int startY;


    public PipeSprite(Bitmap bmp, Bitmap bmp2, int x, int y) {
        image = bmp;
        image2 = bmp2;
        yY = startY = y;
        xX = startX = x;

    }

    public void resetXY(){
        xX = startX;
        yY = startY;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, xX, -(GameView.GAPHEIGHT / 2) + yY, null);
        canvas.drawBitmap(image2,xX, ((screenHeight / 2)
                + (GameView.GAPHEIGHT / 2)) + yY, null);}

    public void update() {
        xX -= GameView.VELOCITY;
    }

}
