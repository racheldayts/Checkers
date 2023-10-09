import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
public class Board extends JPanel implements ActionListener
{
    public static final int INVALID = -1;
    public static final int EMPTY = 0;
    public static final int RED = 1;
    public static final int BLACK = 2;
    
    private Square[] squares;
    private int firstClickId;
    private int secondClickId;
    private int currentPlayer;
    private Stats s;
    private GridBagConstraints c = null;
    
    public Board(Stats s) 
    {
        start();
        this.s = s;
    }
    
    public void start() 
    {
        if (c != null) 
        {
            for (int i = 0; i < squares.length; i++) 
            {
                remove(squares[i].getButton());
            }
        }
        
        squares = new Square[64];
        for(int i=0; i<squares.length; i++) 
        {
            squares[i] = new Square(i);
        }
        setBoard();
        
        firstClickId = -1;
        secondClickId = -1;
        currentPlayer = 1;
        addAction();
    }
    
    public void setBoard() 
    {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        
        c.fill=GridBagConstraints.BOTH;
        c.ipady=20;
        c.ipadx=50;
        
        c.gridwidth = 1;
        c.gridheight = 1;
        for (int i = 0; i < squares.length; i++) 
        {
            c.gridx = i % 8;
            c.gridy = i / 8;
            add(squares[i].getButton(), c);
        }
    }
    
    public void addAction() 
    {
        for (int i = 0; i < squares.length; i++) 
        {
            squares[i].getButton().addActionListener(this);
        }
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        for (int i = 0; i < squares.length; i++) 
        {
            if (e.getSource() == squares[i].getButton()) 
            {
                if (squares[i].getCurrentState() == INVALID) 
                {
                    return;
                }
                if ((currentPlayer == 1 && squares[i].getCurrentState() == BLACK)
                    || (currentPlayer == 2 && squares[i].getCurrentState() == RED))
                {
                    return;
                }
                
                if (firstClickId == -1) 
                { // first click
                    if (squares[i].getCurrentState() == EMPTY)
                    {
                        return;
                    }
                    firstClickId = squares[i].getID();
                    squares[i].highlight(true);
                    return;
                }
                
                // second click

                if (squares[i].getCurrentState() == currentPlayer) 
                {
                    startSquare().highlight(false);
                    firstClickId = squares[i].getID();
                    squares[i].highlight(true);
                    return;
                }
                if (squares[i].getCurrentState() != EMPTY) 
                {
                    return;
                }
                secondClickId = squares[i].getID();
                if (makeMove()) 
                {
                    if (currentPlayer == RED) 
                    {
                        currentPlayer = BLACK;
                    } else {
                        currentPlayer = RED;
                    }
                    int winner = isWinner();
                    if (winner != -1) 
                    {
                        s.updateScore(winner);
                        startSquare().highlight(false);
                        start();
                        return;
                    }
                }
                startSquare().highlight(false);
                firstClickId = -1;
                secondClickId = -1;
            }
        }
    }
    
    public boolean makeMove() 
    {
        int difference;
        boolean isKingMovingBackward = false;
        
        if (currentPlayer == RED) 
        {
            difference = firstClickId - secondClickId;
        } else { // black
            difference = secondClickId - firstClickId;
        }
        
        if (startSquare().isKing())
        {
            if (difference < 0) 
            {
                isKingMovingBackward = true;
            }
            difference = Math.abs(difference);
        }
        
        if (difference == 7 || difference == 9) 
        {
            endSquare().setCurrentState(currentPlayer);
            if (isKingable()) 
            {
                endSquare().setKing();
            }
            startSquare().setCurrentState(EMPTY);
            
            return true;
        }
        
        int clickId = (currentPlayer == RED ? secondClickId : firstClickId);
        if (isKingMovingBackward)
        {
            clickId = (currentPlayer == RED ? firstClickId : secondClickId);
        }
        if (difference == 14 && squares[clickId + 7].getCurrentState() != currentPlayer
                || difference == 18 && squares[clickId + 9].getCurrentState() != currentPlayer)
                {
            endSquare().setCurrentState(currentPlayer);
            if (isKingable()) 
            {
                endSquare().setKing();
            }
            
            squares[(difference/2) + clickId].setCurrentState(EMPTY);
            
            startSquare().setCurrentState(EMPTY);
            return true;
        }
        
        return false;
    }
    
    public boolean isKingable() {
        if (startSquare().isKing())
        {
            return true;
        }
        
        if (currentPlayer == RED && secondClickId <= 7)  
        {
            return true;
        }
        if (currentPlayer == BLACK && secondClickId >= 56) 
        {
            return true;
        }
        return false;
    }
    
    public Square startSquare() 
    {
        return squares[firstClickId];
    }
    
    public Square endSquare()
    {
        return squares[secondClickId];
    }
    
    public int isWinner() 
    {
        ArrayList<Integer> idsOfPieces = new ArrayList<Integer>();
        for (int i = 0; i < squares.length; i++) 
        {
            if (squares[i].getCurrentState() == currentPlayer) 
            {
                idsOfPieces.add(i);
            }
        }
        
        for (int i = 0; i < idsOfPieces.size(); i++) 
        {
            if (squares[idsOfPieces.get(i)].isKing() || squares[idsOfPieces.get(i)].getCurrentState() == RED) 
            {
                if (idsOfPieces.get(i)-7 >= 0 && squares[idsOfPieces.get(i)-7].getCurrentState() == EMPTY) 
                {
                    return -1;
                }
                if (idsOfPieces.get(i)-9 >= 0 && squares[idsOfPieces.get(i)-9].getCurrentState() == EMPTY) 
                {
                    return -1;
                }
            }
            
            if (squares[idsOfPieces.get(i)].isKing() || squares[idsOfPieces.get(i)].getCurrentState() == BLACK) 
            {
                if (idsOfPieces.get(i)+7 < 64 && squares[idsOfPieces.get(i)+7].getCurrentState() == EMPTY) 
                {
                    return -1;
                }
                if (idsOfPieces.get(i)+9 < 64 && squares[idsOfPieces.get(i)+9].getCurrentState() == EMPTY) 
                {
                    return -1;
                }
            }
            
            int opponent = (currentPlayer == RED ? BLACK : RED);
            if (squares[idsOfPieces.get(i)].isKing() || squares[idsOfPieces.get(i)].getCurrentState() == RED) 
            {
                if (idsOfPieces.get(i)-14 >= 0 && squares[idsOfPieces.get(i)-14].getCurrentState() == EMPTY && squares[idsOfPieces.get(i)-7].getCurrentState() == opponent)
                {
                    return -1;
                }
                if (idsOfPieces.get(i)-18 >= 0 && squares[idsOfPieces.get(i)-18].getCurrentState() == EMPTY && squares[idsOfPieces.get(i)-9].getCurrentState() == opponent) 
                {
                    return -1;
                }
            }
            
            if (squares[idsOfPieces.get(i)].isKing() || squares[idsOfPieces.get(i)].getCurrentState() == BLACK) 
            {
                if (idsOfPieces.get(i)+14 < 64 && squares[idsOfPieces.get(i)+14].getCurrentState() == EMPTY && squares[idsOfPieces.get(i)+7].getCurrentState() == opponent) 
                {
                    return -1;
                }
                if (idsOfPieces.get(i)+18 < 64 && squares[idsOfPieces.get(i)+18].getCurrentState() == EMPTY && squares[idsOfPieces.get(i)+9].getCurrentState() == opponent) 
                {
                    return -1;
                }
            }
        }
        if (currentPlayer == RED) 
        {
            return BLACK;
        } else 
        {
            return RED;
        }
    }
}






