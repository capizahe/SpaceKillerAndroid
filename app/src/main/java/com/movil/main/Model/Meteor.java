package com.movil.main.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.movil.main.R;

import java.util.Random;

public class Meteor {

    public float INIT_Y;
    public static final int SPRITE_SIZE_WIDTH =200;
    public static final int SPRITE_SIZE_HEIGTH=200;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteMeteor;
    private boolean isMoving;
    public boolean isGone;



    public Meteor(Context context, float ScreenWidth, float ScreenHeight){
        speed = 5;
        Random random = new Random();
        isMoving = false;

        Bitmap original_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor);
        spriteMeteor = Bitmap.createScaledBitmap(original_bitmap,SPRITE_SIZE_WIDTH,SPRITE_SIZE_HEIGTH,false);

        this.maxX = ScreenWidth - (spriteMeteor.getWidth()/2);
        this.maxY = ScreenHeight - spriteMeteor.getHeight();
        positionX = this.maxX;
        positionY = this.maxY - random.nextInt((int)this.maxY);
    }


    public int getMIN_SPEED() {
        return MIN_SPEED;
    }

    public int getMAX_SPEED() {
        return MAX_SPEED;
    }

    public float getMaxY() {
        return maxY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public float getMaxX() {
        return maxX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getSpriteMeteor() {
        return spriteMeteor;
    }

    public void setSpriteMeteor(Bitmap spriteMeteor) {
        this.spriteMeteor = spriteMeteor;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void move(){
        if(this.positionX <=0) isGone = true;
        this.positionX-=speed;
    }
}

