/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Point;

/**
 *
 * @author Erik Brendel
 */
public class DoublePoint {
    public double x;
    public double y;
    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public DoublePoint(Point p) {
        x = p.x;
        y = p.y;
    }
}
