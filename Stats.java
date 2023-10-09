import java.awt.*;
import javax.swing.*;
public class Stats extends JPanel
{
    private JLabel playerOneName;
    private JLabel playerOneScore;
    
    private JLabel playerTwoName;
    private JLabel playerTwoScore;
    
    private JLabel win;
    
    // sets player names and scores, and add them to the screen
    public Stats(String p1, String p2) 
    {
        playerOneName = new JLabel(p1);
        playerOneName.setFont(new Font("Verdana", Font.PLAIN, 28));
        add(playerOneName);
        playerOneScore = new JLabel("0");
        playerOneScore.setFont(new Font("Verdana", Font.PLAIN, 28));
        add(playerOneScore);
        
        playerTwoName = new JLabel("  " + p2);
        playerTwoName.setFont(new Font("Verdana", Font.PLAIN, 28));
        add(playerTwoName);
        playerTwoScore = new JLabel("0");
        playerTwoScore.setFont(new Font("Verdana", Font.PLAIN, 28));
        add(playerTwoScore);
        
        win=new JLabel("");
        win.setFont(new Font("Verdana", Font.PLAIN, 28));
        add(win);
    }
    
    // updates the score of the winning player by adding 1 point and updating the scores on the screen
    public void updateScore(int winner) 
    {
        int p1Score = Integer.parseInt(playerOneScore.getText());
        int p2Score = Integer.parseInt(playerTwoScore.getText());
        
        if (winner == Board.RED) 
        {
            p1Score++;
            playerOneScore.setText("" + p1Score);
        } else if (winner == Board.BLACK) 
        {
            p2Score++;
            playerTwoScore.setText("" + p2Score);
        }
        
        if (p1Score == p2Score) 
        {
            win.setText("  Tie!");
        } 
        else if (p1Score > p2Score) 
        {
            win.setText("  " + playerOneName.getText() + " is winning!");
        } else 
        {
            win.setText("  " + playerTwoName.getText() + " is winning!"); 
        }

        add(playerOneName);
        add(playerOneScore);
        add(playerTwoName);
        add(playerTwoScore);
        add(win);
    }
}
