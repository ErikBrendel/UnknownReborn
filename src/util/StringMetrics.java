package util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringMetrics {

    public static Dimension getBounds(Graphics2D g, String text) {
        Rectangle2D bounds = g.getFont().getStringBounds(text, g.getFontRenderContext());
        return new Dimension((int) Math.ceil(bounds.getWidth()), (int) Math.ceil(bounds.getHeight()));
    }

    public static boolean isPureAscii(String v) {
        return StandardCharsets.US_ASCII.newEncoder().canEncode(v);
    }

    public static ArrayList<String> splitIntoLines(String text, int maxPixelSize, Graphics2D g) {
        ArrayList<String> list = new ArrayList<>();

        do {
            String lineNow = text;
            while (getBounds(g, lineNow).width >= maxPixelSize) {
                lineNow = removeLastWord(lineNow);
            }
            list.add(lineNow);
            //System.out.println("added as line: " + lineNow);
            if (text.length() == lineNow.length()) { //reached end of text
                text = "";
            } else {
                text = text.substring(lineNow.length() + 1); //+1 for the next space symbol.
            }
        } while (text.length() > 0);

        return list;
    }

    public static String removeLastWord(String input) {
        String[] words = input.split(" ");
        String output = "";
        for (int i = 0; i < words.length - 1; i++) {
            output = output + " " + words[i];
        }
        output = output.substring(1);
        return output;
    }
}
