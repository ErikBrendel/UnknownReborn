package Activities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import unknownreborn.AnimatedBufferedImage;
import unknownreborn.Map;
import util.DoublePoint;

/**
 *
 */
public class MapActivity extends GameActivity {

    public MapActivity(ActivityManager manager) {
        super(manager);
    }

    private Map activeMap;
    private DoublePoint playerLocation;
    private double mapSightRange = 16f; //wie breit das Sichtfenster ist (in mapSegmenten)

    @Override
    public void render(Graphics2D g, int width, int height) {
        double pixelsForOneSegment = (double) (width) / mapSightRange;
        DoublePoint sizeKoord = new DoublePoint(mapSightRange, mapSightRange / (double) (width) * (double) (height)); //Die Größe des Bildschirms in Kachelkoordinaten
        DoublePoint startKoord = new DoublePoint(playerLocation.x - (sizeKoord.x / 2f), playerLocation.y - (sizeKoord.y / 2f)); //ecke oben links des Bildschirms, in Kachelkoordinaten umgerechnet

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
        final String[] layers1 = {"Boden", "Boden2", "Wand", "Wand2"};
        final String[] layers2 = {"Vorne", "Vorne2"};

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
        //roter punkt für den spieler
        int playerX = (int) Math.round((playerLocation.x - startKoord.x) * pixelsForOneSegment);
        int playerY = (int) Math.round((playerLocation.y - startKoord.y) * pixelsForOneSegment);
        g.setColor(Color.red);
        g.fillOval(playerX - 10, playerY - 10, 20, 20);

        //entities malen.... noch keine ahnung wie :D
        /*
        
         E N T I T I E S ! 
        
         */
        //alle vorderen sachen malen, wie palmblätter usw
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
                playerLocation.y -= 0.5f;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                playerLocation.y += 0.5f;
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                playerLocation.x += 0.5f;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                playerLocation.x -= 0.5f;
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
        return true;
    }

    @Override
    public void update() {
    }

    /**
     * als Parameter bitte eine ArrayList\<Object\> mit der Map als erstem
     * element und einem FloatPoint für die startPosition des Spielers
     *
     * @param p the object-List
     */
    @Override
    public void onEnter(Object p) {
        try {
            ArrayList<Object> ps = (ArrayList<Object>) p;
            activeMap = (Map) ps.get(0);
            playerLocation = (DoublePoint) ps.get(1);
        } catch (Exception ex) {
            System.out.println("Wrong parameters for the MapActivity!");
            ex.printStackTrace();
        }
    }

    @Override
    public void onExit() {

    }
}
