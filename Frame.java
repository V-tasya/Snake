import javax.swing.*;
import java.util.Random;

public class Frame extends JFrame {
    // functional window with title and signs
   private JFrame frame = new JFrame("Snake");


    public Frame() {
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);// opening the window at the center of the screen
        frame.setResizable(false); // we can't change the window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // for terminating the program
        Panel panel = new Panel(600, 600);
        frame.add(panel); //panel inside the frame
        frame.pack(); // now th panel is 600*600
        panel.requestFocus(); // now the game listening the key process
    }
}
