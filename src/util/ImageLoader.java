/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Erik Brendel
 */
public class ImageLoader {

    public BufferedImage image(String name) {
        return image(name, "[ImageLoader]");
    }
    public BufferedImage image(String name, String preOut) {
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        URL url = null;
        try {
            String newName = name;
            if (!newName.contains(".")) {
                newName += ".PNG";
            } else {
                System.out.println(preOut + "Loading Image:" + newName);
            }
            url = getClass().getResource(newName);
            BufferedImage image = ImageIO.read(url);
            bi = image;
        } catch (IOException ex) {
            System.err.println(preOut + "Image Error: " + ex.toString());
        }
        if (bi != null) {
            return bi;
        } else {
            System.err.println(preOut + "No Image found at " + url);
            return null;
        }
    }
    
    private static ImageLoader thisOne;

    public static ImageLoader get() {
        if (thisOne == null) {
            thisOne = new ImageLoader();
        }
        return thisOne;
    }
    
    
    /*
     *
     * SCALING IMAGES
     *
     */
    public static final int MODE_FINE = AffineTransformOp.TYPE_BICUBIC;
    public static final int MODE_MEDIUM = AffineTransformOp.TYPE_BILINEAR;
    public static final int MODE_FAST = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
    public static BufferedImage getScaledImage(BufferedImage image, int width, int height, int mode) {
        return getScaledImage(image, width, height, mode, image.getType());
    }
    public static BufferedImage getScaledImage(BufferedImage image, int width, int height, int mode, int BufferedImageType) {
        if (width == 0 || height == 0) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double) width / imageWidth;
        double scaleY = (double) height / imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, mode);

        return bilinearScaleOp.filter(image,new BufferedImage(width, height, BufferedImageType));
    }
}
