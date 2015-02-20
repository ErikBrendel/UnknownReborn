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
import org.jdom2.Element;
import util.ImageLoader;

/**
 *
 * @author Erik Brendel
 */
public class MapLoader {
    
    public static Map loadMapFromResources(String blankFileName) {
        InputStream fileStream = Map.class.getClassLoader().getResourceAsStream("/maps/" + blankFileName + ".tmx");
        return new Map(fileStream);
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
            BufferedImage fullTileSet = ImageLoader.get().image("/gui/tilesets/" + name);
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
