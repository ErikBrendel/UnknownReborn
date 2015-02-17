/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        manager.addActivity(new MainMenueActivity(manager), "mainMenue");
        panel = new GamePanel(manager);
        panel.addKeyListener(panel);
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
