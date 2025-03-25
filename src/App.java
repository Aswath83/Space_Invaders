import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        int tileSize = 32;
        int rows = 16;
        int columns = 16;
        int boardWidth = tileSize * columns; // 32 * 16 = 512
        int boardHeight = tileSize * rows;   // Fixed the height calculation

        JFrame frame = new JFrame("Space Invaders");
        frame.setSize(boardWidth, boardHeight); // Set frame size
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SpaceInvaders spaceInvaders = new SpaceInvaders(frame); // Pass the frame instance
        frame.add(spaceInvaders);
        frame.pack();
        frame.setVisible(true);
        
        spaceInvaders.requestFocus(); // Ensure the game panel gets focus
    }
}
