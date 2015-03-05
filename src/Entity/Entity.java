/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Point;
import java.awt.image.BufferedImage;
import util.DoublePoint;
import util.ImageLoader;

/**
 *
 * @author Erik Brendel
 */
public abstract class Entity {

    private DoublePoint location = null;
    private MoveComponent myMoveComponent = null;
    private CollisionComponent myCollisionComponent = null;

    public DoublePoint getLocation() {
        return location;
    }

    public void setLocation(DoublePoint newLoc) {
        location = newLoc;
    }

    /**
     * gibt das Bild dieses Entitys zurück. Dieses sollte die Größe aus
     * getDimension mal 32 besitzen... also 32x32 Pixel pro Kachelkoordinate
     *
     * @return das aktuelle Bild des entities
     */
    public abstract BufferedImage getImage();
    
    public BufferedImage getScaledImage(double pixelsPerSegment) {
        BufferedImage small = getImage();
        return ImageLoader.getScaledImage(small, (int)Math.round(small.getWidth() * pixelsPerSegment / 32d), (int)Math.round(small.getHeight() * pixelsPerSegment / 32d), ImageLoader.MODE_FAST);
    }

    /**
     * gibt die Dimensionen des BufferedImages aus getImage() zurück, um im
     * vornhinein überprüfen zu können, ob es gemalt werden muss. Werte in Kachelkoordinaten
     *
     * @return die aktuellen Abmaße des Bildes des Entitys
     */
    public abstract DoublePoint getDimensions();

    /**
     * gibt dem Entity ein MoveComponent, welches es bewegt
     *
     * @param c das MoveComponent
     */
    public void setMoveComponent(MoveComponent c) {
        myMoveComponent = c;
        c.e = this;
        MoveComponent.activeComponents.add(c);
        MoveComponent.startTicking();
    }

    public MoveComponent getMoveComponent() {
        return myMoveComponent;
    }

    /**
     * gibt dem Entity ein CollisionComponent, so dass der Spieler / andere
     * bewegte Objekte wissen, dass sie durch dieses Objekt nicht hindurch gehen
     * sollen
     *
     * @param c das CollisionComponent
     */
    public void setCollisionComponent(CollisionComponent c) {
        myCollisionComponent = c;
    }

    public CollisionComponent getCollisionComponent() {
        return myCollisionComponent;
    }

}
