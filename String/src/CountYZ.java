public class CountYZ {
    //Given a string, count the number of words ending in 'y' or 'z'
    // -- so the 'y' in "heavy" and the 'z' in "fez" count, but not
    // the 'y' in "yellow" (not case sensitive). We'll say that a y or z
    // is at the end of a word if there is not an alphabetic letter
    // immediately following it. (Note: Character.isLetter(char) tests
    // if a char is an alphabetic letter.)
    //

    //countYZ("fez day") → 2
    //countYZ("day fez") → 2
    //countYZ("day fyyyz") → 2

    public static void main(String[] args) {
        System.out.println(countYZ("!!day--yaz!!"));
    }

    public static int countYZ(String str) {
        int count = 0;

        for(int i = 0; i <= str.length() -2; i++) {
            char c = Character.toLowerCase(str.charAt(i));
//            System.out.println("c is " + c);


            //if the first character through the second last character is y or z
                // and the next character is not a letter
                    //add 1 to count
            if((c == 'y' || c == 'z') && !Character.isLetter(str.charAt(i+1))) {
                count++;
            }

        }
        char d = Character.toLowerCase(str.charAt(str.length() - 1));
//        System.out.println("d is " + d);

        //if the last character is y or z, add 1 to count
        if(d == 'y' || d == 'z') {
            count++;
        }

        return count;
    }
}
