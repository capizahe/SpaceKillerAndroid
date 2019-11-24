package com.movil.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.movil.main.Model.Bullet;
import com.movil.main.Model.Meteor;
import com.movil.main.Model.SpaceShip;

import java.util.Random;

public  class Game extends SurfaceView implements Runnable {


    private boolean isPlaying;
    private Paint paint,textPaint;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private int points=0;
    private Random random;
    private boolean start_points;
    private int counter=0;
    //Game objects
    private SpaceShip spaceShip;
    private Bullet bullet;
    private Meteor meteor;

    private boolean shoot;
    private boolean justshooted = true;
    private float screenWith,screenHeight;

    private float spaceshippos;




    public Game(Context context , float screenwidth, float screenheight){
        super(context);
        points=0;
        random= new Random();
        this.screenWith=screenwidth;
        this.screenHeight=screenheight;

        this.spaceShip = new SpaceShip(context,screenwidth,screenheight);
        this.bullet = new Bullet(context,screenwidth,screenheight);
        this.meteor = new Meteor(context,screenwidth,screenheight);

        paint = new Paint();
        textPaint = new Paint();

        textPaint.setTextSize(60);
        textPaint.setFakeBoldText(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        holder = getHolder();
        isPlaying = true;

    }

    private void paintFrame() {
        if (holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.rgb(0,87,75));
            spaceshippos = spaceShip.getPositionY();
            canvas.drawBitmap(meteor.getSpriteMeteor(),meteor.getPositionX(),meteor.getPositionY(),paint);
            canvas.drawBitmap(spaceShip.getSpriteSpaceShip(),spaceShip.getPositionX(),spaceShip.getPositionY(),paint);
            if(shoot) {
                if (!bullet.isGone()) {
                    if (justshooted) {
                        bullet.setPositionY(spaceShip.getPositionY());
                        justshooted = false;
                    } else
                        canvas.drawBitmap(bullet.getSpriteBullet(), bullet.getPositionX(), bullet.getPositionY(), paint);
                } else {
                   resetBullet();
                }
            }
            holder.unlockCanvasAndPost(canvas);

        }

    }


    @Override
    public void run() {
        while (isPlaying) {
            updateInfo();
            paintFrame();
            spaceshippos = spaceShip.getPositionY();

        }
    }
    private void updateInfo() {
        spaceShip.move();
        meteor.move();
        spaceshippos = spaceShip.getPositionY();
        if(shoot) bullet.move();
        if(meteor.isGone){
            resetMeteor();
        }

    }

    private void resetBullet(){
        bullet = new Bullet(getContext(), screenWith, screenHeight);
        bullet.setPositionY(spaceshippos);
        this.shoot = false;
    }

    private void resetMeteor(){
        this.meteor = new Meteor(getContext(),screenWith,screenHeight);
    }

    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x1 = event.getX();
        float y1 = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                spaceShip.setMoving(false);
                spaceshippos = spaceShip.getPositionY();
                break;
            case MotionEvent.ACTION_DOWN:

                if(x1 < screenWith/2) {
                    //Click right side (move)
                    spaceShip.setMoving(true);
                }else{
                    //Click left side (shoot)
                    shoot = true;
                    spaceShip.setMoving(false);
                    spaceshippos = spaceShip.getPositionY();
                }
                break;
        }
        return true;

    }
}
