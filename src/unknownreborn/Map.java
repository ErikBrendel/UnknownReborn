/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.io.InputStream;

/**
 *
 * @author Erik Brendel
 */
public class Map {
    public static Map loadMapFromResources(String blankFileName) {
        InputStream fileStream = Map.class.getClassLoader().getResourceAsStream("/maps/" + blankFileName + ".tmx");
        fileStream.
        String fileContent = "";
        return new Map(fileContent);
    }
    public Map(String rawXMLInput) {
        
    }
}
