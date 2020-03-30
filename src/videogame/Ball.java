/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.util.Date;

/**
 * @author oscarrodriguez
 */
public class Ball extends Item {

    private static Date time0 = new Date();
    private double seconds;
    private int x0;
    private int y0;
    private double xVelocity;
    private double yVelocity;
    private int points;
    private int lives;
    private int consecutiveMisses;
    private int consecutivePoints;
    private boolean flying;
    private Game game;

    public Ball(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);

        this.points = 0;
        this.lives = 5;
        this.consecutiveMisses = 0;
        this.consecutivePoints = 0;
        this.seconds = 0;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.game = game;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
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

    public void setFlying(boolean flying) {
        if (flying) {
            setSeconds(0);
            time0 = new Date();
        }
        this.flying = flying;
    }

    public boolean getFlying() {
        return flying;
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

    private boolean isMouseInsideBall() {
        return
                game.getMouseManager().getX() >= getX() - 50 && game.getMouseManager().getX() <= getX() + getWidth() + 50 &&
                        game.getMouseManager().getY() >= getY() - 50 && game.getMouseManager().getY() <= getY() + getHeight() + 50;
    }

    @Override
    public void tick() {
        if (flying) setSeconds((new Date().getTime() - time0.getTime()) / 1000.0);
        if (isMouseInsideBall()) {
            setX(game.getMouseManager().getX() - 25);
            setY(game.getMouseManager().getY() - 25);
        }

        if (getConsecutiveMisses() == 3) {
            setConsecutiveMisses(0);
            setLives(getLives() - 1);
        }

        if (getConsecutivePoints() == 50) {
            setConsecutivePoints(0);
            setLives(getLives() + 1);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
}
