/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author oscarrodriguez
 */
public abstract class Item {

    /**
     * Store x position
     */
    protected int x;

    /**
     * Store y position
     */
    protected int y;

    /**
     * Store width
     */
    protected int width;

    /**
     * Store height
     */
    protected int height;

    /**
     * Set the initial values to create the item
     *
     * @param i
     * @param i1
     * @param width
     * @param i3
     * @param i2
     * @param height
     */
    public Item(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get x value
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Get y value
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set x value
     *
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y value
     *
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param i
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @param i
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * To update positions of the item for every tick
     */
    public abstract void tick();

    /**
     * To paint the item
     *
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);

    /**
     *
     * @param o
     * @param i
     * @return
     */
    public boolean collision(Object i) {
        if (i instanceof Item) {
            Item item = (Item) i;
            Rectangle rItem = new Rectangle(getX(), getY(), getWidth(), getHeight());
            Rectangle rOther = new Rectangle(item.getX(), item.getY(), item.getWidth(), item.getHeight());
            
            return rItem.intersects(rOther);
        }

        return false;
    }
}
