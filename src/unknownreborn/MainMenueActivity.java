/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
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
    private Point buttonBeenden = null;
    private Point buttonOptionen = null;
    private Point buttonNeuesSpiel = null;
    private Point buttonSize = null;

    @Override
    public void render(Graphics2D g, int width, int height) {
        g.drawImage(bgImage, 0, 0, null);

        // alle Knöpfe malen
        drawButton(g, buttonBeenden, buttonSize, "Beenden", selectedButton == 2);
        drawButton(g, buttonOptionen, buttonSize, "Optionen", selectedButton == 1);
        drawButton(g, buttonNeuesSpiel, buttonSize, "Neues Spiel", selectedButton == 0);
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
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        bgImage = ImageLoader.getScaledImage(ImageLoader.get().image("/gui/mainBG.jpg"), screen.width, screen.height, ImageLoader.MODE_FINE);
        
        buttonSize = new Point(400, 50);
        buttonBeenden = new Point((screen.width - buttonSize.x) / 2, screen.height - 60 - buttonSize.y);
        buttonOptionen = new Point(buttonBeenden.x, buttonBeenden.y - buttonSize.y);
        buttonNeuesSpiel = new Point(buttonOptionen.x, buttonOptionen.y - buttonSize.y);
    }

    @Override
    public void onExit() {
        bgImage = null;
        buttonBeenden = null;
        buttonOptionen = null;
        buttonNeuesSpiel = null;
    }

}
