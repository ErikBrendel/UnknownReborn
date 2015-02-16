/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.image.BufferedImage;

/**
 *
 * The Interface for an Activity (or State) in the game
 * 
 */
public interface GameActivity {

    /**
     *
     * @param img das BufferedImage über das drüber gemalt werden soll (damit die größe schon feststeht)
     */
    public abstract void render(BufferedImage img);
    
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
        public void render(BufferedImage img) {
        }

        @Override
        public void onEnter() {
        }

        @Override
        public void onExit() {
        }
    };
}
