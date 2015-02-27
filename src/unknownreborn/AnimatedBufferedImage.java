/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import util.ImageLoader;

/**
 * Ein animiertes BufferedImage
 *
 *
 * DIE GETTICK METHODE MUSS NOCH GEFÜLLT WERDEN!!!!!!
 *
 *
 * @author Erik Brendel
 */
public class AnimatedBufferedImage {

    private final ArrayList<BufferedImage> imageList = new ArrayList<>();
    private final ArrayList<BufferedImage> scaledImageList = new ArrayList<>();
    private long startTick = Long.MIN_VALUE;
    private final int pauseMS;
    private boolean loop = false;
    private Point oldDimension = new Point(0, 0);
    private boolean started = true; //ob die animation schon läuft

    public BufferedImage getBufferedImage(Point dimension) {
        if (dimension.x != oldDimension.x || dimension.y != oldDimension.y) { //rescale
            //System.out.println(oldDimension);
            oldDimension = dimension;
            rescaleGif();
        }
        if (!started) {
            return scaledImageList.get(0); //first one if not started
        }

        BufferedImage erg = scaledImageList.get(scaledImageList.size() - 1); //last one
        int msPassed = (int) (getTick() - startTick);

        if (loop) { //remove full Loop cycles
            int loopMS = pauseMS * scaledImageList.size();
            msPassed = msPassed % loopMS;
        }

        int id = 0; // check wich point we are
        while (msPassed >= pauseMS) {
            msPassed -= pauseMS;
            id++;
        }

        if (id < scaledImageList.size()) { // set image if possible, else return last
            erg = scaledImageList.get(id);
        }

        return erg;
    }

    private void rescaleGif() {

        scaledImageList.clear();
        for (BufferedImage orig : imageList) {
            scaledImageList.add(ImageLoader.getScaledImage(orig, oldDimension.x, oldDimension.y, ImageLoader.MODE_FAST));
        }
    }

    public AnimatedBufferedImage(ArrayList<BufferedImage> list, int pauseMS) {
        this.pauseMS = pauseMS;
        for (BufferedImage img : list) {
            imageList.add(convert(img));
        }
    }

    public AnimatedBufferedImage(BufferedImage img, int pauseMS) {
        this.pauseMS = pauseMS;
        imageList.add(convert(img));
    }

    public AnimatedBufferedImage(BufferedImage img) {
        this.pauseMS = 100;
        imageList.add(convert(img));
    }

    private BufferedImage convert(BufferedImage raw) {
        BufferedImage c;
        if (raw.getType() == BufferedImage.TYPE_INT_RGB) {
            c = new BufferedImage(raw.getWidth(), raw.getHeight(), BufferedImage.TYPE_INT_RGB);
        } else {
            c = new BufferedImage(raw.getWidth(), raw.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }
        Graphics2D g = c.createGraphics();
        g.drawImage(raw, 0, 0, null);
        return c;
    }

    public void addImage(BufferedImage img) {
        imageList.add(img);
    }

    public void restart(boolean loopIt) {
        started = true;
        loop = loopIt;
        startTick = getTick();
    }

    public void stop() {
        startTick = Long.MIN_VALUE;
        started = false;
    }

    private static boolean ticking = false;
    private static long tick = 0l;

    private static long getTick() {
        if (ticking) {
            return tick;
        } else {
            ticking = true;
            new Thread() {
                public void run() {
                    while (ticking) {
                        tick++;
                        try {
                            Thread.sleep(1);
                        } catch (Exception ex) {
                        }
                    }
                }
            }.start();
            return 0l;
        }
    }
}
