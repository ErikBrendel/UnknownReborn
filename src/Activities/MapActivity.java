package Activities;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import unknownreborn.AnimatedBufferedImage;
import unknownreborn.Map;
import util.FloatPoint;

/**
 *
 */
public class MapActivity extends GameActivity {

    public MapActivity(ActivityManager manager) {
        super(manager);
    }

    private Map activeMap;
    private FloatPoint playerLocation;
    private float mapSightRange = 16f; //wie breit das Sichtfenster ist (in mapSegmenten)

    @Override
    public void render(Graphics2D g, int width, int height) {
        int pixelsForOneSegment = Math.round((float) (width) / mapSightRange);
        FloatPoint sizeKoord = new FloatPoint(mapSightRange, mapSightRange / (float) (width) * (float) (height)); //Die Größe des Bildschirms in Kachelkoordinaten
        FloatPoint startKoord = new FloatPoint(playerLocation.x - (sizeKoord.x / 2f), playerLocation.y - (sizeKoord.y / 2f)); //ecke oben links des Bildschirms, in Kachelkoordinaten umgerechnet

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
        final String[] layers = {"Boden", "Boden2", "Wand", "Vorne"};

        for (int y = startKachel.y; y <= endKachel.y; y++) {
            for (int x = startKachel.x; x <= endKachel.x; x++) {

                //zuerst die koordinaten des Malpunktes bestimmen (in px)
                int drawX = Math.round(((float) (x) - startKoord.x) * (float) (pixelsForOneSegment));
                int drawY = Math.round(((float) (y) - startKoord.y) * (float) (pixelsForOneSegment));

                //jede ebene an diese Stelle übereinander malen
                for (String layerName : layers) {
                    AnimatedBufferedImage aimg = activeMap.getImage(layerName, x, y);
                    BufferedImage img = aimg.getBufferedImage(new Point(pixelsForOneSegment, pixelsForOneSegment));
                    g.drawImage(img, drawX, drawY, null);
                }

            }
        }

        //entities malen.... noch keine ahnung wie :D
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
            playerLocation = (FloatPoint) ps.get(1);
        } catch (Exception ex) {
            System.out.println("Wrong parameters for the MapActivity!");
            ex.printStackTrace();
        }
    }

    @Override
    public void onExit() {

    }
}
