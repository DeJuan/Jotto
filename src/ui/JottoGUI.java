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
 * with exceptions as they're now out of bounds. I haven't quite figured out
 * exactly how to set their "isCancelled" status, but for now, since that won't
 * actually interfere with playing with the new puzzle as those exceptions go off
 * in the background, it is a problem that can be bypassed for now and potentially revisited
 * for the final.
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
        puzzleNumber.setText("Puzzle #42");
        
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
