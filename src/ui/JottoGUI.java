package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import model.JottoModel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * // TODO Write specifications for your JottoGUI class.
 * // Remember to name all your components, otherwise autograder will give a zero.
 * // Remember to use the objects newPuzzleButton, newPuzzleNumber, puzzleNumber,
 * // guess, and guessTable in your GUI!
 */
public class JottoGUI extends JFrame {

    // remember to use these objects in your GUI:
    public final JButton newPuzzleButton;
    public final JTextField newPuzzleNumber;
    public JLabel puzzleNumber;
    public final JTextField guess;
    public final JTable guessTable;
    public static JPanel jottoPanel = new JPanel();
    public Random generator = new Random();
    public final JLabel guessDescription;
    private final JottoModel jottoModel = new JottoModel();
    
    public JottoGUI() {
        newPuzzleButton = new JButton();
        newPuzzleButton.setName("newPuzzleButton");
        newPuzzleButton.setText("New Puzzle");
        
        newPuzzleNumber = new JTextField();
        newPuzzleNumber.setName("newPuzzleNumber");
        newPuzzleNumber.setToolTipText("Enter Desired Puzzle Number");
        
        puzzleNumber = new JLabel();
        puzzleNumber.setName("puzzleNumber");
        puzzleNumber.setText("Puzzle #42");
        
        guessDescription = new JLabel();
        guessDescription.setName("guessDescription");
        guessDescription.setText("Please enter your guess in the box to the right");
        
        
        guess = new JTextField();
        guess.setName("guess");
        
        
        guessTable = new JTable();
        guessTable.setName("guessTable");
        
        /*
        jottoPanel.add(puzzleNumber);
        jottoPanel.add(newPuzzleButton);
        jottoPanel.add(newPuzzleNumber);
        */
        //Problem 2: Create the top row of the GUI. They're in horizontal sequence. 
        //horizontal layout = sequential group { c1, c2, c3 }
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
       
        
        layout.setHorizontalGroup(
        		layout.createParallelGroup()
        			.addGroup(layout.createSequentialGroup()
        					.addComponent(puzzleNumber)
        					.addComponent(newPuzzleButton)
        					.addComponent(newPuzzleNumber))
        			.addGroup(layout.createSequentialGroup()
        					.addComponent(guessDescription)
        					.addComponent(guess)));
        
        
        layout.setVerticalGroup(
        		layout.createParallelGroup()
        			.addGroup(layout.createSequentialGroup()
        				.addComponent(puzzleNumber)
                		.addComponent(guessDescription))
        			.addGroup(layout.createSequentialGroup()
        				.addComponent(newPuzzleButton)
        				.addComponent(guess))
        			.addComponent(newPuzzleNumber));
        			
        
        ActionListener puzzleRefresher = new ActionListener()
        {
			public void actionPerformed(ActionEvent e) throws NumberFormatException
			{
				try
				{
					int newPuz = Integer.parseInt(newPuzzleNumber.getText());
					puzzleNumber.setText("Puzzle# "+newPuz);
				}
				catch(NumberFormatException nfeinvinp)
				{
					int randomIndex = generator.nextInt(100000);
					puzzleNumber.setText("Puzzle#"+randomIndex);
				}
				
			}
        	
        };
        
        ActionListener sendGuessToServer = new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		try 
        		{	
        			//System.out.println(guess.getText());
        			//System.out.println(Integer.parseInt(puzzleNumber.getText().substring(8)));
					String result = jottoModel.makeGuess(guess.getText(), Integer.parseInt(puzzleNumber.getText().substring(8)));
					System.out.println(result);
					guess.setText("");
				} 
        		catch (Exception e1) {
					guess.setText("");
				}
        	}
        };
        
        newPuzzleButton.addActionListener(puzzleRefresher);
        newPuzzleNumber.addActionListener(puzzleRefresher);
        
        guess.addActionListener(sendGuessToServer);
        
        layout.linkSize(puzzleNumber, newPuzzleButton, newPuzzleNumber);
        this.pack();
        }
        
     
      

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JottoGUI main = new JottoGUI();

                main.setVisible(true);
                //jottoPanel.setVisible(true);
            }
        });
    }
}
