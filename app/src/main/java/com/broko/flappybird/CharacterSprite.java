package com.broko.flappybird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.LinkedList;


public class CharacterSprite {
    private LinkedList<Bitmap> imageList;
    public int x, y, yVelocity, xVelocity;
    private int screenWidth;
    private int screenHeight;


    private Bitmap currentImage;
    int flapCount;


    CharacterSprite() {
        imageList = new LinkedList<>();
        flapCount = 0;
        x = 100;
        y = 100;
        yVelocity = 10;
        xVelocity = 5;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        System.out.println("width: " + screenWidth);
        System.out.println("height: " + screenHeight);
    }

    public void setCurrentImage(Bitmap currentImage) {
        this.currentImage = currentImage;
    }

    public void addBitmap(Bitmap image) {
        imageList.add(image);
    }



    public void draw(Canvas canvas) {
        canvas.drawBitmap(currentImage, x, y, null);
    }

    public void update() {
        y += yVelocity;
    }
}
