package snakeGame;


/* CPCS - 391
*  GROUP PROJECT
*  1. Eyad Halwani (Leader) - 1847263
*  2. Khalid Alghamdi - 1846409
*  3. Mohammed Miaji - 1845953 
*/
public class Location {
    // variables
    private double x;
    private double y;
    
    // accessors, mutators
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    
    public void setX(double value) {
        x = value;
    }
    public void setY(double value) {
        y = value;
    }
    
    // constructor
    public Location() {
        x = 0;
        y = 0;
    }
    public Location(double val1, double val2) {
        x = val1;
        y = val2;
    }
    
    public boolean equals(Location other) {
        if (x == other.getX() && y == other.getY()) {
            return true;
        } else {
            return false;
        }
    }
}