package util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.nio.charset.StandardCharsets;

public class StringMetrics {
    public static Dimension getBounds(Graphics2D g, String text) {
        Rectangle2D bounds = g.getFont().getStringBounds(text, g.getFontRenderContext());
        return new Dimension((int)Math.ceil(bounds.getWidth()), (int)Math.ceil(bounds.getHeight()));
    }
    
    public static boolean isPureAscii(String v) {
        return StandardCharsets.US_ASCII.newEncoder().canEncode(v);
    }
}