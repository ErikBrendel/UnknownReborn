/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Erik Brendel
 */
public class ActivityManager {
    
    public ActivityManager() {
        activities = new HashMap<>();
        activeStack = new ArrayList<>();
        activeStack.add(GameActivity.ACTIVITY_EMPTY);
    }
    private final HashMap<String, GameActivity> activities; //die palette aller activities
    private final ArrayList<GameActivity> activeStack; //die liste der aktuell angezeigten activities

    /**
     * Eine neue Activity der Palette hinzufügen
     *
     * @param newActivity die Activity
     * @param name der Name
     */
    public void loadActivity(GameActivity newActivity, String name) {
        activities.put(name, newActivity);
    }

    /**
     * eine neue Activity auf den ActivityStack drauf legen --> drüber rendern
     * lassen
     *
     * @param name the name of the new activity
     * @param parameter die parameter, die der activity übergeben werden sollen
     */
    public void showActivity(String name, Object parameter) {
        GameActivity newActivity = activities.get(name);
        if (newActivity == null) { //wenn die activity nicht vorhanden
            System.out.println("No activity called " + name);
        } else {
            newActivity.onEnter(parameter);
            activeStack.add(newActivity);
        }
    }

    /**
     * eine aktive activity aus dem activeStack entfernen
     *
     * @param name
     */
    public void removeActivity(String name) {
        GameActivity remove = activities.get(name);
        if (remove != null) {
            remove.onExit();
            activeStack.remove(remove);
        }
    }

    public void update(long delta) {
    }

    /**
     * hier wird die aktuelle activity gerendert
     *
     * @param g das Graphics-Objekt zum zeichnen
     * @param w width
     * @param h height
     */
    public void render(Graphics2D g, int w, int h) {
        //der reihe nach alle aus der liste übereinander rendern lassen
        for (GameActivity a : activeStack) {
            a.render(g, w, h);
        }
    }

    public void keyPressed(KeyEvent e) {
        GameActivity a;
        int i = activeStack.size();
        do {
            i--;
            a = activeStack.get(i);
        } while (!a.onKeyPressed(e));

    }

    public void keyReleased(KeyEvent e) {
        GameActivity a;
        int i = activeStack.size();
        do {
            i--;
            a = activeStack.get(i);
        } while (!a.onKeyReleased(e));
    }
}
