import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.border.LineBorder;

public class Square 
{
    private JButton button;
    private int id;
    private int currentState;
    private boolean isKing;
    
    public Square(int id) 
    {
        this.id = id;
        isKing = false;
        
        button = new JButton();
        
        currentState = getSquareInitialState();
        setCurrentState(currentState);
        
        for (int i = 0; i < 64; i++) 
        {
            if (currentState == -1) 
            {
                button.setBackground(Color.WHITE);
            } else 
            {
                button.setBackground(Color.GRAY);
            }
            button.setBorder(new LineBorder(Color.GRAY));
            button.setFocusPainted(false);
        }
    }
    
    public int getSquareInitialState() 
    {
        switch(id) 
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
            case 14:
            case 17:
            case 19:
            case 21:
            case 23:
                return 2;
            case 24:
            case 26:
            case 28:
            case 30:
            case 33:
            case 35:
            case 37:
            case 39:
                return 0;
            case 40:
            case 42:
            case 44:
            case 46:
            case 49:
            case 51:
            case 53:
            case 55:
            case 56:
            case 58:
            case 60:
            case 62:
                return 1;
        }
        return -1;
    }
    
    public int getID() 
    {
        return id;
    }
    
    public boolean isKing() 
    {
        return isKing;
    }
    
    public void setKing() 
    {
        isKing = true;
        setCurrentState(currentState);
    }
    
    public JButton getButton() 
    {
        return button;
    }
    
    public int getCurrentState() 
    {
        return currentState;
    }
    
    public void setCurrentState(int newState) 
    {
        currentState = newState;
        String imagePath;
        if (currentState == 1) 
        { // red
            imagePath = "resources/red-" + (isKing?"king-":"") + "50px.png";
        } else if (currentState == 2) 
        { // black
            imagePath = "resources/black-" + (isKing?"king-":"") + "50px.png";
        } else 
        {
            imagePath = "resources/blank-50px.png";
        }
        
        try 
        {
            Image img = ImageIO.read(getClass().getResource(imagePath));
            button.setIcon(new ImageIcon(img));
        } catch (Exception ex) 
        {
            System.out.println(ex);
        }
        
        if (currentState == 0) 
        {
            button.setIcon(null);
            isKing = false;
        }
    }
    
    public void highlight(boolean isFlag) 
    {
        if (isFlag) 
        {
            button.setBorder(new LineBorder(Color.RED));
        } else 
        {
            button.setBorder(new LineBorder(Color.GRAY));
        }
    }
}


