import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
public class CheckerGame extends JFrame
{
    public static void main(String[] args) 
    {
        CheckerGame game = new CheckerGame();
    }
    
    public CheckerGame() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1100,700));
        setLayout(new BorderLayout());
        setTitle("Checkers");
        
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the first player's name:");
        String p1 = reader.nextLine();
        System.out.println("Please enter the second player's name:");
        String p2 = reader.nextLine();
        Stats s = new Stats(p1, p2);
        add(s, BorderLayout.NORTH);
        Board b = new Board(s);
        add(b, BorderLayout.CENTER);
        
        pack();
        setVisible(true);
    }
}
