/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activities;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Erik Brendel
 */
public class ACTIVITY_VORLAGE_ZUM_KOPIEREN extends GameActivity {

    public ACTIVITY_VORLAGE_ZUM_KOPIEREN(ActivityManager manager) {
        super(manager);
    }

    @Override
    public void render(Graphics2D g, int width, int height) {
    }


    @Override
    public boolean onKeyPressed(KeyEvent e) {
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
    }

    @Override
    public void onExit() {
        
    }
}
