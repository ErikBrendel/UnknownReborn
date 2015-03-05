/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import Entity.Entity;
import Entity.PlayerEntity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import util.DoublePoint;
import util.ImageLoader;

/**
 *
 * @author Erik Brendel
 */
public class MapLoader {
    
    public static Map loadMapFromResources(String blankFileName) {
        URL url = MapLoader.class.getResource("/resources/maps/" + blankFileName + ".tmx");
        System.out.println("url = " + url);
        
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(url);
            Element rootElement = document.getRootElement();
            
            //tilesets laden
            List<Element> tilesetElements = rootElement.getChildren("tileset");
            ArrayList<AnimatedBufferedImage> tileList = new ArrayList<>();
            tileList.add(new AnimatedBufferedImage(new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB))); //the 0-segment (just transparency)
            for (Element e : tilesetElements) {
                ArrayList<AnimatedBufferedImage> tiles = loadTileset(e);
                tileList.addAll(tiles);
            }
            Map map = new Map(rootElement, tileList);
            
            
            map.addEntity(PlayerEntity.createNew());
            
            return map;

        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    private static final int imagePixelSize = 32;
    
    

    /**
     * Lädt die einzelnen Bilder eines Tilesets
     * 
     * 
     * ANIMATIONEN FEHLEN NOCH!
     * 
     * 
     * @param tileSetNode das Element-Objekt des Tilesets
     * @return eine Liste aller Segment-Bilder des Tilesets
     */
    public static ArrayList<AnimatedBufferedImage> loadTileset(Element tileSetNode) {
        try {
            //String name = tileSetNode.getAttributeValue("name");
            String[] splitFileName = tileSetNode.getChild("image").getAttributeValue("source").split("/");
            String name = splitFileName[splitFileName.length - 1];
            System.out.println("name = " + name);
            if (name == null ||name.equals("")) {
                throw new IOException("Kein Name vergeben.");
            }
            BufferedImage fullTileSet = ImageLoader.get().image("/resources/images/tilesets/" + name);
            int imgWidth = fullTileSet.getWidth()/imagePixelSize; //in segments
            int imgHeight = fullTileSet.getHeight()/imagePixelSize;
            
            ArrayList<AnimatedBufferedImage> list = new ArrayList<>();
            for (int row = 0; row < imgHeight; row++) {
                for (int x = 0; x < imgWidth; x++) {
                    BufferedImage segment = fullTileSet.getSubimage(x * imagePixelSize, row * imagePixelSize, imagePixelSize, imagePixelSize);
                    AnimatedBufferedImage animation = new AnimatedBufferedImage(segment);
                    list.add(animation);
                }
            }
            
            
            
            return list;
        } catch (Exception ex) {
            System.out.println("Error in loading Tileset:");
            ex.printStackTrace();
            return null;
        }
    }
}
