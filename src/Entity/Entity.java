/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import util.DoublePoint;

/**
 *
 * @author Erik Brendel
 */
public abstract class Entity {
    public DoublePoint getLocation() {
        return location;
    }
    private DoublePoint location;
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
        c.e = this;
        MoveComponent.activeComponents.add(c);
        MoveComponent.startTicking();
    }
    
    
    
    
    public static abstract class MoveComponent {
        private static ArrayList<MoveComponent> activeComponents = new ArrayList<>();
        private static boolean ticking = false;
        private static void startTicking() {
            if(!ticking) {
                ticking = true;
                new Thread() {
                    public void run() {
                        while(ticking) {
                            for (MoveComponent c: activeComponents) {
                                c.onTick();
                            }
                            try {
                                Thread.sleep(1);
                            } catch (Exception ex) {
                            }
                        }
                    }
                }.start();
            }
        }
        
        
        /**
         * launched on every millisecond
         * 
         * overwrite to do the moving logic
         */
        public abstract void onTick(); 
        
        /**
         * use this method in your onTick Method
         * @param newLoc the new Location of this entity
         */
        protected final void setLocation(DoublePoint newLoc) {
            e.setLocation(newLoc);
        }
        /**
         * use this in your onTick method
         * @return the actual location of the entity
         */
        protected final DoublePoint getLocation() {
            return e.getLocation();
        }
        private Entity e;
    }
}
