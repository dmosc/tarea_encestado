/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Ernesto García
 */
public class Hole extends Item {
    public Hole(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.hole, getX(), getY(), getWidth(), getHeight(), null);
    }
}
