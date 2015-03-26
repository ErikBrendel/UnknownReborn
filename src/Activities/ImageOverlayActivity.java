/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activities;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import unknownreborn.AnimatedBufferedImage;
import util.ImageLoader;

/**
 *
 * @author Erik Brendel
 */
public class ImageOverlayActivity extends GameActivity {

    private BufferedImage overlay = null;
    private boolean fadeout = false;
    private long startFadingTick = Long.MIN_VALUE;
    private int fadeMS = 0;

    public ImageOverlayActivity(ActivityManager manager) {
        super(manager);
    }

    @Override
    public void render(Graphics2D g, int width, int height) {
        if (overlay.getWidth() != width || overlay.getHeight() != height) {
            overlay = ImageLoader.getScaledImage(overlay, width, height, ImageLoader.MODE_FAST);
        }
        if (fadeout) {
            float opacity = (float)(AnimatedBufferedImage.getTick() - startFadingTick) / (float)(fadeMS);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            g.drawImage(overlay, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_ATOP));
        } else {
            g.drawImage(overlay, 0, 0, null);
        }
    }

    @Override
    public boolean onKeyPressed(KeyEvent e) {
        return false;
    }

    @Override
    public boolean onKeyReleased(KeyEvent e) {
        return false;
    }

    @Override
    public void update() {
    }

    @Override
    public void onEnter(Object p) {
        overlay = (BufferedImage) p;
    }

    @Override
    public void onExit() {
        overlay = null;
    }

    /**
     * lässt das bild ausblenden --> Activity wird transparent
     *
     * @param ms die Zeit in millisekunden
     */
    public void fadeOut(int ms) {
        fadeMS = ms;
        fadeout = true;
        startFadingTick = AnimatedBufferedImage.getTick();
    }
}
