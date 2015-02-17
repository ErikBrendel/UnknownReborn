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

    public MainMenueActivity(ActivityManager manager) {
        super(manager);
    }

    private BufferedImage bgImage = null;

    @Override
    public void render(Graphics2D g, int width, int height) {
        if (!(bgImage.getWidth() == width && bgImage.getHeight() == height)) { //sollte nur das erste mal sein
            //bild auf Bildschirm skalieren
            bgImage = ImageLoader.getScaledImage(bgImage, width, height, ImageLoader.MODE_FINE);
        }
        g.drawImage(bgImage, 0, 0, null);
    }

    int selectedButton = 0;
    final int maxSelectedButton = 2;

    @Override
    public void onKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                selectedButton--;
                if (selectedButton < 0) {
                    selectedButton = maxSelectedButton;
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                selectedButton++;
                if (selectedButton > maxSelectedButton) {
                    selectedButton = 0;
                }
                break;
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                launchButtonEvent(selectedButton);
                break;
            case KeyEvent.VK_ESCAPE:
                launchButtonEvent(maxSelectedButton); //last button--> exit --> exits game

        }
        System.out.println(selectedButton);
    }

    private void launchButtonEvent(int buttonID) {
        switch (buttonID) {
            case maxSelectedButton:
                System.exit(0);
        }
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
