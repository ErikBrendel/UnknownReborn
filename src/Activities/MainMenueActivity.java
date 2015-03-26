/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activities;

import Entity.Entity;
import Entity.MoveComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import unknownreborn.Map;
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
                final Map m = MapLoader.loadMapFromResources("test2");
                Entity follower = new Entity() {
                    @Override
                    public BufferedImage getImage() {
                        BufferedImage img = new BufferedImage(32, 64, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g = img.createGraphics();
                        g.setColor(Color.BLUE);
                        g.fillOval(10, 20, 12, 12);
                        g.setColor(Color.BLACK);
                        g.fillRect(15, 2, 2, 30);

                        return img;
                    }

                    @Override
                    public DoublePoint getDimensions() {
                        return new DoublePoint(1, 2);
                    }
                };
                follower.setMoveComponent(new MoveComponent() {
                    public void onTick() {
                        DoublePoint old = getLocation();
                        DoublePoint playerLoc = m.getPlayerEntity().getLocation();
                        DoublePoint newLoc = new DoublePoint(old.x * 0.999 + playerLoc.x * 0.001, old.y * 0.999 + playerLoc.y * 0.001);
                        if (m.isWalkable(newLoc, null)) {
                            setLocation(newLoc);
                        }
                    }
                });
                follower.setLocation(new DoublePoint(19,27));

                m.addEntity(follower);

                manager.clearActivityStack();
                manager.showActivity("mapActivity", m);
                break;
            case 1:
                manager.showActivity("textBoxActivity", "Es gibt leider noch keine Optionen. Tut mir sehr leid. Aber diese Textbox sieht doch schon gut aus, oder?");
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
        MP3Player.addSource("menueBG", "menueBG.ogg", true);
        MP3Player.play("menueBG", true);

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
