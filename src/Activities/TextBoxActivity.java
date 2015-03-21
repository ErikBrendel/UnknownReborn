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
import unknownreborn.AnimatedBufferedImage;
import util.ImageLoader;
import util.StringMetrics;

/**
 *
 * @author Erik Brendel
 */
public class TextBoxActivity extends GameActivity {

    public static final int FONT_SIZE = 100;
    public static final Font TEXT_FONT = new Font(Font.SERIF, Font.PLAIN, FONT_SIZE);
    public static final int animationPauseBetweenCharacters = 50; //ms

    private String msg = "";
    private ArrayList<String> msgList = null;
    private BufferedImage textBoxBGImage = null;

    private long textAnimationStartTick = Long.MIN_VALUE;
    private boolean animateBothLines = true;
    private boolean isAnimating = true;

    public TextBoxActivity(ActivityManager manager) {
        super(manager);
    }

    @Override
    public void render(Graphics2D g, int width, int height) {
        g.setFont(TEXT_FONT);
        g.setColor(Color.BLACK);
        if (msgList == null) {
            msgList = StringMetrics.splitIntoLines(msg, width - 50, g);
        }
        if (textBoxBGImage.getWidth() != width) {
            textBoxBGImage = ImageLoader.getScaledImage(textBoxBGImage, width, height / 2, ImageLoader.MODE_FINE);
        }
        g.drawImage(textBoxBGImage, 0, height / 2, null);

        int delta = (int) (AnimatedBufferedImage.getTick() - textAnimationStartTick);
        int charactersToShow = delta / animationPauseBetweenCharacters;

        String line1 = msgList.get(0);
        String line2 = "";
        if (msgList.size() >= 2) {
            line2 = msgList.get(1);
        }

        if (isAnimating) {
            if (animateBothLines) {
                if (charactersToShow < line1.length()) {
                    line1 = line1.substring(0, charactersToShow);
                    line2 = "";
                } else {
                    charactersToShow -= line1.length();
                    if (charactersToShow < line2.length()) {
                        line2 = line2.substring(0, charactersToShow);
                    } else {
                        isAnimating = false;
                        textAnimationStartTick = Long.MIN_VALUE;
                    }
                }
            } else {
                if (charactersToShow < line2.length()) {
                    line2 = line2.substring(0, charactersToShow);
                } else {
                    isAnimating = false;
                    textAnimationStartTick = Long.MIN_VALUE;
                }
            }
        }

        g.drawString(line1, 75, (int) (height * 0.75));
        g.drawString(line2, 75, (int) (height - 75));
    }

    /**
     * zeigt die nächste zeile an oder überspringt die gerade laufende
     * text-animation
     */
    public void next() {
        if (isAnimating) {
            isAnimating = false;
            textAnimationStartTick = Long.MIN_VALUE;
            animateBothLines = false;
            return;
        }
        if (msgList.size() <= 2) {
            manager.removeActivity("textBoxActivity");
        } else {
            msgList.remove(0);
            animateBothLines = false;
            textAnimationStartTick = AnimatedBufferedImage.getTick();
            isAnimating = true;
        }
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
        textAnimationStartTick = AnimatedBufferedImage.getTick();
        animateBothLines = true;
        isAnimating = true;
    }

    @Override
    public void onExit() {
        textBoxBGImage = null;
        msg = "";
        msgList = null;
        textAnimationStartTick = Long.MIN_VALUE;
        animateBothLines = true;
        isAnimating = false;
    }
}
