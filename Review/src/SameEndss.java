import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class: SameEnds.java
 * @author Richard Smith
 * Written: 1/14/19
 * ITEC 3150 Spring 2019
 * Purpose: Business class for SameEnds program. This class will have the following instance variables:
 *     - integer ArrayList of original, which will be the original list passed into the constructor
 *     - int of sequenceLength, which will represent the length of same integer sequence at the beginning and end of the list
 *     - String of sequence, which will be the sequence of same integers, if it exists
 *     - integer LinkedList of front, which will be the front half of the original list
 *     - integer Queue of back, which will be the back half of the original list
 * This class will also have the following methods:
 *     - SameEnds constructor, accepting one parameter for ArrayList of integers
 *     - getOriginal, converts the original instance variable to serial String output
 *     - convertToList, which will convert the original ArrayList into the front and back instance variables
 *     - determineSequence, which handles the main logic of evaulating whether a sequence exists
 *     - toString, custom toString output
 */
public class SameEndss
{
    private ArrayList<Integer> original;
    private int sequenceLength;
    private String sequence;
    private LinkedList<Integer> front;
    private Queue<Integer> back;

    /**
     * Method: SameEnds
     * Purpose: Single argument constructor that takes a parameter of ArrayList of integers, and sets the instance
     * variable charList to it. Will initialize the remaining instance variables to defaults.
     * @param original
     */
    public SameEndss(ArrayList<Integer> original)
    {
        this.original = original;
        sequenceLength = 0;
        sequence = "";
        front = new LinkedList<>();
        back = new LinkedList<>();
    }

    /**
     * Method: getOriginal
     * Purpose: Uses ENHANCED FOR loop to convert charList instance variable to a serialized String output
     * @return
     */
    public String getOriginal()
    {
        String output = "";
        for (int element : original)
        {
            output += element + " ";
        }
        return output.trim();
    }

    /**
     * Method: convertToList
     * Purpose: This method will create a new ArrayList temp from the instance variable original. It will then remove
     * the center element if it is odd, in order to make it even halves. It will then add the proper elements to the
     * instance variables front and back
     */
    private void convertToLists()
    {
        ArrayList<Integer> temp = original;

        if (temp.size() % 2 == 1) //if amount of integers is odd, then remove middle to make even halves
        {
            temp.remove(temp.size() / 2);
        }

        front = new LinkedList<>();
        for (int index = 0; index < temp.size()/2; index++)
        {
            front.add(temp.get(index));
        }

        back = new LinkedList<>();
        for (int index = temp.size()/2; index < temp.size(); index++)
        {
            back.add(temp.get(index));
        }
    }

    /**
     * Method: determineSequence
     * Purpose: This method will handle the main evaluation for whether the list starts and ends with the same sequence
     * of integers. It will first call the convertToList() method in order to establish the front and back instance variables.
     * It will then go through a while loop based on the condition that the back variable is not empty. With each loop,
     * it will use an IF ELSE statement that will compare the index of the front variable (set by the variable frontIndex
     * which starts at 0) with the first/top node of the back queue, using the poll() method which will also remove it from
     * the queue. If the comparison is a match, the instance variables will be changed to reflect such and the frontIndex
     * variable will increase by one in order to compare the next element in front with its respective position in the back
     * queue. If it is a mismatch, the instance variables will be reset to their defaults and the frontIndex is set back
     * to 0, in order to restart the comparison. Since the back queue is reduced to no nodes, it allows the loop to essentially
     * restart at the beginning of the front list when a mismatch is encountered.
     */
    public void determineSequence()
    {
        convertToLists();

        int frontIndex = 0;
        while (!back.isEmpty())
        {
            if (front.get(frontIndex).equals(back.poll()))
            {
                //Means we have a match to this point, so adjust instance variables and increase frontIndex
                //in order to continue moving through the front variable
                sequenceLength++;
                sequence += front.get(frontIndex) + " ";
                frontIndex++;
            }
            else
            {
                //Means we have found a mismatch, so reset instance and index variables in order to start at the
                //beginning of the front variable one next while loop pass (if back variable is not empty)
                sequenceLength = 0;
                sequence = "";
                frontIndex = 0;
            }
        }
    }

    /**
     * Method: toString
     * Purpose: Custom toString output that will use an IF ELSE statement to return a string concerning the sequenceLength
     * and actual sequence of integers.
     * @return
     */
    public String toString()
    {
        String output = "";
        if (sequenceLength == 0)
        {
            output = "There are no common sequences.";
        }
        else
        {
            output = "The longest sequence is " + sequence.trim() + ", and the sequence length is " + sequenceLength;
        }
        return output;
    }
}
