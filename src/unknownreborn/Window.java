/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import javax.swing.JFrame;

/**
 *
 * @author Erik Brendel
 */
public class Window {
    JFrame window;
    /**
     * fenster laden
     */
    public void init() {
        window = new JFrame();
        window.setUndecorated(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     *  fenster anzeigen
     */
    public void show() {
        window.setVisible(true);
    }
}
