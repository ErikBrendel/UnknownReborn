/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unknownreborn;

import java.awt.image.BufferedImage;

/**
 *
 * @author Erik Brendel
 */
public interface GameActivity {
    public abstract BufferedImage render();
    public abstract void onEnter();
    public abstract void onExit();
}
