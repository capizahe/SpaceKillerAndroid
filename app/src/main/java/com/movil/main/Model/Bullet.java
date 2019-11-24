package com.movil.main.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.movil.main.R;

public class Bullet {

    public static final float INIT_X =100;
    public static final float INIT_Y =100;
    public static final int SPRITE_SIZE_WIDTH =100;
    public static final int SPRITE_SIZE_HEIGTH=100;
    public static final float GRAVITY_FORCE=10;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap bullet;
    private boolean isMoving;


    public Bullet(Context context, float ScreenWidth, float ScreenHeight){
        speed = 70;
        positionX = INIT_X;
        positionY = INIT_Y;
        isMoving = false;

        Bitmap original_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot);
        bullet = Bitmap.createScaledBitmap(original_bitmap,SPRITE_SIZE_WIDTH,SPRITE_SIZE_HEIGTH,false);

        this.maxX = ScreenWidth - (bullet.getWidth()/2);
        this.maxY = ScreenHeight - bullet.getHeight();
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

    public Bitmap getSpriteBullet() {
        return bullet;
    }

    public void setSpriteBullet(Bitmap spriteSpaceShip) {
        this.bullet = spriteSpaceShip;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void move(){
        this.positionX += speed;
    }

    public boolean isGone(){
        return this.positionX > this.maxX;
    }

    public boolean intersectsWithMeteor(Meteor meteor){
      //  if(this.y)
        if(this.positionX == meteor.getPositionX())return true;
        return false;
    }


}
