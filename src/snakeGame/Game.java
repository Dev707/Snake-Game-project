package snakeGame;
/* CPCS - 391
*  GROUP PROJECT
*  1. Eyad Halwani (Leader) - 1847263
*  2. Khalid Alghamdi - 1846409
*  3. Mohammed Miaji - 1845953 
*/


import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Color;
import snakeGame.Snake;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import java.util.*;
import javax.media.opengl.GLAutoDrawable;
import static javax.swing.JOptionPane.showMessageDialog;
import org.lwjgl.input.Keyboard;

public class Game {
    // Variables
    GLAutoDrawable drawable;
    private final int POINTS_PER_MEAL = 10;
    private final int FRAME_RATE = 60;
    private Snake snake;
    private Square food;
    private Square bomb;
    private long count = 0; // counts the frames which have elapsed (snake will only move every few count values to control speed)
    private final int SPEED = 7;    // how often to move the snake (# of frames)
    private int points;
    private int maxx;
    private int maxy;
    private Random uberDice;
    private int counter = 3;
    
    // Accessors/Mutators
    
    // Constructor: parameters are the max x and y values of the board (pixels)
    public Game(int x, int y) {
        // display stuff
        maxx = x;
        maxy = y;
        
        //opengl/lwjgl stuff
        initDisplay();
        initOpenGL();
        Keyboard.enableRepeatEvents(false);
        
        // game stuff
        uberDice = new Random();
        snake = new Snake(0, 0);
        food = new Square();
        bomb = new Square();
        initFood();
        initBomb();
        points = 0;
    }
    
    // Methods
    
    // Manages the game
    public void startGame() {
        message();
        while(!Display.isCloseRequested()) {
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            
            // game management
            if (count % SPEED == 0) {
                manageInput();
            }
            snake.draw();
            growFood();
            growBomb();
            handleCollisions();
            if (count % SPEED == 0) {
                snake.move();
            }
            count++;
            
            Display.update();
            Display.sync(FRAME_RATE);
        }
        
        gameOver();
    }
    
    // generates a random coordinate for the food to spawn on. It needs to make sure that it will be 'aligned' with a space that the snake's
    //head will occupy, so itneds to be a multiple of (square size + buffer[in snake class])
    public Location generateRandomCoordinate() {
        Location random;
        double x = 0;
        double y = 0;
        int temp;
        int gridX = maxx / (int)(Square.SIZE + snake.BUFFER);     // number of valid x coordinates on the screen
        int gridY = maxy / (int)(Square.SIZE + snake.BUFFER);     // number of valid y coordinates on the screen
        
        // generating random x and y:
        temp = uberDice.nextInt(gridX);
        x = temp * (Square.SIZE + snake.BUFFER);
        temp = uberDice.nextInt(gridY);
        y = temp * (Square.SIZE + snake.BUFFER);
        
        // create and return location
        random = new Location(x, y);
        return random;
    }
    
    // Manages the input
    public void manageInput() {
        while(Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_UP && snake.getDirection() != Direction.DOWN) {
                snake.setDirection(Direction.UP);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && snake.getDirection() != Direction.UP) {
                snake.setDirection(Direction.DOWN);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && snake.getDirection() != Direction.RIGHT) {
                snake.setDirection(Direction.LEFT);
            } else if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && snake.getDirection() != Direction.LEFT) {
                snake.setDirection(Direction.RIGHT);
            }
        }
    }
    
    public void addPoints(int num) {
        points += num;
    }
    
    public void removePoints(int num) {
        points -= num;
    }
    
    // creates new food
    public void growFood(int x, int y) {
        food.setLocation(x, y);
        growFood();
    }
    public void growFood() {
        food.draw(1);
    }
    public void growFood(Location loc) {
        food.setLocation(loc);
        growFood();
    }
    //bomb-------
    public void growBomb(int x, int y) {
        bomb.setLocation(x, y);
        growBomb();
    }
    public void growBomb() {
        bomb.draw(0);
    }
    public void growBomb(Location loc) {
        bomb.setLocation(loc);
        growBomb();
    }
    
    // initializes a new food
    public void initFood() {
        food.setLocation(generateRandomCoordinate());
    }
    //bomb-----
    public void initBomb() {
        bomb.setLocation(generateRandomCoordinate());
    }
    // Checks and handles collisions
    public void handleCollisions() {
        // handles out of bounds
        if (outOfBounds()) {
            System.out.println("Out of Bounds");
            gameOver();
        }
        //handles food being eaten
        if (foodEaten()) {
            addPoints(POINTS_PER_MEAL);
            initFood();
            snake.addSegment();
            message();
        }
        if (bombEaten()) {
            initBomb();
            if (counter <= 1){
            gameOver();
            }
            else {
                counter--; //This will decrease the number of tries
                message();
            }
            
        }
        
        // handles snake eating itself
        if (cannibal()) {
            System.out.println("--------------------------------");
            System.out.println("Cannibal");
            gameOver();
        }
        
        
    }
    //This is a message 
    public void message() {
    System.out.println("--------------------------------");
                System.out.println("Number of tries left " + counter);
                System.out.println("Your Score: " + points);
    }
    // detects if snake goes out of bounds
    public boolean outOfBounds() {
        boolean out = false;
        Direction dir = snake.getDirection();
        if (dir == Direction.UP && snake.getLocation().getY() > maxy - Square.SIZE) {
            out = true;
        } else if (dir == Direction.DOWN && snake.getLocation().getY() < 0) {
            out = true;
        } else if (dir == Direction.LEFT && snake.getLocation().getX() < 0) {
            out = true;
        } else if (dir == Direction.RIGHT && snake.getLocation().getX() > maxx - Square.SIZE) {
            out = true;
        }
        return out;
    }
    // detects if the snake eats the food
    public boolean foodEaten() {
        if (snake.getLocation().equals(food.getLocation())) {
            return true;
        } else {
            return false;
        }
    }
    //bomb-----
    public boolean bombEaten() {
        if (snake.getLocation().equals(bomb.getLocation())) {
            return true;
        } else {
            return false;
        }
    }
    //detects when the snake eats itself
    public boolean cannibal() {
        boolean check = false;
        Square[] array = snake.getSegments();
        for (int i = 1; i < array.length; i++) {
            if (snake.getLocation().equals(array[i].getLocation())) {
                check = true;
            }
        }
        return check;
    }
    
    // called when snake touches the edges
    public void gameOver() {
//        TextRenderer t = new TextRenderer(new java.awt.Font("Impact", java.awt.Font.BOLD, 40));
//        t.beginRendering(drawable.getWidth(), drawable.getHeight());
//        t.setColor(Color.RED);
//        t.draw("Game Over", 920, 500);
//        t.draw("" + points, 970, 450);
//        t.endRendering();
        
        Display.destroy();
        showMessageDialog(null, "Game Over\n you scored: " + points);
        System.out.println("Game over");
        System.out.println("Your Score: " + points);
        System.exit(0);
    }
     
    // initializes opengl
    public void initOpenGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 1000, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }
    
    // initializes display
    public void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(maxx, maxy));
            Display.setTitle("Snake Game");
            Display.create();
        } catch (LWJGLException ex) {
            System.out.println("LWJGL Exception Thrown");
            System.exit(1);
        }
    }
}