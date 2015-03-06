package Activities;

import Entity.Entity;
import Entity.PlayerEntity;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import unknownreborn.AnimatedBufferedImage;
import unknownreborn.Map;
import util.DoublePoint;

/**
 *
 */
public class MapActivity extends GameActivity {

    private static final String[] layers1 = {"Boden", "Boden2", "Wand", "Wand2"};
    private static final String[] layers2 = {"Vorne", "Vorne2"};

    private Map activeMap;
    
    private PlayerEntity playerEntity;
    private double mapSightRange = 16f; //wie breit das Sichtfenster ist (in mapSegmenten)
    
    public MapActivity(ActivityManager manager) {
        super(manager);
    }


    /**
     * change this method when the camera should focus smth else
     * @return the center of the camera
     */
    public DoublePoint getFocusLocation() {
        return playerEntity.getLocation();
    }

    @Override
    public void render(Graphics2D g, int width, int height) {
        double pixelsForOneSegment = (double) (width) / mapSightRange;
        DoublePoint sizeKoord = new DoublePoint(mapSightRange, mapSightRange / (double) (width) * (double) (height)); //Die Größe des Bildschirms in Kachelkoordinaten
        DoublePoint startKoord = new DoublePoint(getFocusLocation().x - (sizeKoord.x / 2f), getFocusLocation().y - (sizeKoord.y / 2f)); //ecke oben links des Bildschirms, in Kachelkoordinaten umgerechnet

        //wir wollen ja keinen schwarzen bildschirm zeigen... wenn wir an der ecke sind, "hält diese den Bildschirm vom weiterscrollen ab"
        if (startKoord.x < 0) {
            startKoord.x = 0;
        }
        if (startKoord.y < 0) {
            startKoord.y = 0;
        }
        if (startKoord.x + sizeKoord.x > activeMap.getDimensions().x) {
            startKoord.x = activeMap.getDimensions().x - sizeKoord.x;
        }
        if (startKoord.y + sizeKoord.y > activeMap.getDimensions().y) {
            startKoord.y = activeMap.getDimensions().y - sizeKoord.y;
        }

        //jetzt haben wir start und size, jetzt können wir raus finden, welche Kacheln gemalt werden müssen
        Point startKachel = new Point((int) startKoord.x, (int) startKoord.y);
        Point endKachel = new Point((int) (startKoord.x + sizeKoord.x + 1), (int) (startKoord.y + sizeKoord.y + 1));

        //jetzt werden die Kacheln gemalt, zeilenweise
        for (int y = startKachel.y; y <= endKachel.y; y++) {
            for (int x = startKachel.x; x <= endKachel.x; x++) {

                //zuerst die koordinaten des Malpunktes bestimmen (in px)
                int drawX = (int) Math.round(((double) (x) - startKoord.x) * pixelsForOneSegment);
                int drawY = (int) Math.round(((double) (y) - startKoord.y) * pixelsForOneSegment);

                //jede ebene an diese Stelle übereinander malen
                for (String layerName : layers1) {
                    AnimatedBufferedImage aimg = activeMap.getImage(layerName, x, y);
                    BufferedImage img = aimg.getBufferedImage(new Point((int) Math.round(pixelsForOneSegment) + 1, (int) Math.round(pixelsForOneSegment) + 1)); //plus 1, damit sich die bilder leicht überlappen.... sonst gibt es hässliche weiße linien
                    g.drawImage(img, drawX, drawY, null);
                }

            }
        }

        /*
        
         E N T I T I E S ! 
        
         */
        for (Entity entity : activeMap.getAllEntitiesSorted()) {
            if (true/* wenn es gemalt werden muss */) {
                BufferedImage entityImg = entity.getScaledImage(pixelsForOneSegment);

                int entityX = (int) Math.round((entity.getLocation().x - startKoord.x - (entity.getDimensions().x / 2d)) * pixelsForOneSegment);
                int entityY = (int) Math.round((entity.getLocation().y - startKoord.y - (entity.getDimensions().y / 2d)) * pixelsForOneSegment);

                g.drawImage(entityImg, entityX, entityY, null);
            }
        }

        /*
        
         VORDERE KACHELEBENE
        
         */
        for (int y = startKachel.y; y <= endKachel.y; y++) {
            for (int x = startKachel.x; x <= endKachel.x; x++) {
                int drawX = (int) Math.round(((double) (x) - startKoord.x) * pixelsForOneSegment);
                int drawY = (int) Math.round(((double) (y) - startKoord.y) * pixelsForOneSegment);

                for (String layerName : layers2) {
                    AnimatedBufferedImage aimg = activeMap.getImage(layerName, x, y);
                    BufferedImage img = aimg.getBufferedImage(new Point((int) Math.round(pixelsForOneSegment) + 1, (int) Math.round(pixelsForOneSegment) + 1)); //plus 1, damit sich die bilder leicht überlappen.... sonst gibt es hässliche weiße linien
                    g.drawImage(img, drawX, drawY, null);
                }
            }
        }
    }

    @Override
    public boolean onKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                playerEntity.move(0, true, activeMap);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                playerEntity.move(2, true, activeMap);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                playerEntity.move(4, true, activeMap);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                playerEntity.move(6, true, activeMap);
                break;
            case KeyEvent.VK_PLUS:
                mapSightRange /= 1.1f;
                break;
            case KeyEvent.VK_MINUS:
                mapSightRange *= 1.1f;
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                playerEntity.move(0, false, activeMap);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                playerEntity.move(2, false, activeMap);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                playerEntity.move(4, false, activeMap);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                playerEntity.move(6, false, activeMap);
                break;
        }
        return true;
    }

    @Override
    public void update() {
    }

    /**
     * als Parameter bitte eine Map
     *
     * @param p the object-List
     */
    @Override
    public void onEnter(Object p) {
        try {
            activeMap = (Map) p;
            playerEntity = activeMap.getPlayerEntity();
        } catch (Exception ex) {
            System.out.println("Wrong parameters for the MapActivity!");
            ex.printStackTrace();
        }
    }

    @Override
    public void onExit() {

    }
}
