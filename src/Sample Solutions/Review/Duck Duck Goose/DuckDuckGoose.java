import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Random;


public class DuckDuckGoose
{
	private FileReader fr;
	private Scanner sc;
	private Random random = new Random();
	private LinkedList<String> ll = new LinkedList<String>();//to make an iterator, need to use constructor in LinkedList because it has to be associated with a LL
	private ListIterator<String> iter = ll.listIterator();//provides an iterator for visiting all Strings in LinkedList ll

	/**
	 * openFile method to open the file, then invokes the method that reads it
	 *
	 * @param fileName
	 *
	 * @throws FileNotFoundException
	 */
	public void openFile(String fileName) {
		try {
			fr = new FileReader(fileName);
			sc = new Scanner(fr);
			while (sc.hasNext()) //add names to LinkedList named "ll"
			{
				String s = sc.nextLine();
				ll.add(s);
			}
			System.out.println("Here are all the players: " + ll);
			System.out.println();
			playDuckDuck();
		} catch (FileNotFoundException fnfe) {
			System.out.println("File not Found");
		} catch (NoSuchElementException nsee) {
			System.out.println("No such element was found");
		} catch (Exception e) {
			System.out.println("An exception occurred");
			e.printStackTrace();
		} finally {
			try {
				fr.close();
				sc.close();
			} catch (IOException ioe) {
				System.out.println("Cannot close the output file");
			} catch (NullPointerException npe) {
				System.out.println("File was not created correctly");
			} catch (Exception e) {
				System.out.println("An error occurred");
			}

		}
	}

	/**
	 * playDuckDuck method goes through the LinkedList and plays the game
	 *
	 * @throws IllegalStateException
	 * @throws NoSuchElementException
	 */
	public void playDuckDuck() throws IllegalStateException, NoSuchElementException
	{
		iter = ll.listIterator(); //instantiate the iterator
		while (ll.size() > 1) //until we have a single winner, pick a random number and remove that person
		{
			int num = (random.nextInt(10)) + 1;// add 1 to remove possibility of zero as a number and to get any number from 1 to 10

			System.out.println("Goose player number "+ num + "!");
			for (int m = 0; m < num; m++)
			{
				if (iter.hasNext() == false)// if iterator has reached the end of the LinkedList ll, this will return false
				{
					iter = ll.listIterator();// make the LinkedList reset back to position (0)
					iter.next();
				}
				else
				{
					iter.next();//move iterator one node at a time (using for-loop) to get to the randomly chosen node
				}
			}
			iter.remove(); //remove the node forward of the randomized location
			System.out.println("The players left are: " + ll); //print out list of players after random player has been removed
			System.out.println();
		}

		System.out.println("The winner is " + ll + "!");

	}

	public static void main(String[] args)
	{
		DuckDuckGoose ddg = new DuckDuckGoose(); //create an object DuckDuckGoose
		ddg.openFile("players.txt"); // the method openFile INCLUDES the method playDuckDuck which plays the game with the text file

	}
}
