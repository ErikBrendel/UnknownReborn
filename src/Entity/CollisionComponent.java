package Entity;

import util.DoublePoint;

public abstract class CollisionComponent {

    /**
     * launched on every move attempt of any object
     *
     * @return a Point representing width and height of the collision box,
     * centered around the entities location
     */
    public abstract DoublePoint getCollisionBoxDimensions();
}
