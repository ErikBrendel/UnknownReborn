/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import util.ImageLoader;

/**
 *
 * @author Erik Brendel
 */
public class Map {


    public Map(InputStream in) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(in);
            rootElement = document.getRootElement();
            
            //tilesets laden
            ArrayList<Element> tilesetElements = (ArrayList<Element>) rootElement.getChildren("tileset");
            tileList = new ArrayList<>();
            tileList.add(new AnimatedBufferedImage(new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB))); //the 0-segment (just transparency)
            for (Element e : tilesetElements) {
                ArrayList<AnimatedBufferedImage> tiles = MapLoader.loadTileset(e);
                tileList.addAll(tiles);
            }
            

        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Element rootElement;
    private ArrayList<AnimatedBufferedImage> tileList;
    
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
    
}
