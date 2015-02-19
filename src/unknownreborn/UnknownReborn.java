/*
 */
package unknownreborn;

/**
 *
 * @author Erik Brendel
 */
public class UnknownReborn {

    /**
     * Here it all begins.... :D
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("UnknownReborn DevBuild v0.1");
        UnknownReborn game = new UnknownReborn();
        game.initLogger();
        Window w = new Window();
        w.init();
        w.show();
    }
    
    public void initLogger() {
        
    }
}