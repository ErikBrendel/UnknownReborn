/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activities;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import util.StringMetrics;

/**
 *
 * @author Erik Brendel
 */
public class TextBoxActivity extends GameActivity {
    private String msg = "";
    private ArrayList<String> msgList = null;
    public static final int FONT_SIZE = 30;
    public static final Font TEXT_FONT = new Font(Font.SERIF, Font.PLAIN, FONT_SIZE);

    public TextBoxActivity(ActivityManager manager) {
        super(manager);
    }

    @Override
    public void render(Graphics2D g, int width, int height) {
        g.setFont(TEXT_FONT);
        if(msgList == null) {
            msgList = StringMetrics.splitIntoLines(msg, width - 50, g);
        }
        
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
    /**
     * please pass a String as object
     */
    public void onEnter(Object p) {
        msg = (String) p;
    }

    @Override
    public void onExit() {
        
    }
}
