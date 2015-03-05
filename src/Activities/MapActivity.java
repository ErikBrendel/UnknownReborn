package Activities;

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

    public MapActivity(ActivityManager manager) {
        super(manager);
    }

    private Map activeMap;
    private DoublePoint playerLocation;
    private int playerDirection = 10;
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
                move(0, true);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                move(2, true);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                move(4, true);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                move(6, true);
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

    private static final double moveSizePerTick = 0.005d;
    private static final double moveSizePerTickLittle = Math.sqrt((moveSizePerTick * moveSizePerTick) / 2);

    /**
     * ändert die Bewegung des Spielers
     *
     * @param newDirection 0 ist up, 2 ist rechst, 4 ist unten und 6 ist
     * links,.... die werte dazwischen sind für allgemeine drehungen für
     * objekte.... ich wollte halt die richtungsangaben programmweit einheitlich
     * lassen
     * @param startNotStop start heißt loslaufen, wenn false dannn anhalten
     */
    public void move(int newDirection, boolean startNotStop) {
        if (startNotStop) {
            pressedDirections.add(newDirection);
        } else {
            pressedDirections.remove(newDirection);
        }
        playerDirection = 0;
        for (Object o : pressedDirections.toArray()) {
            playerDirection += (int) o;
        }
        if (pressedDirections.isEmpty() || pressedDirections.size() > 2 || 
                (pressedDirections.contains(0) && pressedDirections.contains(4)) || 
                (pressedDirections.contains(2) && pressedDirections.contains(6))) {
            playerDirection = 10;
        } else {
            if (pressedDirections.contains(0) && pressedDirections.contains(6)) {
                playerDirection = 7;
            } else {
                playerDirection = playerDirection / pressedDirections.size();
            }
        }

        if (!moveThreadRunning && startNotStop) {
            moveThreadRunning = true;
            //start moving thread
            new Thread() {
                public void run() {
                    do {
                        DoublePoint oldPlayerLocation = new DoublePoint(playerLocation);
                        switch (playerDirection) {
                            case 0:
                                playerLocation.y -= moveSizePerTick;
                                break;
                            case 1:
                                playerLocation.y -= moveSizePerTickLittle;
                                playerLocation.x += moveSizePerTickLittle;
                                break;
                            case 2:
                                playerLocation.x += moveSizePerTick;
                                break;
                            case 3:
                                playerLocation.y += moveSizePerTickLittle;
                                playerLocation.x += moveSizePerTickLittle;
                                break;
                            case 4:
                                playerLocation.y += moveSizePerTick;
                                break;
                            case 5:
                                playerLocation.y += moveSizePerTickLittle;
                                playerLocation.x -= moveSizePerTickLittle;
                                break;
                            case 6:
                                playerLocation.x -= moveSizePerTick;
                                break;
                            case 7:
                                playerLocation.y -= moveSizePerTickLittle;
                                playerLocation.x -= moveSizePerTickLittle;
                                break;
                        }
                        
                        if(!activeMap.isWalkable(playerLocation, null)) {
                            playerLocation = oldPlayerLocation;
                        }

                        try {
                            Thread.sleep(1);
                        } catch (Exception ex) {

                        }
                    } while (moveThreadRunning);
                }
            }.start();
        }
    }
    private boolean moveThreadRunning = false;
    private HashSet<Integer> pressedDirections = new HashSet<>();
    
    
    

    @Override
    public boolean onKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                move(0, false);
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                move(2, false);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                move(4, false);
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                move(6, false);
                break;
        }
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
