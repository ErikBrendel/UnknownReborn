/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Point;
import java.util.ArrayList;
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
     * gibt das AnimatedBufferedImage zurück, das an genau dieser Stelle sein soll.
     * @param layerName der Name der Ebene
     * @param x x-Position
     * @param y y-Position
     * @return
     */
    public AnimatedBufferedImage getImage(String layerName, int x, int y) {
        ArrayList<Element> layers = (ArrayList<Element>) rootElement.getChildren("layer");
        Element thisLayer = null;
        for (Element e: layers) {
            if(e.getAttributeValue("name").equals(layerName)) {
                thisLayer = e;
                break;
            }
        }
        if (thisLayer == null) {
            return null;
        }
        int layerWidth = Integer.valueOf(thisLayer.getAttributeValue("width"));
        int index = (y * layerWidth) + x;
        int tileID = Integer.valueOf(thisLayer.getChild("data").getChildren().get(index).getTextTrim());
        return tileList.get(tileID);
    }
    
    public Point getDimensions() {
        Element aLayer = rootElement.getChild("layer");
        int width = Integer.valueOf(aLayer.getAttributeValue("width"));
        int height = Integer.valueOf(aLayer.getAttributeValue("height"));
        return new Point(width, height);
    }
    
}
