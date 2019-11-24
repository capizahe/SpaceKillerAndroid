package com.movil.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.movil.main.Model.*;

import java.util.Random;

public  class Game extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private boolean isPlaying;
    private Paint paint,textPaint;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private int points;
    private int counter=0;
    //Game objects
    private SpaceShip spaceShip;
    private Bullet bullet;
    private Meteor meteor;
    private EnemySpaceShip enemySpaceShip;
    private EnemyBullet enemyBullet;

    private boolean shoot;
    private boolean justshooted = true;
    private boolean enemyjustshooted = true;
    private float screenWith,screenHeight;

    private float spaceshippos;
    private boolean start;

    private boolean surfaceReady;
    private Context context;


    private boolean soundones_laser,soundones_meteor;
    public Game(Context context , float screenwidth, float screenheight){
        super(context);
        points=5;
        this.context = context;
        this.screenWith=screenwidth;
        this.screenHeight=screenheight;
        this.spaceShip = new SpaceShip(context,screenwidth,screenheight);
        this.bullet = new Bullet(context,screenwidth,screenheight);
        this.meteor = new Meteor(context,screenwidth,screenheight);
        this.enemySpaceShip = new EnemySpaceShip(context,screenwidth,screenheight);
        this.enemyBullet = new EnemyBullet(context,screenwidth,screenheight);


        this.paint = new Paint();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                textPaint = new Paint();
                textPaint.setTextSize(60);
                textPaint.setFakeBoldText(true);
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                isPlaying = false;
            }
        }
        );

        holder = getHolder();


    }



    private void paintFrame() {

        if (holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.rgb(0,87,75));
            //Enemies
            canvas.drawBitmap(meteor.getSpriteMeteor(), meteor.getPositionX(), meteor.getPositionY(), paint);
            canvas.drawBitmap(enemySpaceShip.getSpriteEnemySpaceShip(), enemySpaceShip.getPositionX(), enemySpaceShip.getPositionY(), paint);
            setEnemyBullet();
            canvas.drawBitmap(enemyBullet.getSpriteEnemyBullet(), enemyBullet.getPositionX(), enemyBullet.getPositionY(), paint);


            //Hero
            canvas.drawBitmap(spaceShip.getSpriteSpaceShip(), spaceShip.getPositionX(), spaceShip.getPositionY(), paint);
            canvas.drawText("Life:" + spaceShip.getLive() +"/100", 50, screenHeight - 20, textPaint);
            canvas.drawText("SCORE:" + points +"/500", screenWith - 300, screenHeight - 20, textPaint);
            //Bullet
                if (shoot) {
                    if (justshooted) {
                        bullet.setPositionY(spaceshippos);
                        canvas.drawBitmap(bullet.getSpriteBullet(), spaceShip.getPositionX(), spaceshippos, paint);
                        justshooted = false;
                    } else {
                        bullet.setPositionY(spaceshippos);
                        canvas.drawBitmap(bullet.getSpriteBullet(), bullet.getPositionX(), bullet.getPositionY(), paint);
                    }
                }

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void setEnemyBullet() {
        if(enemyjustshooted) {
            this.enemyBullet.setPositionX(this.enemySpaceShip.getPositionX());
            this.enemyBullet.setPositionY(this.enemySpaceShip.getPositionY());
            enemyjustshooted=false;
        }
    }

    @Override
    public void run() {
        while (isPlaying) {
            updateInfo();
            paintFrame();
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                while(isPlaying) spaceshippos = spaceShip.getPositionY();
            }
        });

    }
    private void updateInfo() {

                spaceShip.move();
                meteor.move();
                enemySpaceShip.move();
                enemyBullet.move();

                if(start){
                    if(shoot) {
                        if(!soundones_laser) {SoundEffects.laserShotSound(); soundones_laser =true;}
                        bullet.move();
                        if(bullet.intersectsWithMeteor(meteor)){
                            resetMeteor();
                            resetBullet();
                            points+=15;
                            if(!soundones_meteor) {SoundEffects.meteorExplosionSound();soundones_meteor = true;}
                        }
                        if(bullet.intersectsWithObject(enemySpaceShip)){
                            resetEnemy();
                            resetBullet();
                            points+=15;
                            if(!soundones_meteor) {SoundEffects.meteorExplosionSound();soundones_meteor = true;}
                        }
                        else if(bullet.isGone()){
                                resetBullet();
                        }

                    }

                    else if(!enemySpaceShip.isGone && enemyBullet.isGone()){
                        resetEnemyBullet();
                    }

                    else if(enemySpaceShip.isGone){
                        if(enemyBullet.isGone()){
                            resetEnemyBullet();
                        }
                        resetEnemy();
                        points-=5;
                    }
                   else if(meteor.isGone){
                        resetMeteor();
                        points-=5;
                    }
                    else if(enemyBullet.intersectsWithObject(spaceShip)){
                        resetEnemyBullet();
                        spaceShip.setLive(spaceShip.getLive()-10);
                    }

                    if(spaceShip.getLive() <= 0) loose("¡Te quedaste sin vida!");
                    else if(spaceShip.intersectsWithObject(meteor)) loose("Te golpéo un asteroide");
                    else if(spaceShip.intersectsWithObject(enemySpaceShip))loose("Te golpéo una nave alienigena");
                    else if(points >= 500) victory();

                }



    }

    private  void loose(final String lose){
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, lose, Toast.LENGTH_SHORT).show();
                ((Activity) context).finish();
            }
        });
    }


    private  void victory(){
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "¡GANASTE!", Toast.LENGTH_SHORT).show();
                ((Activity) context).finish();
            }
        });
    }

    private void resetBullet(){
        bullet = new Bullet(getContext(), screenWith, screenHeight);
        bullet.setPositionY(spaceshippos);
        this.shoot = false;
        this.soundones_laser = false;
    }

    private void resetMeteor(){
        this.meteor = new Meteor(getContext(),screenWith,screenHeight);
        this.soundones_meteor = false;

    }

    private void resetEnemyBullet(){
        this.enemyBullet = new EnemyBullet(getContext(),screenWith,screenHeight);
        this.soundones_meteor = false;
        this.enemyjustshooted =true;

    }

    private void resetEnemy(){
        this.enemySpaceShip = new EnemySpaceShip(getContext(),screenWith,screenHeight);
        this.soundones_meteor = false;

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
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                spaceShip.setMoving(false);
                break;
            case MotionEvent.ACTION_DOWN:
                counter++;
                if(counter >= 3){
                    isPlaying = true;
                    start = true;
                    if(x1 < screenWith/2) {
                    //Click right side (move)
                        spaceShip.setMoving(true);
                    }else{
                    //Click left side (shoot)
                        shoot = true;
                        spaceShip.setMoving(false);
                    }
                }
                 break;

        }
        spaceshippos = spaceShip.getPositionY();
        return true;

    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
        if(gameplayThread != null){
            try{
                gameplayThread.join();
            }catch (InterruptedException e){

            }
        }
        surfaceReady = true;
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
