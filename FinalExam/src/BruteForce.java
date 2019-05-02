/**
 * /**Class: BruteForce
 *  * @author Raghu Khanal
 *  * @version 1.0
 *  * Course : ITEC 3150
 *  * Written: May 2, 2019
 *
 *  This class implements a brute force algorithm recursively and
 *  prints out all the possible binary numbers w/ length of 4.
 */

public class BruteForce {
    public static void main(String[] args) {
        int[] nums = {0, 1};
        BruteIt(nums, 4, "");
    }

    // Credits to John Dvorak:
    // https://stackoverflow.com/questions/13157656/permutation-of-an-array-with-repetition-in-java
    public static void BruteIt(int[] nums, int length, String mixed) {
        if(mixed.length() >= length) {
            System.out.println(mixed);
        }
        else {
            for(int i : nums) {
                BruteIt(nums, length, mixed + i);
            }
        }
    }
}
