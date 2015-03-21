/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import Activities.ActivityManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashSet;
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

    public void makeVisible() {
        this.setVisible(true);
    }

    public void updateFPS(long fps) {
        this.fps = fps;
    }

    private class GamePanel extends JPanel implements KeyListener {

        ActivityManager manager;

        public GamePanel(ActivityManager manager) {
            this.manager = manager;
        }

        @Override
        public void paint(Graphics g) {
            manager.render((Graphics2D) g, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
            g.drawString("FPS: " + Long.toString(fps), 20, 12);
        }

        HashSet<Integer> pressedKeys = new HashSet<>();

        @Override
        public void keyPressed(KeyEvent e) {
            if (!pressedKeys.contains(e.getKeyCode())) {
                pressedKeys.add(e.getKeyCode());
                manager.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            pressedKeys.remove(e.getKeyCode());
            manager.keyReleased(e);
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
}
