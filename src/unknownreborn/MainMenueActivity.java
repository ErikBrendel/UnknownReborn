/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
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

        { // alle Knöpfe malen
            Point buttonSize = new Point(400, 50);
            Point b1Start = new Point((width - buttonSize.x) / 2, height - 30 - buttonSize.y);
            drawButton(g, b1Start, buttonSize, "Beenden", selectedButton == 2);

            Point b2Start = new Point(b1Start.x, b1Start.y - 30 - buttonSize.y);
            drawButton(g, b2Start, buttonSize, "Optionen", selectedButton == 1);

            Point b3Start = new Point(b2Start.x, b2Start.y - 30 - buttonSize.y);
            drawButton(g, b3Start, buttonSize, "Neues Spiel", selectedButton == 0);
        }
    }

    /**
     * draws one button with that grafics object
     *
     * @param g the graphics to paint with
     * @param start the upper left start corner of the button
     * @param size the size of the button
     * @param text the text to be displayed on
     * @param selected if it is selected
     */
    public void drawButton(Graphics2D g, Point start, Point size, String text, boolean selected) {
        g.setColor(Color.BLACK);
        if (selected) {
            g.setColor(Color.GRAY);
        }
        g.fillRect(start.x, start.y, size.x, size.y);
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
