/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * @author oscarrodriguez
 */
public final class Game implements Runnable {
    private BufferStrategy bs;          // to have several buffers when displaying
    private Graphics g;                 // to paint objects
    private Display display;            // to display in the game
    String title;                       // title of the window
    private int width;                  // width of the window
    private int height;                 // height of the window
    private Thread thread;              // thread to create the game
    private boolean running;            // to set the game
    private KeyManager keyManager;      // to manage the keyboard
    private MouseManager mouseManager;  // to manage the mouse
    private Ball ball;                  // to use a ball
    private Hole hole;                  // to use a hole
    private ThrowZone throwZone;        // to use the throwZone

    /**
     * to create title, width and height and set the game is still not running
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        throwZone = new ThrowZone(0, 0, 200, getHeight(), this);
        ball = new Ball(getWidth() / 2 + 50, getHeight() / 2, 50, 50, this);
        hole = new Hole(700, 360, 80, 100);
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();

        display.getJframe().addMouseListener(mouseManager);
        display.getJframe().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 1000;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        keyManager.tick();
        ball.tick();
        hole.tick();

        if (ball.collision(throwZone) && !mouseManager.isLeft()) {

            if (!ball.getFlying()) {
                ball.setFlying(true);

                int totalVelocity = 1600;
                double percentageOfYImpulse = ball.getY() / (throwZone.getHeight() * 1.0);
                double percentageOfXImpulse = (throwZone.getWidth() - ball.getX()) / (throwZone.getWidth() * 1.0);

                double newYVelocity = percentageOfYImpulse * totalVelocity * Math.sin(45) * -1;
                double newXVelocity = percentageOfXImpulse * totalVelocity * Math.cos(45);

                ball.setyVelocity(newYVelocity);
                ball.setxVelocity(newXVelocity);

                ball.setX0(ball.getX());
                ball.setY0(ball.getY());
            }
        }

        if (ball.getFlying() && ball.getX() <= getWidth() + 50 && ball.getY() <= getHeight() + 50) {
            double gravity = 9.81;
            int multiplier = 160;
            int newX = (int) (ball.getX0() + ball.getxVelocity() * ball.getSeconds());
            int newY = (int) (ball.getY0() + ball.getyVelocity() * ball.getSeconds() + (gravity * multiplier) * Math.pow(ball.getSeconds(), 2) / 2);
            ball.setX(newX);
            ball.setY(newY);
        } else if (ball.getFlying()) {
            ball.setX(getWidth() / 2 + 50);
            ball.setY(getHeight() / 2);
            ball.setFlying(false);
            Assets.miss.play();
            ball.setConsecutiveMisses(ball.getConsecutiveMisses() + 1);
        }

        if(ball.collision(hole)){
            ball.setX(getWidth() / 2 + 50);
            ball.setY(getHeight() / 2);
            ball.setFlying(false);
            Assets.enter.play();
            ball.setPoints(ball.getPoints() + 10);
        }
    }

    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            if (ball.getLives() > 0) {
                g = bs.getDrawGraphics();
                g.drawImage(Assets.background, 0, 0, width, height, null);
                throwZone.render(g);
                ball.render(g);
                hole.render(g);

                g.setColor(Color.YELLOW);
                g.setColor(Color.YELLOW);
                g.drawString("VIDAS: " + ball.getLives(), 400, getHeight() - 20);
                g.drawString("PUNTOS: " + ball.getPoints(), 600, getHeight() - 20);
            } else {
                // Finish game logic
            }

            bs.show();
            g.dispose();
        }
    }

    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}
