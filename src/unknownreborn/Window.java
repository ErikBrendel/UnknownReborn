/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

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
 * @author Erik Brendel
 */
public class Window {
    
    JFrame window;
    GamePanel panel;
    ActivityManager manager;
    
    /**
     * fenster laden
     */
    public void init() {
        window = new JFrame();
        window.setUndecorated(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        manager = new ActivityManager();
        manager.loadActivity(new MainMenueActivity(manager), "mainMenue");
        manager.showActivity("mainMenue", null);
        panel = new GamePanel(manager);
        window.addKeyListener(panel);
        window.setContentPane(panel);
        
        //remove cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        window.getContentPane().setCursor(blankCursor);
    }

    /**
     *  fenster anzeigen
     */
    public void show() {
        window.setVisible(true);
        new Thread() {
            public void run() {
                while(true) {
                    panel.repaint();
                    try {
                        Thread.sleep(10);
                    } catch (Exception ex) {
                        
                    }
                }
            }
        }.start();
    }
    
    
    
    
    
    /**
     * das JPanel im JFrame, in dem alles passiert
     */
    private class GamePanel extends JPanel implements KeyListener{
        ActivityManager manager;
        /**
         * den activityManager übergeben
         * @param manager der manager 
         */
        public GamePanel(ActivityManager manager) {
            this.manager = manager;
        }
        
        /**
         * nutzt die render-methode des activityManagers
         */
        @Override
        public void paint(Graphics gOrig) {
            Graphics2D g = (Graphics2D) gOrig;
            manager.render(g, getWidth(), getHeight());
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
