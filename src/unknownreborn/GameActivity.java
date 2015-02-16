/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.Graphics2D;

/**
 *
 * The Interface for an Activity (or State) in the game
 * 
 */
public interface GameActivity {

    /**
     *
     * @param g the graphics-object to be painted with
     * @param width  height of the screen
     * @param hight height of the screen
     */
    public abstract void render(Graphics2D g, int width, int hight);
    
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
    public static final GameActivity ACTIVITY_EMPTY = new GameActivity() {
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
    };
}
