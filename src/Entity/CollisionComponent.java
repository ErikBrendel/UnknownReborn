package Entity;

import util.DoublePoint;

public abstract class CollisionComponent {

    /**
     * launched on every move attempt of any object
     *
     * @return a Point representing the upper left corner of the collision box,
     * P(0, 0) is the entity location, values in segments
     */
    public abstract DoublePoint getCollisionBoxStart();

    /**
     * launched on every move attempt of every object
     *
     * @return the lower right corner of the collision box, more details see
     * getCollisionBoxStart();
     */
    public abstract DoublePoint getCollisionBoxEnd();
}
