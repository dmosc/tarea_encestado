/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;
import java.util.Date;

/**
 *
 * @author oscarrodriguez
 */
public class Ball extends Item {

    private final static Date time0 = new Date();
    private final static int angle = 45;
    private int seconds;
    private int x0;
    private int y0;
    private double xVelocity;
    private double yVelocity;
    private final double gravity;
    private Game game;

    public Ball(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        
        seconds = (int) ((new Date().getTime() - time0.getTime()) / 1000);
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.gravity = 9.81;
        this.game = game;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity / 100;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    private boolean isMouseInsideBall () {
        return 
            game.getMouseManager().getX() >= getX() - 50 && game.getMouseManager().getX() <= getX() + getWidth() + 50 &&
            game.getMouseManager().getY() >= getY() - 50 && game.getMouseManager().getY() <= getY() + getHeight() + 50;
    }
    
    public void throwBall () {
        setX(getX0() + (int) getxVelocity() * seconds);
        setY( getY0() + 5 );
        
        System.out.println(getY() + 5);
    }
    
    @Override
    public void tick() {
        setSeconds( (int) ((new Date().getTime() - time0.getTime()) / 1000) );
        System.out.println( seconds );
        if ( isMouseInsideBall() ) {
            setX(game.getMouseManager().getX() - 25);
            setY(game.getMouseManager().getY() - 25);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
}
