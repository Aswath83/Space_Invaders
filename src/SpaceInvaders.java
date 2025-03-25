import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener {
    private int boardWidth = 800, boardHeight = 600;
    private int playerX = 380, playerY = 550, playerSpeed = 10;
    private boolean left, right, shooting;
    private ArrayList<Rectangle> bullets, aliens, alienBullets;
    private int score = 0;
    private Timer timer, gameTimer;
    private int timeLeft = 60;
    private int alienDirection = 1;
    private int alienSpeed = 2;
    private int waveFrequency = 100;
    private JFrame frame;
    private boolean canShoot = true; // To prevent continuous shooting

    public SpaceInvaders(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        bullets = new ArrayList<>();
        aliens = new ArrayList<>();
        alienBullets = new ArrayList<>();

        spawnAliens();

        timer = new Timer(30, this);
        timer.start();

        gameTimer = new Timer(1000, e -> {
            timeLeft--;
            if (timeLeft <= 0) {
                winGame();
            }
        });
        gameTimer.start();
    }

    private void spawnAliens() {
        aliens.clear();
        Random rand = new Random();
        int numAliens = 10 + rand.nextInt(15); // Spawn between 10 to 25 aliens

        for (int i = 0; i < numAliens; i++) {
            int x = rand.nextInt(boardWidth - 50); // Random X within screen width
            int y = rand.nextInt(boardHeight / 2 - 40); // Keep aliens in upper half

            // Ensure aliens don't spawn too low (near player)
            if (y > playerY - 100) {
                y -= 100;
            }

            aliens.add(new Rectangle(x, y, 40, 30));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, 40, 20);

        g.setColor(Color.RED);
        for (Rectangle alien : aliens) {
            g.fillRect(alien.x, alien.y, alien.width, alien.height);
        }

        g.setColor(Color.YELLOW);
        for (Rectangle bullet : bullets) {
            g.fillRect(bullet.x, bullet.y, 5, 10);
        }

        g.setColor(Color.CYAN);
        for (Rectangle bullet : alienBullets) {
            g.fillRect(bullet.x, bullet.y, 5, 10);
        }
        
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
        g.drawString("Time Left: " + timeLeft + "s", 700, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (left && playerX > 0) playerX -= playerSpeed;
        if (right && playerX < getWidth() - 40) playerX += playerSpeed;

        // Shooting logic: Only fire once per key press
        if (shooting && canShoot) {
            bullets.add(new Rectangle(playerX + 18, playerY, 5, 10));
            canShoot = false; // Prevent continuous shooting until key is released
        }

        bullets.removeIf(bullet -> (bullet.y -= 10) < 0);
        alienBullets.removeIf(bullet -> (bullet.y += 10) > getHeight());

        for (Rectangle alien : aliens) {
            alien.x += alienSpeed * alienDirection;
            alien.y += Math.sin(alien.x / waveFrequency) * 2;
        }

        // Reverse direction when hitting screen bounds
        for (Rectangle alien : aliens) {
            if (alien.x < 0 || alien.x > getWidth() - 40) {
                alienDirection *= -1;
                aliens.forEach(a -> a.y += 10);
                break;
            }
        }

        // Aliens shoot bullets randomly
        if (Math.random() < 0.02 && !aliens.isEmpty()) {
            Rectangle alien = aliens.get((int) (Math.random() * aliens.size()));
            alienBullets.add(new Rectangle(alien.x + 18, alien.y + 30, 10, 10));
        }

        // Collision detection for bullets and aliens
        Iterator<Rectangle> itB = bullets.iterator();
        while (itB.hasNext()) {
            Rectangle bullet = itB.next();
            Iterator<Rectangle> itA = aliens.iterator();
            
            while (itA.hasNext()) {
                Rectangle alien = itA.next();
                if (bullet.intersects(alien)) {
                    itA.remove();  // Remove the alien
                    itB.remove();  // Remove the bullet after collision
                    score += 10;
                    break; // Stop checking other aliens for this bullet
                }
            }
        }

        // If all aliens are destroyed, respawn them
        if (aliens.isEmpty()) spawnAliens();

        // Check for game over conditions
        if (aliens.stream().anyMatch(alien -> alien.y >= playerY) ||
            alienBullets.stream().anyMatch(bullet -> bullet.intersects(new Rectangle(playerX, playerY, 40, 20)))) {
            gameOver();
        }

        repaint();
    }

    private void gameOver() {
        timer.stop();
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        returnToMenu();
    }

    private void winGame() {
        timer.stop();
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "Yay! You succeeded! Your final score: " + score);
        returnToMenu();
    }

    private void returnToMenu() {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) shooting = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shooting = false;
            canShoot = true; // Reset shooting ability when key is released
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}

class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invaders");
        SpaceInvaders game = new SpaceInvaders(frame);
        
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
