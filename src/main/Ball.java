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
    private double seconds;
    private int x0;
    private int y0;
    private double xVelocity;
    private double yVelocity;
    private int points;
    private int lives;
    private int consecutiveMisses;
    private int consecutivePoints;
    private Game game;

    public Ball(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);

        this.points = 0;
        this.lives = 5;
        this.consecutiveMisses = 0;
        this.consecutivePoints = 0;
        seconds = new Date().getTime() - time0.getTime() / 1000.0;
        this.xVelocity = 0;
        this.yVelocity = 0;
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

    public double getSeconds() {
        return seconds;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getConsecutiveMisses() {
        return consecutiveMisses;
    }

    public void setConsecutiveMisses(int consecutiveMisses) {
        this.consecutiveMisses = consecutiveMisses;
    }

    public int getConsecutivePoints() {
        return consecutivePoints;
    }

    public void setConsecutivePoints(int consecutivePoints) {
        this.consecutivePoints = consecutivePoints;
    }

    private boolean isMouseInsideBall () {
        return 
            game.getMouseManager().getX() >= getX() - 50 && game.getMouseManager().getX() <= getX() + getWidth() + 50 &&
            game.getMouseManager().getY() >= getY() - 50 && game.getMouseManager().getY() <= getY() + getHeight() + 50;
    }
    
    public void throwBall () {
        setX((int) (getX0() + (int) getxVelocity() * seconds));
        setY((getY0() + 5));
        
        System.out.println(getY() + 5);
    }
    
    @Override
    public void tick() {
        setSeconds( (new Date().getTime() - time0.getTime()) / 1000.0 );
        System.out.println( seconds );
        if ( isMouseInsideBall() ) {
            setX(game.getMouseManager().getX() - 25);
            setY(game.getMouseManager().getY() - 25);
        }

        if ( consecutiveMisses == 3 ) {
            setConsecutiveMisses( 0 );
            setLives( getLives() - 1 );
        }

        if ( consecutivePoints == 50 ) {
            setConsecutivePoints( 0 );
            setLives( getLives() + 1 );
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
}
