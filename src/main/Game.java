/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 *
 * @author oscarrodriguez
 */
public final class Game implements Runnable {

    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private boolean ballFlying;        // check if ball should be in flying mode
    private KeyManager keyManager;  // to manage the keyboard
    private MouseManager mouseManager;  // to manage the mouse
    private Ball ball;          // to use a ball
    private ThrowZone throwZone;          // to use the throwZone

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param string
     * @param i
     * @param i1
     */
    public Game(String title, int width, int height) {
        
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        ballFlying = false;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        throwZone = new ThrowZone(20, 20, 200, 460, this);
        ball = new Ball(getWidth() / 2 + 50, getHeight() / 2, 50, 50, this);
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
    
    public KeyManager getKeyManager() {
        return keyManager;
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
        int fps = 50;
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
        
        if ( ball.collision( throwZone ) && !mouseManager.isLeft() ) {
            if ( !ballFlying ) {
                ballFlying = true;
                ball.setxVelocity( ( this.getWidth() - ball.getX() ) * Math.cos( 45 ) * 10 );
                ball.setyVelocity( ball.getY() * -1 * Math.sin( 45 ) );
                ball.setX0( ball.getX() );
                ball.setY0( ball.getY() );
            }
        }
        
        if ( ballFlying && ball.getX() <= getWidth() - 50 && ball.getY() <= getHeight() - 50 ) {
//            System.out.println( "X: " + ball.getX0() + (int) ball.getxVelocity() * ball.getSeconds() );
//            System.out.println( "Y: " + (int) (ball.getY0() + ball.getyVelocity() * ball.getSeconds() + 9.81 * Math.pow(ball.getSeconds(), 2) / 2) );
            ball.setX( ball.getX0() + (int) ball.getxVelocity() * ball.getSeconds() );
            ball.setY((int) (ball.getY0() + ball.getyVelocity() * ball.getSeconds() + 9.81 * Math.pow(ball.getSeconds(), 2) / 2));
        } else {
            ballFlying = false;
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
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            throwZone.render(g);
            ball.render(g);

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
