/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Point;
import java.awt.image.BufferedImage;
import util.DoublePoint;

/**
 *
 * @author Erik Brendel
 */
public abstract class Entity {

    private DoublePoint location;
    private MoveComponent myMoveComponent = null;
    private CollisionComponent myCollisionComponent = null;

    public DoublePoint getLocation() {
        return location;
    }

    public void setLocation(DoublePoint newLoc) {
        location = newLoc;
    }

    public BufferedImage getImage() {
        //create a new bufferedImage using getDimensions()
        return null;
    }

    public Point getDimensions() {
        return null;
    }

    public void setMoveComponent(MoveComponent c) {
        myMoveComponent = c;
        c.e = this;
        MoveComponent.activeComponents.add(c);
        MoveComponent.startTicking();
    }

    public MoveComponent getMoveComponent() {
        return myMoveComponent;
    }

    public void setCollisionComponent(CollisionComponent c) {
        myCollisionComponent = c;
    }

    public CollisionComponent getCollisionComponent() {
        return myCollisionComponent;
    }

}
