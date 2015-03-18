/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import util.ImageLoader;
import util.StringMetrics;

/**
 *
 * @author Erik Brendel
 */
public class TextBoxActivity extends GameActivity {
    public static final int FONT_SIZE = 30;
    public static final Font TEXT_FONT = new Font(Font.SERIF, Font.PLAIN, FONT_SIZE);
    
    
    private String msg = "";
    private ArrayList<String> msgList = null;
    private BufferedImage textBoxBGImage = null;

    public TextBoxActivity(ActivityManager manager) {
        super(manager);
    }

    @Override
    public void render(Graphics2D g, int width, int height) {
        g.setFont(TEXT_FONT);
        if(msgList == null) {
            msgList = StringMetrics.splitIntoLines(msg, width - 50, g);
        }
        g.setColor(Color.BLACK);
        if(textBoxBGImage.getWidth() != width) {
            textBoxBGImage = ImageLoader.getScaledImage(textBoxBGImage, width, height/2, ImageLoader.MODE_FINE);
        }
        g.drawImage(textBoxBGImage, 0, height/2, null);
    }

    /**
     * zeigt die nächste zeile an oder überspringt die gerade laufende text-animation
     */
    public void next() {
        
    }


    @Override
    public boolean onKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                next();
                break;
        }
        return true;
    }


    @Override
    public boolean onKeyReleased(KeyEvent e) {
        return true;
    }

    @Override
    public void update() {
    }

    @Override
    /**
     * please pass a String as object
     */
    public void onEnter(Object p) {
        msg = (String) p;
        textBoxBGImage = ImageLoader.get().image("/resources/images/tex/textBoxBGImage.png");
    }

    @Override
    public void onExit() {
        textBoxBGImage = null;
    }
}
