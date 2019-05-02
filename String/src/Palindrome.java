import java.util.Comparator;

public class Palindrome {
    public static void main(String[] args) {




        int n = 123;
        int r,s = 0;
        int t = n;

        while(n > 0) {

            r = n % 10; //=3 , 2, 1
            n = n / 10; //12, 1, 0 //do one thing
            s = s * 10 + r; //3, 32, 321 //reverse that thing

        }

        if(t == s) {
            System.out.println("Palindrome");
        }
        else {
            System.out.println("Not Palindrome");
        }


    }
}
