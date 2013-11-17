package model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * For my testing strategy:
 * 
 * PROBLEM 1:==============================================================================
 *  1) I'm going to make sure that I can get the expected
 *  replies back from the server as outlined in the problem set.
 * 
 *  2) Once I am confident that the server reads correctly, I will add code for handling
 *  server errors to the Model, then test my code against a server error from invalid word. 
 *  
 *  3) I'll then test what happens if the word is not the proper length. This should already be handled, since
 *  the server's error message always starts with "error" and I've used error detection on that word. 
 *  END PROBLEM 1=======================================================================================
 *  
 *  
 * @author DeJuan
 *
 */
public class ModelTest 
{
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void testServerReadingValidInputs() throws Exception
	{
		//This assumes server number 5555, with keyword vapid.
		JottoModel test = new JottoModel();
		String response = test.makeGuess("rapid", 5555);
		assertEquals("guess 4 4", response);
		String responsetwo = test.makeGuess("bulls", 5555);
		assertEquals("guess 0 0", responsetwo);
		String finalResponse = test.makeGuess("vapid", 5555);
		assertEquals("guess 5 5", finalResponse);
	}
	
	@Test(expected=RuntimeException.class)
	public void testServerReadingInvalidWordError() throws Exception
	{
		JottoModel test = new JottoModel();
		String invalidWordResponse = test.makeGuess("pirda", 5555);
	}
	
	@Test(expected=RuntimeException.class)
	public void testServerReadingInvalidWordLengthError() throws Exception
	{
		JottoModel test = new JottoModel();
		String invalidWordLengthResponse = test.makeGuess("pie", 5555);
	}
}
