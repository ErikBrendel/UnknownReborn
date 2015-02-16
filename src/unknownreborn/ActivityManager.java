/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.image.BufferedImage;
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
    public void addState(GameActivity newActivity, String name) {
        activities.put(name, newActivity);
    }

    /**
     * hier wird die aktuelle activity gerendert
     * @param img das Bild
     */
    public void render(BufferedImage img) {
        active.render(img);
    }
    
}
