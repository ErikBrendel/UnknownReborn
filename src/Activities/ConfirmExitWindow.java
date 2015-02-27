/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import unknownreborn.UnknownReborn;
import util.GraphicsMethods;

/**
 *
 * @author Erik Brendel
 */
public class ConfirmExitWindow extends GameActivity {

    public ConfirmExitWindow(ActivityManager manager) {
        super(manager);
    }

    private String msg;
    private boolean firstSelected;

    @Override
    public void render(Graphics2D g, int width, int height) {
        Point size = new Point(300, 200);
        Point start = new Point((width - size.x) / 2, (height - size.y) / 2);
        g.setColor(Color.BLACK);
        g.fillRect(start.x, start.y, size.x, size.y);
        g.setColor(Color.WHITE);
        GraphicsMethods.drawInnerBounds(g, start, size, 5);
    }

    @Override
    public boolean onKeyPressed(KeyEvent e) {
        UnknownReborn.isRunning = false;
        switch (e.getKeyCode()) {
        }
        return true;
    }

    @Override
    public boolean onKeyReleased(KeyEvent e) {
        return true;
    }

    @Override
    public void update() {
    }

    @Override
    public void onEnter(Object p) {
        firstSelected = false;
        msg = "Error no MSG";
        try {
            msg = (String) p;
        } catch (Exception e) {
            System.out.println("No String as parameter!");
        }
    }

    @Override
    public void onExit() {
        msg = null;
        firstSelected = false;
    }
}
