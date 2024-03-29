/*package deprecatedCode;

public class JottoGUIMixedNonConcurrentAndConcurrentCodeComments {

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

	*//**
	 * // TODO Write specifications for your JottoGUI class.
	 * // Remember to name all your components, otherwise autograder will give a zero.
	 * // Remember to use the objects newPuzzleButton, newPuzzleNumber, puzzleNumber,
	 * // guess, and guessTable in your GUI!
	 *//*
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
	        
	        
	        class JottoTableModel extends AbstractTableModel
	        {	Object[][] rowData = new Object[30][3];
	        	public int getRowCount() {return rowData.length;}
	        	public int getColumnCount() { return 3;}
	        	public Object getValueAt(int row, int col){
	        		return null; //Don't think you can actually get the data...
	        	}
	        	public boolean isCellEditable(int row, int col) {return false;}
	        	public void setValueAt(Object value, int row, int col)
	        	{
	        		rowData[row][col] = value;
	        		fireTableCellUpdated(row, col);
	        	}
	        	
	        	public void resetTable(){
	        		rowData = new Object[30][3];
	        		fireTableStructureChanged();
	        	}
	        	
	        }
	        //JottoTableModel mod = new JottoTableModel();
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
	        					.addComponent(guess))   // );
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
							//duplecounter+=1;
							//tableModel.addRow(new Object[3]);
						}
	     			  
	     			 else
						{	
						guessTable.setValueAt(result.substring(6,7), duplecounterLocal, 1);
						guessTable.setValueAt(result.substring(8,9), duplecounterLocal, 2);
						//duplecounter +=1;
						//tableModel.addRow(new Object[3]);
						}
	     		   }
	     		   catch(Exception expaow)
	     		   {
	     			   guessTable.setValueAt("Invalid guess", duplecounterLocal, 1);
	     			   //duplecounter +=1;
	     		   }
	     	   }
	     	   
	     	   
	        }
	        
	        ActionListener puzzleRefresher = new ActionListener()
	        {
				public void actionPerformed(ActionEvent e) throws NumberFormatException
				{
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
	        			//System.out.println(guess.getText());
	        			//System.out.println(Integer.parseInt(puzzleNumber.getText().substring(8)));
	        			String attemptedGuess = guess.getText();
	        			currentGuess = attemptedGuess;
	        			guessTable.setValueAt(attemptedGuess, counter, 0);
	        			counter+=1;
	        			tableModel.addRow(new Object[3]);
						//String result = jottoModel.makeGuess(attemptedGuess, Integer.parseInt(puzzleNumber.getText().substring(8)));
						new ServerMessenger().execute();
						//System.out.println(result);
						guess.setText("");
						
						if (result.compareTo("guess 5 5") == 0)	
						{	
							guessTable.setValueAt("You win!", counter, 1);
							counter+=1;
							tableModel.addRow(new Object[3]);
						}
						else
						{	
						guessTable.setValueAt(result.substring(6,7), counter, 1);
						guessTable.setValueAt(result.substring(8,9), counter, 2);
						counter +=1;
						tableModel.addRow(new Object[3]);
						}
						
					} 
	        		catch (Exception e1) {
	        			duplecounter+=1;
	        			counter+=1;
	        			guessTable.setValueAt("Invalid guess", counter, 1);
						guess.setText("");
						
						//duplecounter+=1;
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

}
*/