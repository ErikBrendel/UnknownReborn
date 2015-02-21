/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Erik Brendel, Jonas Hanke
 */
public class Window extends JFrame {

    private GamePanel panel;
    private long fps = 0;

    public Window(ActivityManager manager, String name) {
        super(name);
        panel = new GamePanel(manager);
    }

    /**
     * fenster laden
     */
    public void initialisation() {
        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel);
        this.addKeyListener(panel);
        //remove cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        this.getContentPane().setCursor(blankCursor);
    }

    /**
     * fenster anzeigen
     */
    public void makeVisible() {
        this.setVisible(true);
    }

    public void updateFPS(long fps) {
        this.fps = fps;
    }

    /**
     * das JPanel im JFrame, in dem alles passiert
     */
    private class GamePanel extends JPanel implements KeyListener {

        ActivityManager manager;

        /**
         * den activityManager übergeben
         *
         * @param manager der manager
         */
        public GamePanel(ActivityManager manager) {
            this.manager = manager;
        }

        /**
         * nutzt die render-methode des activityManagers
         */
        @Override
        public void paint(Graphics g) {
            manager.render((Graphics2D) g, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.drawString("FPS: " + Long.toString(fps), 20, 10);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            manager.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            manager.keyReleased(e);
        }

        @Override
        public void keyTyped(KeyEvent e) {
            //do nothing
        }
    }
}