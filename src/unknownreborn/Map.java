/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Erik Brendel
 */
public class Map {
    public static Map loadMapFromResources(String blankFileName) {
        InputStream fileStream = Map.class.getClassLoader().getResourceAsStream("/maps/" + blankFileName + ".tmx");
        //Scanner s = new Scanner(fileStream).useDelimiter("\\A");
        //String fileContent = s.hasNext() ? s.next() : "";
        return new Map(fileStream);
    }
    public Map(InputStream in) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document document = (Document) builder.build(in);
            rootElement = document.getRootElement();
            
            
            
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Element rootElement;
}
