package unknownreborn;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik Brendel
 */
public class UnknownReborn {

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
        game.gameRun();
    }

    public void initLogger() {
    }

    public void gameInitialisation() {
        manager.loadActivity(new MainMenueActivity(manager), "mainMenue");
        manager.showActivity("mainMenue", null);
        window.initialisation();
        last = System.nanoTime();
    }

    public void gameRun() {
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
    }

    private void computeDelta() {
        delta = System.nanoTime() - last;
        last = System.nanoTime();
        fps = ((long) 1e9) / delta;
    }
}
