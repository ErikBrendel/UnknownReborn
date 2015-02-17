/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author Erik Brendel
 */
public class ActivityManager {
    public ActivityManager() {
        activities = new HashMap<>();
        active = GameActivity.ACTIVITY_EMPTY;
    }
    private final HashMap<String, GameActivity> activities; //die palette aller activities
    private GameActivity active;

    /**
     * Eine neue Activity der Palette hinzufügen
     * @param newActivity die Activity
     * @param name der Name
     */
    public void addActivity(GameActivity newActivity, String name) {
        activities.put(name, newActivity);
    }
    
    /**
     * zu einer anderen Activity wechseln
     * @param name the name of the new activity
     */
    public void showActivity(String name) {
        GameActivity newActivity = activities.get(name);
        if (newActivity == null) { //wenn die activity nicht vorhanden
            System.out.println("No activity called " + name);
            newActivity = GameActivity.ACTIVITY_EMPTY;
        }
        newActivity.onEnter();
        active = newActivity;
    }

    /**
     * hier wird die aktuelle activity gerendert
     * @param g das Graphics-Objekt zum zeichnen
     * @param w width
     * @param h height
     */
    public void render(Graphics2D g, int w, int h) {
        active.render(g, w, h);
    }
    
    public void keyPressed(KeyEvent e) {
        active.onKeyPressed(e);
    }
    public void keyReleased(KeyEvent e) {
        active.onKeyReleased(e); 
    }
    
}
