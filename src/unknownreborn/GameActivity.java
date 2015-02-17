/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * The Interface for an Activity (or State) in the game
 * 
 */
public abstract class GameActivity {
    final ActivityManager myManager;
    public GameActivity(ActivityManager manager) {
        myManager = manager;
    }

    /**
     *
     * @param g the graphics-object to be painted with
     * @param width  height of the screen
     * @param hight height of the screen
     */
    public abstract void render(Graphics2D g, int width, int hight);
    
    public abstract void onKeyPressed(KeyEvent e);
    
    public abstract void onKeyReleased(KeyEvent e); 
    
    /**
     * vll. muessen wir hier noch was übergeben :D
     */
    public abstract void update();
    
    /**
     * wenn die activity geladen wird --> ...angezeigt wird
     */
    public abstract void onEnter();
    
    /**
     * letzte methode, danach wird nicht mehr render() aufgerufen
     */
    public abstract void onExit();
    
    /**
     * Eine default-activity, komplet schwarz
     */
    public static final GameActivity ACTIVITY_EMPTY = new GameActivity(null) {
        @Override
        public void render(Graphics2D g, int width, int hight) {
        }
        
        @Override
        public void update() {
        }

        @Override
        public void onEnter() {
        }

        @Override
        public void onExit() {
        }

        @Override
        public void onKeyPressed(KeyEvent e) {
        }

        @Override
        public void onKeyReleased(KeyEvent e) {
        }
    };
}

//git-probleme behoben