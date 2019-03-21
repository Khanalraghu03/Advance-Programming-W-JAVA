import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class: SameEndsTester.java
 * @author Richard Smith
 * Written: 1/14/19
 * ITEC 3150 Spring 2019
 * Purpose: This will be the driver class for the SameEnds program, which will compare an ArrayList of integers and
 * determine if it has the same sequence of ints at the beginning and the end. In addition to the standard main method,
 * the other method of this class will handle creating SameEnds objects out of the ArrayLists and call the needed instance
 * methods in order to evaluate the sequence.
 */
public class SameEndssTester
{
    /**
     * Method: testSequence
     * Purpose: This method will take an ArrayList of integers as it parameter and create a new SameEnds objects passing
     * in that as its parameter. It will then call the determineSequence() method on the object to evaluate its sequence
     * and then the toString method to SOP the evaluation.
     * @param list
     */
    public void testSequence(ArrayList<Integer> list)
    {
        SameEndss sameEnds = new SameEndss(list);
        System.out.println("For the list of integers: " + sameEnds.getOriginal());
        sameEnds.determineSequence();
        System.out.println(sameEnds.toString());
        System.out.println();
    }

    /**
     * Method: main
     * Purpose: Standard Java program initializer. This method will create an object of this class in order to call the
     * other method. It will then call the testSequence() method with an ArrayList of integers that are to be evaluated
     * for the same sequence at the beginning and end of the list.
     * @param args
     */
    public static void main(String[] args)
    {
        SameEndssTester tester = new SameEndssTester();

        tester.testSequence(new ArrayList<Integer>()); //no seq
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(0))); //no seq
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(5,6,1,4,5,6,1))); //seq of 5 6 1
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(5,6,1,4,5,6,1,2))); //no seq
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(7,4,6,3,7,4,1))); //no seq
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(1,2,3,1,2,4,5,1,2,4,1,2,3, 1))); //seq of 1 2 3
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(7,7,7,7))); //seq of 7 7
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(7,7,6,7,7))); //seq of 7 7
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(7,7,7))); //seq of 7
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(6,6))); //seq of 6
        tester.testSequence(new ArrayList<Integer>(Arrays.asList(10,99,123,10))); //seq of 10
    }
}

