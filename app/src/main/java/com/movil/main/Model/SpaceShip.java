package com.movil.main.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.movil.main.R;

public class SpaceShip {

    public static final float INIT_X =50;
    public static final float INIT_Y =50;
    public static final int SPRITE_SIZE_WIDTH =300;
    public static final int SPRITE_SIZE_HEIGTH=200;
    public static final float GRAVITY_FORCE=10;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteSpaceShip;
    private boolean isMoving;

    public int live = 100;

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public SpaceShip(Context context, float ScreenWidth, float ScreenHeight){
        speed = 1;
        positionX = INIT_X;
        positionY = INIT_Y;
        isMoving = false;

        Bitmap original_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.craft);
        spriteSpaceShip = Bitmap.createScaledBitmap(original_bitmap,SPRITE_SIZE_WIDTH,SPRITE_SIZE_HEIGTH,false);

        this.maxX = ScreenWidth - (spriteSpaceShip.getWidth()/2);
        this.maxY = ScreenHeight - spriteSpaceShip.getHeight();
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

    public Bitmap getSpriteSpaceShip() {
        return spriteSpaceShip;
    }

    public void setSpriteSpaceShip(Bitmap spriteSpaceShip) {
        this.spriteSpaceShip = spriteSpaceShip;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void move(){

        if(isMoving){
            speed+= 5;
        }else{
            speed-= 5;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        this.positionY -= speed - GRAVITY_FORCE;

        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }

    }

    public boolean intersectsWithObject(Object  obj) {
        if(obj instanceof  Meteor){ obj=(Meteor) obj;
         if ((this.positionY> ((Meteor)obj).getPositionY())) {
            if ((this.positionY <= ((Meteor)obj).getPositionY() + EnemySpaceShip.SPRITE_SIZE_HEIGTH)) {
                if(this.positionX+SPRITE_SIZE_WIDTH/2 >= ((Meteor)obj).getPositionX()) {
                    Log.i("Interseccion", "¡Choco!");
                    return true;
                }
            }
            return false;
            }
        }else if(obj instanceof  EnemySpaceShip){
            if ((this.positionY> ((EnemySpaceShip)obj).getPositionY())) {
                if ((this.positionY <= ((EnemySpaceShip)obj).getPositionY() + EnemySpaceShip.SPRITE_SIZE_HEIGTH)) {
                    if(this.positionX+SPRITE_SIZE_WIDTH/2>= ((EnemySpaceShip)obj).getPositionX()) {
                        Log.i("Interseccion", "¡Choco!");
                        return true;
                    }
                }
                return false;
            }
        }

        return false;
    }
}
