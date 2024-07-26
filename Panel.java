import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

// places components on the window
public class Panel extends JPanel implements ActionListener, KeyListener {
    private Random random = new Random();
    private ArrayList<Tile> snakesBody;
    private int velocityX;
    private int velocityY;
    private Timer timer;
    private int WIDTH;
    private int HIGHT;
    private int UNIT = 25;
    private Tile snakeHead;
    private Tile apple;
    private boolean gameOver;

    private class Tile {
        // one square is 25*25
        private int x;
        private int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setPosition(int prevX, int prevY) {
            this.x = prevX;
            this.y = prevY;
        }
    }

    private class SnakesSegment {
        private int x;
        private int y;

        public SnakesSegment(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setPosition() {
            this.x = x;
            this.y = y;
        }
    }

    public Panel(int width, int hight) {
        this.WIDTH = width;
        this.HIGHT = hight;
        setPreferredSize(new Dimension(this.WIDTH, this.HIGHT));
        setBackground(Color.BLACK);
        snakeHead = new Tile(0, 0);
        apple = new Tile(15, 15);
        placeApple();
        timer = new Timer(150, this); // drawing the frame over and over again
        timer.start();
        velocityX = 0;
        velocityY = 0;
        addKeyListener(this);
        setFocusable(true);
        this.snakesBody = new ArrayList<>();
    }

    // for panting the components
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        //Girde
        for (int i = 0; i < WIDTH / UNIT; i++) {
            graphics.drawLine(i * UNIT, 0, i * UNIT, HIGHT); // |
            graphics.drawLine(0, i * UNIT, WIDTH, i * UNIT); // -
        }
        //Snake
        graphics.setColor(Color.GREEN);
        graphics.fillRect(snakeHead.x, snakeHead.y, UNIT, UNIT);
        // now the green rectangle(snake's head) is in O(0,0), width and hight is 25 and 25
        // Apple
        graphics.setColor(Color.RED);
        graphics.fillRect(apple.x, apple.y, UNIT, UNIT);
        // each snakes body part
        for (int i = 0; i < snakesBody.size(); i++) {
            Tile snakePart = snakesBody.get(i);
            graphics.setColor(Color.GREEN);
            graphics.fillRect(snakePart.x, snakePart.y, UNIT, UNIT);
        }

        graphics.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            graphics.setColor(Color.RED);
            graphics.drawString("Game Over: " + String.valueOf(snakesBody.size()), UNIT - 16, UNIT);
        } else {
            graphics.drawString("Score: " + String.valueOf(snakesBody.size()), UNIT - 16, UNIT);
        }
    }

    public void placeApple() {
        apple.x = random.nextInt(WIDTH / UNIT) * UNIT;
        apple.y = random.nextInt(HIGHT / UNIT) * UNIT;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // every 150 second this method will be recalled and repaint
        move();
        repaint();
        if (gameOver) {
            timer.stop();
        }
    }

    public void move() {
        if (collision(snakeHead, apple)) {
            snakesBody.add(new Tile(snakeHead.x, snakeHead.y));
            placeApple();
        }
        if (snakesBody.size() > 0) {
            for (int i = snakesBody.size() - 1; i > 0; i--) {
                snakesBody.get(i).setPosition(snakesBody.get(i - 1).x, snakesBody.get(i - 1).y);
            }
            snakesBody.get(0).setPosition(snakeHead.x, snakeHead.y);
        }
        // Move the head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Check for collisions with body
        for (int i = 0; i < snakesBody.size(); i++) {
            Tile snakePart = snakesBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        // Check for collisions with walls
        if (snakeHead.x < 0 || snakeHead.x >= WIDTH || snakeHead.y < 0 || snakeHead.y >= HIGHT) {
            gameOver = true;
        }
    }


    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -UNIT;
        } else if (event.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = UNIT;
        } else if (event.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -UNIT;
            velocityY = 0;
        } else if (event.getKeyCode() == KeyEvent.VK_RIGHT && velocityY != -1) {
            velocityX = UNIT;
            velocityY = 0;
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
