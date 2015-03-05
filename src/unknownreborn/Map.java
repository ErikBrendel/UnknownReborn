/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;

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
        try {
            List<Element> layers = rootElement.getChildren("layer");
            Element thisLayer = null;
            for (Element e : layers) {
                if (e.getAttributeValue("name").equals(layerName)) {
                    thisLayer = e;
                    break;
                }
            }
            if (thisLayer == null) {
                return emptyAnimation;
            }
            int layerWidth = Integer.valueOf(thisLayer.getAttributeValue("width"));
            int index = (y * layerWidth) + x;
            int tileID = Integer.valueOf(thisLayer.getChild("data").getChildren().get(index).getAttributeValue("gid"));
            return tileList.get(tileID);
        } catch (Exception ex) { //teilweise werden bilder von außerhalb der Map abgefragt
            return emptyAnimation;
        }
    }

    public static final AnimatedBufferedImage emptyAnimation = new AnimatedBufferedImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));

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

}
