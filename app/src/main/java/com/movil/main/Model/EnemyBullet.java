package com.movil.main.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.movil.main.R;

public class EnemyBullet {
    public static final float INIT_X = 100;
    public static final float INIT_Y = 100;
    public static final int SPRITE_SIZE_WIDTH = 100;
    public static final int SPRITE_SIZE_HEIGTH = 100;
    public static final float GRAVITY_FORCE = 10;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteEnemyBullet;
    private boolean isMoving;


    public EnemyBullet(Context context, float ScreenWidth, float ScreenHeight) {
        speed = 10;
        positionX = INIT_X;
        positionY = INIT_Y;
        isMoving = false;

        Bitmap original_bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemybullet);
        spriteEnemyBullet = Bitmap.createScaledBitmap(original_bitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = ScreenWidth - (spriteEnemyBullet.getWidth() / 2);
        this.maxY = ScreenHeight - spriteEnemyBullet.getHeight();
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

    public Bitmap getSpriteEnemyBullet() {
        return spriteEnemyBullet;
    }

    public void setSpriteEnemyBullet(Bitmap spriteEnemyBullet) {
        this.spriteEnemyBullet = spriteEnemyBullet;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void move() {
        this.positionX -= speed;
    }

    public boolean isGone() {
        return this.positionX < 0;
    }

    public boolean intersectsWithObject(Object obj){
        {
            if (obj instanceof SpaceShip) {
                obj = (SpaceShip) obj;
                if ((this.positionY >= ((SpaceShip) obj).getPositionY())) {
                    if ((this.positionY <= ((SpaceShip) obj).getPositionY() + EnemySpaceShip.SPRITE_SIZE_HEIGTH)) {
                        if (this.positionX <= ((SpaceShip) obj).getPositionX()) {
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
}
