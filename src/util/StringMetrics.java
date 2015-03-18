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

    public static ArrayList<String> splitIntoLines(String complete, int maxPixelSize, Graphics2D g) {
        ArrayList<String> list = new ArrayList<>();

        do {
            String now = complete;
            while (getBounds(g, now).width > maxPixelSize) {
                now = removeOneWord(now);
            }
            list.add(now);
            if (complete.length() == now.length()) {
                break; //kein weiterer text
            } else {
                complete = complete.substring(now.length(), complete.length() - 1);
            }
        } while (true);

        return list;
    }

    public static String removeOneWord(String input) {
        String[] in = input.split(" ");
        String erg = "";
        for (int i = 0; i < in.length - 1; i++) {
            erg += in[i];
        }
        return erg.substring(0, erg.length() - 1);
    }
}
