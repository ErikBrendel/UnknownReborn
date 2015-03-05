/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import Entity.CollisionComponent;
import Entity.Entity;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import util.DoublePoint;

/**
 *
 * @author Erik Brendel
 */
public class Map {

    public Map(Element rootElement, ArrayList<AnimatedBufferedImage> tileList) {
        this.rootElement = rootElement;
        this.tileList = tileList;
    }

    private final Element rootElement;
    private final ArrayList<AnimatedBufferedImage> tileList;
    private final ArrayList<Entity> entityList = new ArrayList<>();

    /**
     * gibt das AnimatedBufferedImage zurück, das an genau dieser Stelle sein
     * soll. Die Ebenen heißen Boden, evtl Boden2, Wand und Vorne.
     *
     * @param layerName der Name der Ebene
     * @param x x-Position
     * @param y y-Position
     * @return
     */
    public AnimatedBufferedImage getImage(String layerName, int x, int y) {
        return tileList.get(getSegmentID(layerName, x, y));
    }

    private int getSegmentID(String layerName, int x, int y) {
        if (x < 0 || x >= getDimensions().x) {
            return 0;
        }
        if (y < 0 || y >= getDimensions().y) {
            return 0;
        }
        List<Element> layers = rootElement.getChildren("layer");
        Element thisLayer = null;
        for (Element e : layers) {
            if (e.getAttributeValue("name").equals(layerName)) {
                thisLayer = e;
                break;
            }
        }
        if (thisLayer == null) {
            return 0;
        }
        int layerWidth = Integer.valueOf(thisLayer.getAttributeValue("width"));
        int index = (y * layerWidth) + x;
        return Integer.valueOf(thisLayer.getChild("data").getChildren().get(index).getAttributeValue("gid"));
    }

    public static final AnimatedBufferedImage emptyAnimation = new AnimatedBufferedImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));

    /**
     * gibt eine Liste aller auf dieser Map befindlichen Entities zurück
     *
     * @return die Array-Liste
     */
    public ArrayList<Entity> getAllEntities() {
        return entityList;
    }
    
    /**
     * Fügt der Map ein neues Entity hinzu
     * @param e das neue entity
     */
    public void addEntity(Entity e) {
        entityList.add(e);
    }

    /**
     * returns the map dimension in segments
     *
     * @return a Point representing the maps width and height
     */
    public Point getDimensions() {
        Element aLayer = rootElement.getChild("layer");
        int width = Integer.valueOf(aLayer.getAttributeValue("width"));
        int height = Integer.valueOf(aLayer.getAttributeValue("height"));
        return new Point(width, height);
    }

    /**
     * checks, is a desired location on the grid is walkable or blocked through
     * walls/entities
     *
     * @param aPoint
     * @param except
     * @return
     */
    public boolean isWalkable(DoublePoint aPoint, ArrayList<Entity> except) {
        if (getSegmentID("Wand", (int) aPoint.x, (int) aPoint.y) != 0) {
            return false;
        }
        if (getSegmentID("Wand2", (int) aPoint.x, (int) aPoint.y) != 0) {
            return false;
        }

        ArrayList<Entity> entities = getAllEntities();
        for (Entity e : entities) {
            if (except != null && !except.contains(e)) {
                CollisionComponent cc = e.getCollisionComponent();
                if (cc != null) {
                    DoublePoint start = new DoublePoint(e.getLocation().x - (cc.getCollisionBoxDimensions().x / 2d), e.getLocation().y - (cc.getCollisionBoxDimensions().y / 2d));
                    DoublePoint end = new DoublePoint(e.getLocation().x + (cc.getCollisionBoxDimensions().x / 2d), e.getLocation().y + (cc.getCollisionBoxDimensions().y / 2d));
                    if (aPoint.x >= start.x && aPoint.x <= end.x) {
                        if (aPoint.y >= start.y && aPoint.y <= end.y) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
