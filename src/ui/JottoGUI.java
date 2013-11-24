package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import model.JottoModel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * This class is the GUI for the Jotto game. 
 * It relies on JottoModel, SwingWorker, and many other Javax.Swing modules.
 * I have tested it as multithreaded by creating a SwingWorker 
 * to which all of the handling of a guess is dispatched. 
 * Each individual SwingWorker takes a copy of a duplication counter, 
 * used to mark the current row of the table where the swing thread
 * is working. This duplication counter is then incremented for the next
 * SwingWorker to take their value from. 
 * 
 * A nice side effect of this is that the delay for long guesses
 * does not freeze the GUI, and the GUI also instantly responds to invalid
 * inputs, as it can work concurrently with other threads processing the inputs
 * on the same table, as all threads know where they should be working and
 * thus don't have to wait for each other. 
 * 
 * In the event that a new puzzle is requested before all the calls return, 
 * the previous calls, since their locations are now invalid, will all return
 * with exceptions as they're now out of bounds. 
 * 
 * Also, it should be noted that upon initialization, we always start
 * playing with puzzle #42. If you do not see the reference, please check the comment
 * by the line where the puzzle number is first set. If you do, I tip my hat to you. (Yay rhyming. ^_^)
 */
public class JottoGUI extends JFrame {

    // Note that most of these have become public for the Listeners to use.
    public final JButton newPuzzleButton;
    public final JTextField newPuzzleNumber;
    public JLabel puzzleNumber;
    public final JTextField guess;
    public final JTable guessTable;
    public static JPanel jottoPanel = new JPanel();
    public Random generator = new Random();
    public final JLabel guessDescription;
    private final JottoModel jottoModel = new JottoModel();
    private int counter = 0;
    public String currentGuess;
    private int duplecounter = 0;
    
