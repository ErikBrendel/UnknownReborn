/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Graphics;
import java.awt.Graphics2D;
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
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        manager = new ActivityManager();
        panel = new GamePanel(manager);
    }

    /**
     *  fenster anzeigen
     */
    public void show() {
        window.setVisible(true);
    }
    
    
    
    
    
    /**
     * das JPanel im JFrame, in dem alles passiert
     */
    private class GamePanel extends JPanel {
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
    }
}
