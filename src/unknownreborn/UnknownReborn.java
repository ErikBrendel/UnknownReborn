package unknownreborn;

import Activities.ActivityManager;
import Activities.ConfirmExitWindow;
import Activities.MainMenueActivity;
import Activities.MapActivity;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MP3Player;

/**
 *
 * @author Erik Brendel
 */
public class UnknownReborn implements Runnable { 

    ActivityManager manager = null;
    Window window = null;
    public static boolean isRunning = false;
    private long delta = 0;
    private long last = 0;
    private long fps = 0;

    public UnknownReborn(String name) {
        manager = new ActivityManager();
        window = new Window(manager, name);
    }

    public static void main(String[] args) {
        UnknownReborn game = new UnknownReborn("UnknownReborn v.0.0.1");
        game.gameInitialisation();
        
        isRunning = true;
        new Thread(game).start();
    }

    public void initLogger() {
    }

    public void gameInitialisation() {
        manager.loadActivity(new MainMenueActivity(manager), "mainMenue");
        manager.loadActivity(new ConfirmExitWindow(manager), "confirmExitWindow");
        manager.loadActivity(new MapActivity(manager), "mapActivity");
        manager.showActivity("mainMenue", null);
        
        window.initialisation();
        last = System.nanoTime();
    }
    
    @Override
    public void run() {
        window.makeVisible();
        
        while (isRunning) {
            computeDelta();
            
            manager.update(delta);
            window.updateFPS(fps);
            window.repaint();
           try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(UnknownReborn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        stopGame();
    }
    
    private void computeDelta() {
        delta = System.nanoTime() - last;
        last = System.nanoTime();
        fps = ((long) 1e9) / delta;
    }
    
    private void stopGame() {
        MP3Player.stopAllAudio();
        window.dispose();
        manager.removeActivity("mainMenue");
    }
}
