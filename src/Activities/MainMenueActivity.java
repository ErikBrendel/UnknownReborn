/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activities;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import unknownreborn.MapLoader;
import util.DoublePoint;
import util.ImageLoader;
import util.MP3Player;

/**
 *
 * @author Erik Brendel
 */
public class MainMenueActivity extends GameActivity {

    public MainMenueActivity(ActivityManager manager) {
        super(manager);
    }

    private BufferedImage bgImage = null;
    private HashMap<String, BufferedImage> buttonImages = null;
    private Point buttonBeenden = null;
    private Point buttonOptionen = null;
    private Point buttonNeuesSpiel = null;
    private Point buttonSize = null;
    MP3Player background;

    @Override
    public void render(Graphics2D g, int width, int height) {
        g.drawImage(bgImage, 0, 0, null);

        // alle Knöpfe malen
        if (selectedButton == 0) {
            g.drawImage(buttonImages.get("neuesspielH"), buttonNeuesSpiel.x, buttonNeuesSpiel.y, null);
        } else {
            g.drawImage(buttonImages.get("neuesspiel"), buttonNeuesSpiel.x, buttonNeuesSpiel.y, null);
        }
        if (selectedButton == 1) {
            g.drawImage(buttonImages.get("optionenH"), buttonOptionen.x, buttonOptionen.y, null);
        } else {
            g.drawImage(buttonImages.get("optionen"), buttonOptionen.x, buttonOptionen.y, null);
        }
        if (selectedButton == 2) {
            g.drawImage(buttonImages.get("beendenH"), buttonBeenden.x, buttonBeenden.y, null);
        } else {
            g.drawImage(buttonImages.get("beenden"), buttonBeenden.x, buttonBeenden.y, null);
        }
    }


    int selectedButton = 0;
    final int maxSelectedButton = 2;

    @Override
    public boolean onKeyPressed(KeyEvent e) {
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
        return true;
    }

    private void launchButtonEvent(int buttonID) {
        switch (buttonID) {
            case 0:
                ArrayList<Object> ol = new ArrayList<>();
                ol.add(MapLoader.loadMapFromResources("test"));
                ol.add(new DoublePoint(10, 10));
                manager.clearActivityStack();
                manager.showActivity("mapActivity", ol);
                break;
            case maxSelectedButton:
                manager.showActivity("confirmExitWindow", "Do you really want to exit?");
                break;
        }
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
        background = new MP3Player("/resources/sound/menueBG.mp3");// -> behoben :) mfg Erik
        background.play(true);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        bgImage = ImageLoader.getScaledImage(ImageLoader.get().image("/resources/images/menue/mainBG.jpg"), screen.width, screen.height, ImageLoader.MODE_FINE);

        buttonSize = new Point(600, 75);
        buttonBeenden = new Point((screen.width - buttonSize.x) / 2, screen.height - 110 - buttonSize.y);
        buttonOptionen = new Point(buttonBeenden.x, buttonBeenden.y - buttonSize.y);
        buttonNeuesSpiel = new Point(buttonOptionen.x, buttonOptionen.y - buttonSize.y);

        buttonImages = new HashMap<>();
        buttonImages.put("beenden", ImageLoader.get().image("/resources/images/menue/btn_beenden.png"));
        buttonImages.put("beendenH", ImageLoader.get().image("/resources/images/menue/btn_beendenHover.png"));
        buttonImages.put("neuesspiel", ImageLoader.get().image("/resources/images/menue/btn_neuesspiel.png"));
        buttonImages.put("neuesspielH", ImageLoader.get().image("/resources/images/menue/btn_neuesspielHover.png"));
        buttonImages.put("optionen", ImageLoader.get().image("/resources/images/menue/btn_optionen.png"));
        buttonImages.put("optionenH", ImageLoader.get().image("/resources/images/menue/btn_optionenHover.png"));
    }

    @Override
    public void onExit() {
        bgImage = null;
        buttonBeenden = null;
        buttonOptionen = null;
        buttonNeuesSpiel = null;
        buttonImages = null;
        // background.stop();
    }
}
