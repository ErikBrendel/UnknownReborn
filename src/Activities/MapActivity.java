package Activities;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import unknownreborn.Map;
import util.FloatPoint;

/**
 *
 */
public class MapActivity extends GameActivity {

    public MapActivity(ActivityManager manager) {
        super(manager);
    }

    private Map activeMap;
    private FloatPoint playerLocation;
    private float mapSightRange = 16f; //wie breit das Sichtfenster ist (in mapSegmenten)
    
    @Override
    public void render(Graphics2D g, int width, int height) {
        int pixelsForOneSegment = Math.round((float)(width) / mapSightRange);
    }


    @Override
    public boolean onKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        }
        return true;
    }


    @Override
    public boolean onKeyReleased(KeyEvent e) {
        return true;
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
}
