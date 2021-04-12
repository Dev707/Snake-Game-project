package snakeGame;

/* CPCS - 391
*  GROUP PROJECT
*  1. Eyad Halwani (Leader) - 1847263
*  2. Khalid Alghamdi - 1846409
*  3. Mohammed Miaji - 1845953 
*/
import org.lwjgl.opengl.*;


public class Square {

    // Variables
    public static final double SIZE = 10;
    private Location location;

    // Accessors, Mutators
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location = loc;
    }

    public void setLocation(double x, double y) {
        location.setX(x);
        location.setY(y);
    }

    // Constructors
    public Square() {
        location = new Location();
    }

    public Square(double x, double y) {
        location = new Location(x, y);
    }

    // Methods
    public void draw(int value) {
        if (value == 1) {
            GL11.glBegin(GL11.GL_QUADS);
            
            GL11.glColor3f(1, 1, 1);
            GL11.glVertex2d(location.getX(), location.getY());
            GL11.glVertex2d(location.getX() + SIZE, location.getY());
            GL11.glVertex2d(location.getX() + SIZE, location.getY() + SIZE);
            GL11.glVertex2d(location.getX(), location.getY() + SIZE);
            GL11.glEnd();
        } else {
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor3f(1, 0f, 0);
            GL11.glVertex2d(location.getX(), location.getY());
            GL11.glVertex2d(location.getX() + SIZE, location.getY());
            GL11.glVertex2d(location.getX() + SIZE, location.getY() + SIZE);
            GL11.glVertex2d(location.getX(), location.getY() + SIZE);
            GL11.glEnd();
        }

    }

    public void draw(int x, int y) {
        setLocation(x, y);
        draw(1);
    }
}
