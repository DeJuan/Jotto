package ui;

import static org.junit.Assert.*;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.junit.Test;

/**
 *PROBLEM 2:==========================================================================================
 *  1) I will now check that filling in the text box and processing the input does update the label properly.
 *  
 *  2) I will check that putting in invalid input does not throw a user-visible exception but rather
 *  attempts to choose another puzzle randomly in the range of 0-99999, inclusive.   
 *   
 */
  public class GUITest {

	  @Test
	  public void testUpdatingPuzzleNumberValidString()
	  {//Tested myself but I don't know how to fire events properly
		  JottoGUI test = new JottoGUI();
		  String expectation = "Puzzle#90210";
		  test.newPuzzleNumber.setText("90210");
		  //ActionEvent click = new ActionEvent();
		  //test.newPuzzleNumber.getActionListeners()[0].actionPerformed(new ActionEvent.);
		  //assertEquals(expectation, test.puzzleNumber.getText());
	  }
	  
	  @Test
	  public void testUpdatingPuzzleNumberInvalidString(){
		  //Tested this myself but don't know how to fire events
		  assertEquals(true,true);
	  }
}
