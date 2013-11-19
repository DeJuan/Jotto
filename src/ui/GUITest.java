package ui;

/**
 *PROBLEM 2:==================================================================================================
 *  0) I made sure that the 3 fields were lined up as desired in the gui before checking their functionality.
 *
 *  1) I will now check that filling in the text box and processing the input does update the label properly.
 *  
 *  2) I will check that putting in invalid input does not throw a user-visible exception but rather
 *  attempts to choose another puzzle randomly in the range of 0-99999, inclusive.   
 *   
 *  I tested these by opening up the gui, using printlines at critical points like
 *  inside the error handler and inside the normal procedure, and testing that
 *  the random puzzle generated could be above 10,000. I then made sure I could
 *  set the puzzle to at least 5 digits manually.
 *  
 *  I also checked to make sure both clicking the button and pressing enter worked.
 *  
 *  
 */
//=========================END PROBLEM 2======================================================================
/**
 * PROBLEM 3:=================================================================================================
 * 
 * 0) Now that we know the puzzle bit is fine, I can move on to actually making the field.
 * First, I made the two fields and made sure that they lined up properly and displayed correctly.
 * 
 * 1) Once the fields were lined up, I worked on the code to attach the action listener,
 * and just tested it by putting in a simple printline of "received enter" rather than
 * actually implementing the behavior.
 * 
 * 2) Once I was certain that the listener was functional, I added the actual functionality,
 * changing methods in JottoModel and JottoGUI to achieve the desired functionality.
 * I added lots of print statements as I went, and intended to use them to give me locations
 * for debugging.
 * 
 * 3) I ran the GUI and tested the sending and printing out of the returned guess.
 * 
 * 4) Once that worked, I added code to handle when an error was received. 
 * 
 * 5) After that, I added the code for printing "You win! The secret code word was -word-!
 * 
 * 6) I ran through everything one more time, trying to change up the server
 * and seeing if guesses on the same word would change. They did, so I conclude that
 * this section is fully functional and now move to problem 4. 
 * 
 *
 */
//==============================END PROBLEM 3=================================================================
/**
 * PROBLEM 4==================================================================================================
 * 
 * 0)First, I coded up just the representation for the table and made sure that
 * it was located properly relative to everything else. 
 * 
 * 1) Next, I added code just for the table to include just the current
 * guess in the leftmost column, then tested that.
 * 
 * 2) Since that worked, I added the rest of the data to the current row, 
 * and got a full report for the current guess.
 * 
 * 3)After that, I updated the table to actually expand and make more rows,
 * then tested those rows by repeatedly submitting guesses. I noticed here that
 * the frame was not expanding to follow the table.
 * 
 * 4) I attempted to fix the problem above by making a model and adding headers, 
 * then I tested that. This did not fix the problem.
 * 
 * 5) So instead, I added a scroll pane, put the table in the scroll pane, 
 * then added the pane rather than the table itself. This worked quite nicely,
 * and I tested to make sure that the entries were recorded properly. 
 * Here I noticed that on an incorrect guess, the code would overwrite that 
 * slot in the table with the next guess.
 * 
 * 6) I fixed the aforementioned bug and added the code for testing human-readable
 * error messages in the gui, and tested that by providing the server with invalid input.
 * 
 * 7) After that, I added code for the victory message, tested that on server 5555.
 * 
 * 8) Then I added the code to reset the server when we change puzzles,
 * and lastly tested that that worked. 
 * 
 * 
 */
//==================================END PROBLEM 4=============================================================
/**
 * PROBLEM 5==================================================================================================
 * 
 * For this particular problem, I didn't really have a pinpoint strategy like the above ones.
 * 
 * 1) I first just wrote the code for a SwingWorker, didn't attach anything to it, 
 * just wrote it and tried not to error anything.
 * 
 * 2) I started commenting out code in the EDT and relegating a copy of that
 * commented code to the SwingWorker(in uncommented form). I then
 * tested to see if the SwingWorker was picking up its execution call and functioning 
 * properly.
 * 
 * 3) I then relegated all of the code to the SwingWorker and began debugging.
 * I changed most of the variables to public for the Workers' sakes and also
 * devised a solution using global integer counters to solve the issue of
 * where each thread should work. 
 * 
 * 4) I then implemented my solution, noted some bugs that occurred, and then
 * revised my solution.
 * 
 * 5) I commented out any discards from the previous solution and then added
 * code for my newly revised version of my solution.
 * 
 * 6) I looped through steps 4 and 5 until the code worked. Note that step 4, where I say
 * "some bugs", mainly referred to processing errors correctly: At first, it would
 * overwrite the current slot with the error, then keep updating. Then, it would overwrite
 * the current slot with the error, then without updating or adding new slots, keep
 * overwriting the same slot with the data. Then came my idea of isolating a local
 * copy of the globals for each Worker. I implemented this and saw that it worked.
 * 
 * 7) I then checked for what would happen when I refreshed the table while
 * threads were still running using the asterisk delayer. 
 * As of this revision, they all come back with 
 * OutOfBounds exceptions as they refer to places that are no longer valid.
 * This doesn't actually affect the game unless you go so fast that you reset
 * the current board and then rapidly approach the locations that are unfinished
 * and happen to expand the board into them, but the glitch will be overwritten when you submit
 * your guess there. Unless you deliberately attempt to discover this, it will not
 * affect gameplay, but I have documented this both in the concurrency argument
 * comment and here so that it is known that this glitch exists, I know it exists,
 * and have left it unfixed for now.
 * 
 *  
 */
//============================END PROBLEM 5===================================================================

