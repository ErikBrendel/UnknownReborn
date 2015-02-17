/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import util.ImageLoader;

/**
 *
 * @author Erik Brendel
 */
public class MainMenueActivity extends GameActivity {

    private BufferedImage bgImage = null;

    public MainMenueActivity(ActivityManager manager) {
        super(manager);
    }
    
    @Override
    public void render(Graphics2D g, int width, int hight) {
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
    }

    @Override
    public void onKeyReleased(KeyEvent e) {
    }

    @Override
    public void update() {
    }

    @Override
    public void onEnter() {
        bgImage = ImageLoader.get().image("/gui/mainBG.jpg", "Loading mainMenue BG");
    }

    @Override
    public void onExit() {
        bgImage = null;
    }
    
}
