package com.movil.main.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.movil.main.R;

import java.util.Random;

public class EnemySpaceShip {

    public static final float INIT_X =100;
    public static final float INIT_Y =100;
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
    private Bitmap spriteEnemySpaceShip;

    public boolean isGone;

    public EnemySpaceShip(Context context, float ScreenWidth, float ScreenHeight){
        speed = 8;
        Random random = new Random();
        int[] spaceships = {R.drawable.enemy1,R.drawable.enemy2,R.drawable.enemy3,R.drawable.enemy4,R.drawable.enemy5};
        Bitmap original_bitmap = BitmapFactory.decodeResource(context.getResources(), spaceships[random.nextInt(spaceships.length)]);
        spriteEnemySpaceShip = Bitmap.createScaledBitmap(original_bitmap,SPRITE_SIZE_WIDTH,SPRITE_SIZE_HEIGTH,false);

        this.maxX = ScreenWidth - (spriteEnemySpaceShip.getWidth()/2);
        this.maxY = ScreenHeight - spriteEnemySpaceShip.getHeight();
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

    public Bitmap getSpriteEnemySpaceShip() {
        return spriteEnemySpaceShip;
    }

    public void setSpriteSpaceShip(Bitmap sriteEnemySpaceShip) {
        this.spriteEnemySpaceShip = spriteEnemySpaceShip;
    }

    public void move(){

        if(this.positionX <=0) isGone = true;
        this.positionX-=speed;
    }

    public boolean intersectsWithObject(Object obj) {
        if(obj instanceof  Meteor){ obj=(Meteor) obj;
            if ((this.positionY> ((Meteor)obj).getPositionY())) {
                if ((this.positionY <= ((Meteor)obj).getPositionY() + EnemySpaceShip.SPRITE_SIZE_HEIGTH)) {
                    if(this.positionX >= ((Meteor)obj).getPositionX()) {
                        Log.i("Interseccion", "¡Choco!");
                        return true;
                    }
                }
                return false;
            }
        }else if(obj instanceof  EnemySpaceShip){
            if ((this.positionY> ((EnemySpaceShip)obj).getPositionY())) {
                if ((this.positionY <= ((EnemySpaceShip)obj).getPositionY() + EnemySpaceShip.SPRITE_SIZE_HEIGTH)) {
                    if(this.positionX >= ((EnemySpaceShip)obj).getPositionX()) {
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
