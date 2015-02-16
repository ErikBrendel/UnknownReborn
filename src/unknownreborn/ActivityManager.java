/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.util.HashMap;

/**
 *
 * @author Erik Brendel
 */
public class ActivityManager {
    public ActivityManager() {
        activities = new HashMap<>();
    }
    private final HashMap<String, GameActivity> activities; //die palette aller activities
    private GameActivity active;
    public void addState(GameActivity newActivity, String name) {
        activities.put(name, newActivity);
    }
    
}
