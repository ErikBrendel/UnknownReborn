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
    
    /**
     *
     * @param e des KeyEvent
     * @return ob das event von dieser activity abgefangen wurde --> sonst wird es an die nächst-tiefere im stack weitergegeben
     */
    public abstract boolean onKeyPressed(KeyEvent e);
    
    /**
     * 
     * @param e das KeyEvent
     * @return ob das event von dieser activity abgefangen wurde --> sonst wird es an die nächst-tiefere im stack weitergegeben
     */
    public abstract boolean onKeyReleased(KeyEvent e); 
    
    /**
     * vll. muessen wir hier noch was übergeben :D
     */
    public abstract void update();
    
    /**
     * wenn die activity geladen wird --> ...angezeigt wird
     * @param parameter ein parameter objekt (wie text bei Chatbox)
     */
    public abstract void onEnter(Object parameter);
    
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
        public void onEnter(Object p) {
        }

        @Override
        public void onExit() {
        }

        @Override
        public boolean onKeyPressed(KeyEvent e) {
            return false;
        }

        @Override
        public boolean onKeyReleased(KeyEvent e) {
            return false;
        }
    };
}

//git-probleme behoben