package model;

import java.net.*;
import java.io.*;

/**
 * // TODO Write specifications for your JottoModel!
 */
public class JottoModel {
    
    /**
     * This method uses java.net.URL to communicate with the 6.005
     * Jotto server and send the guess. From the server, we
     * receive an stream containing the server's response. 
     * This server is located at the following and uses the contexts subbed in to communicate:
     * http://courses.csail.mit.edu/6.005/jotto.py?puzzle=[puzzle #]&guess=[5-letter dictionary word]
     * 
     * 
     * @return String representing server's reply to a valid input. 
     * @throws MalformedURLException if the URL given doesn't exist
     * @throws IOException if there's something wrong with the readers
     * @throws RuntimeException if the above are fine, but the input to server was invalid and caused an error. 
     */
    public String makeGuess(String guess, int currentPuzzle) throws Exception {
    	int puzzleNumber = currentPuzzle;
        URL jottoSite = new URL("http://courses.csail.mit.edu/6.005/jotto.py?puzzle="
        		+ puzzleNumber + "&guess=" + guess);
        BufferedReader replyFromServer = new BufferedReader(new InputStreamReader(jottoSite.openStream()));
        String reply = "";
        String currentSectionOfReply;
        while ((currentSectionOfReply = replyFromServer.readLine()) != null)
        {
        	if (currentSectionOfReply.contains("error"))
        	{
        		throw new RuntimeException("The input to the server was invalid");
        	}
        	reply += currentSectionOfReply;
        }
        replyFromServer.close();
        return reply;
    }
}
