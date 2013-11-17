package ui;

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
    private final JButton newPuzzleButton;
    private final JTextField newPuzzleNumber;
    private final JLabel puzzleNumber;
    private final JTextField guess;
    private final JTable guessTable;
    public static JPanel jottoPanel = new JPanel();
    
    public JottoGUI() {
        newPuzzleButton = new JButton();
        newPuzzleButton.setName("newPuzzleButton");
        newPuzzleButton.setText("New Puzzle");
        
        newPuzzleNumber = new JTextField();
        newPuzzleNumber.setName("newPuzzleNumber");
        newPuzzleNumber.setText("Enter Desired Puzzle Number");
        
        puzzleNumber = new JLabel();
        puzzleNumber.setName("puzzleNumber");
        puzzleNumber.setText("Puzzle #Placeholder");
        
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
        //layout.setAutoCreateContainerGaps(true);
        //layout.setAutoCreateGaps(true);
       
        
        layout.setHorizontalGroup(
        		layout.createSequentialGroup()
        			.addGroup(layout.createSequentialGroup()
        					.addComponent(puzzleNumber)
        					.addComponent(newPuzzleButton)
        					.addComponent(newPuzzleNumber)));
        
        
        layout.setVerticalGroup(
        		layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup()
        				.addComponent(puzzleNumber)
        				.addComponent(newPuzzleButton)
        				.addComponent(newPuzzleNumber)));
        		
        
        
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
