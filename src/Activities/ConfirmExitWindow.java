/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import unknownreborn.UnknownReborn;
import static util.GraphicsMethods.drawInnerBounds;
import util.StringMetrics;

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
        Point size = new Point(400, 200);
        Point start = new Point((width - size.x) / 2, (height - size.y) / 2);
        g.setColor(new Color(0, 0, 0, 230));
        g.fillRect(start.x, start.y, size.x, size.y);
        g.setColor(Color.WHITE);
        drawInnerBounds(g, start, size, 5);

        Dimension msgDimensions = StringMetrics.getBounds(g, msg);
        g.drawString(msg, (width - msgDimensions.width) / 2, start.y + 50);
    }

    @Override
    public boolean onKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                UnknownReborn.isRunning = false;
                break;

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
