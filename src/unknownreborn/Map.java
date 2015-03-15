/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import Entity.CollisionComponent;
import Entity.Entity;
import Entity.PlayerEntity;
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
    private final ArrayList<HitBox> hitBoxList = new ArrayList<>();

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
     * sortiert alle entities nach ihren y-Koordinaten, so dass diejenigen, die
     * weiter hinten stehen, auch eher gerendert werden
     *
     * @return die sortierte Liste
     */
    public ArrayList<Entity> getAllEntitiesSorted() {
        ArrayList<Entity> raw = getAllEntities();
        for (int i = 0; i < raw.size() - 1; i++) {
            for (int j = i; j < raw.size(); j++) {
                if (raw.get(i).getLocation().y > raw.get(j).getLocation().y) {
                    Entity swap = raw.get(i);
                    raw.set(i, raw.get(j));
                    raw.set(j, swap);
                }
            }
        }

        return raw;
    }

    /**
     * gibt das erste PlayerEntity-Objekt aus der entity-liste zurück
     *
     * @return der Player auf dieser Map
     */
    public PlayerEntity getPlayerEntity() {
        for (Entity e : getAllEntities()) {
            if (e instanceof PlayerEntity) {
                return (PlayerEntity) e;
            }
        }
        return null;
    }

    /**
     * Fügt der Map ein neues Entity hinzu
     *
     * @param e das neue entity
     */
    public void addEntity(Entity e) {
        entityList.add(e);
    }

    public void addHitBox(HitBox hb) {
        hitBoxList.add(hb);
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
        //map borders
        if (aPoint.x < 0 || aPoint.y < 0 || aPoint.x > getDimensions().x || aPoint.y > getDimensions().y) {
            return false;
        }

        //all the map-set hitboxes
        for (HitBox hb : hitBoxList) {
            if (aPoint.x >= hb.p1.x && aPoint.x <= hb.p2.x) {
                if (aPoint.y >= hb.p1.y && aPoint.y <= hb.p2.y) {
                    return false;
                }
            }
        }

        //all entities with collisionboxes
        ArrayList<Entity> entities = getAllEntities();
        for (Entity e : entities) {
            if (except == null || !except.contains(e)) {
                CollisionComponent cc = e.getCollisionComponent();
                if (cc != null) {
                    DoublePoint start = new DoublePoint(e.getLocation().x + cc.getCollisionBoxStart().x, e.getLocation().y + cc.getCollisionBoxStart().y);
                    DoublePoint end = new DoublePoint(e.getLocation().x + cc.getCollisionBoxEnd().x, e.getLocation().y + cc.getCollisionBoxEnd().y);
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

    public static class HitBox {

        public DoublePoint p1;
        public DoublePoint p2;

        public HitBox(float x, float y, float width, float height) {
            p1 = new DoublePoint(x, y);
            p2 = new DoublePoint(x + width, y + height);
        }
    }
}