    public JottoGUI() {
        newPuzzleButton = new JButton();
        newPuzzleButton.setName("newPuzzleButton");
        newPuzzleButton.setText("New Puzzle");
        
        newPuzzleNumber = new JTextField();
        newPuzzleNumber.setName("newPuzzleNumber");
        newPuzzleNumber.setToolTipText("Enter Desired Puzzle Number");
        
        puzzleNumber = new JLabel();
        puzzleNumber.setName("puzzleNumber");
        puzzleNumber.setText("Puzzle #42"); //If you are a fan of the Hitchhiker's Guide or know your Internet memes, you'll understand.
        //If you are not, then this is why: 42 is the answer to life, the universe, and everything.
        //So we always start at puzzle 42 for fun. A little tip of the hat to those who get the reference.
        
        
        guessDescription = new JLabel();
        guessDescription.setName("guessDescription");
        guessDescription.setText("Please enter your guess in the box to the right");
        
        
        guess = new JTextField();
        guess.setName("guess");
        guessTable = new JTable(new DefaultTableModel(new Object[]{"Guess", "Common", "Correct"}, 1));
        guessTable.setName("guessTable");
        final DefaultTableModel tableModel = (DefaultTableModel) guessTable.getModel();
        
        JScrollPane scrollPane = new JScrollPane(guessTable);
        scrollPane.setName("scroller");
        
        
        
        
        
        
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
        					.addComponent(guess))  
        			.addGroup(layout.createSequentialGroup())
        					.addComponent(scrollPane));
        
        
        layout.setVerticalGroup(
        		layout.createSequentialGroup()        			
        			.addGroup(layout.createParallelGroup()
        				.addComponent(newPuzzleButton)
        				.addComponent(puzzleNumber)
        				.addComponent(newPuzzleNumber))
        			.addGroup(layout.createParallelGroup()
                		.addComponent(guessDescription)
                		.addComponent(guess))
        			.addGroup(layout.createParallelGroup()
        					.addComponent(scrollPane)));
        			
        
     
        
        
        
        /**
         * This is a server messenger background worker for actually
         * carrying out communication between the server and our program.
         * 
         * Its doInBackground method carries out the server communication and
         * returns the string result of that communication.
         * 
         * Its done() method parses the result and sends the proper sections of it
         * to the event dispatch thread to update the GUI. 
         * @author DeJuan
         *
         */
        class ServerMessenger extends SwingWorker<String, String>
        {
        	//Note that here we take the current value of the duplecounter and
        	//copy it for our local version. The very first thing we do, 
        	//before anything else, is to increment this for the next worker.
        	private String result;
        	private int duplecounterLocal = new Integer(duplecounter);
     	   @Override
     	   public String doInBackground() throws Exception
     	   {
     		   duplecounter+=1;
     		   result = jottoModel.makeGuess(currentGuess, Integer.parseInt(puzzleNumber.getText().substring(8)));
     		   return result;
     	   }
     	   
     	   @Override
     	   protected void done()
     	   {
     		 
     		   try
     		   {
     			  if (result.compareTo("guess 5 5") == 0)	
					{	 	
						guessTable.setValueAt("You win!", duplecounterLocal, 1);
					}
     			  
     			 else
					{	
					guessTable.setValueAt(result.substring(6,7), duplecounterLocal, 1);
					guessTable.setValueAt(result.substring(8,9), duplecounterLocal, 2);
					}
     		   }
     		   catch(Exception expaow)
     		   {
     			   guessTable.setValueAt("Invalid guess", duplecounterLocal, 1);
     		   }
     	   }
     	   
     	   
        }
        /**
         * This action listener is used to refresh the puzzle and make changes whenever
         * we decide to change puzzles. 
         * 
         * It listens for the submission of some text in the newPuzzleNumber field. 
         * Upon detecting a submission, it attempts to parse the submission as an
         * integer. If the parsing succeeds, the puzzle is updated to the new
         * puzzle and all fields are cleared, variables reset, and the GUI refreshed.
         * If the parsing fails, we choose a random integer from 0-99999 inclusive and
         * set the new puzzle to that,and carry out the same tactic as if that random
         * were the original submission.
         * 
         * @author DeJuan
         */
        ActionListener puzzleRefresher = new ActionListener()
        {
			public void actionPerformed(ActionEvent e) throws NumberFormatException
			{ //Note the resets of the global counters.
				try
				{
					int newPuz = Integer.parseInt(newPuzzleNumber.getText());
					puzzleNumber.setText("Puzzle# "+newPuz);
					newPuzzleNumber.setText("");
					tableModel.setRowCount(0);
					tableModel.addRow(new Object[3]);
					counter = 0;
					duplecounter = 0;
				}
				catch(NumberFormatException nfeinvinp)
				{
					int randomIndex = generator.nextInt(100000);
					puzzleNumber.setText("Puzzle#"+randomIndex);
					newPuzzleNumber.setText("");
					tableModel.setRowCount(0);
					tableModel.addRow(new Object[3]);
					counter = 0;
					duplecounter = 0;
				}
				
			}
        	
        };
        /**
         * This is the action listener that actually creates ServerMessengers
         * to handle guessing.
         * 
         * It listens for when a guess is made. Upon a guess being submitted,
         * it creates and then invokes a ServerMessenger to actually carry out
         * the communication process. If the server messenger throws an
         * exception for an invalid guess, the messenger passes that exception
         * up back to this method, where the row is immediately updated with
         * the human readable "Invalid guess". 
         * 
         * However, before invoking the ServerMessenger, the Listener sets
         * the appropriate row in the current table to the "guess" value, 
         * and then makes a new row in the table so that it is prepared
         * to handle a subsequent guess at any time after the first, even if
         * the first is still being processed. 
         * 
         * @author DeJuan
         */
        ActionListener sendGuessToServer = new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		try 
        		{	
        			String attemptedGuess = guess.getText();
        			currentGuess = attemptedGuess;
        			guessTable.setValueAt(attemptedGuess, counter, 0);
        			counter+=1;
        			tableModel.addRow(new Object[3]);
					new ServerMessenger().execute();
					guess.setText("");
				} 
        		catch (Exception e1) {
        			duplecounter+=1;
        			counter+=1;
        			guessTable.setValueAt("Invalid guess", counter, 1);
					guess.setText("");
					tableModel.addRow(new Object[3]);
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
               
            }
        });
    }
    
}
