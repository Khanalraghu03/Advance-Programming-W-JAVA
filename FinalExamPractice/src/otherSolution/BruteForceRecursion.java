package otherSolution;

/**
 * Class: BruteForceRecursion
 * @version 1.0.0
 * @author Richard Smith
 * Written: 4/23/19
 * ITEC 2150 Fall 2018
 * Purpose: 
 */
public class BruteForceRecursion
{
    public static void main(String[] args)
    {
        //array of letters
        char[] letters = {'a', 'b', 'c'};

        //call method
        GiveMeThanos(letters, 4, "");
    }

    // Courtesy of user John Dvorak from:
    // https://stackoverflow.com/questions/13157656/permutation-of-an-array-with-repetition-in-java

    public static void GiveMeThanos(char[] letters, int length, String combo)
    {
        if (combo.length() >= length)
        {
            //if combo length is greater than or equal to length user provided
                //print it out
            System.out.println(combo);
        }
        else
        {
            //While the combo length is less than the length user provide:
                //for every character in letters
            for (char x : letters)
            {
                //add the next letter to it
                GiveMeThanos(letters, length, combo + x);
//                System.out.println(combo + x);

            }
        }
//        System.out.println(GiveMeThanos())
    }



    //Keep repeating the first character in the array until the length
        //decrement, then add the rest of the characters one by one
            //decrement, then follow the same logic

    public static void bruteForce(char[] letters, int length, String combo) {


    }
}
