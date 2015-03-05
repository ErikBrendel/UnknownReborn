package Entity;

import java.util.ArrayList;
import util.DoublePoint;

public abstract class MoveComponent {

    static ArrayList<MoveComponent> activeComponents = new ArrayList<>();
    private static boolean ticking = false;

    static void startTicking() {
        if (!ticking) {
            ticking = true;
            new Thread() {
                public void run() {
                    while (ticking) {
                        for (MoveComponent c : activeComponents) {
                            c.onTick();
                        }
                        try {
                            Thread.sleep(1);
                        } catch (Exception ex) {
                        }
                    }
                }
            }.start();
        }
    }

    /**
     * launched on every millisecond
     *
     * overwrite to do the moving logic
     */
    public abstract void onTick();

    /**
     * use this method in your onTick Method
     *
     * @param newLoc the new Location of this entity
     */
    protected final void setLocation(DoublePoint newLoc) {
        e.setLocation(newLoc);
    }

    /**
     * use this in your onTick method
     *
     * @return the actual location of the entity
     */
    protected final DoublePoint getLocation() {
        return e.getLocation();
    }
    Entity e;
}
