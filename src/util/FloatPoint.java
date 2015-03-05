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
public class FloatPoint {
    public float x;
    public float y;
    public FloatPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public FloatPoint(Point p) {
        x = p.x;
        y = p.y;
    }
}
