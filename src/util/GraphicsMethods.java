/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Erik Brendel
 */
public class GraphicsMethods {
        
    /**
     *
     * @param g the graphics object
     * @param p1 upper left corner
     * @param dimension size of rectangle
     * @param size thickness of border
     */
    public static void drawInnerBounds(Graphics2D g, Point p1, Point dimension, int size) {
        g.fillRect(p1.x, p1.y, dimension.x, size);
        g.fillRect(p1.x, p1.y, size, dimension.y);
        g.fillRect(p1.x, p1.y + dimension.y - size, dimension.x, size);
        g.fillRect(p1.x + dimension.x - size, p1.y, size, dimension.y);
    
    }
}
