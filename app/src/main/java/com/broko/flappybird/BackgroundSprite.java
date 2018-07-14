package com.broko.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackgroundSprite {
    private Bitmap image;
    private  int x,y;

    public BackgroundSprite(Bitmap image){
        this.image = image;

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, 0, 0, null);
        canvas.drawBitmap(image, 0 + image.getWidth(), 0, null);
        canvas.drawBitmap(image, 0 + image.getWidth()*2, 0, null);
    }

}
