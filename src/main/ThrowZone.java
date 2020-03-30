/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics;

/**
 *
 * @author oscarrodriguez
 */
public class ThrowZone extends Item {
    private int x;
    private int y;
    private int width;
    private int height;
    private Game game;

    public ThrowZone(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.game = game;
    }
    
    @Override
    public void tick() {}

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.throwZone, x, y, width, height, null);
    }
}
